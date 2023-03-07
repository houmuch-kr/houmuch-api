package kr.co.houmuch.api.service;

import kr.co.houmuch.api.domain.dto.contract.AreaSummary;
import kr.co.houmuch.api.domain.dto.contract.BuildingSummary;
import kr.co.houmuch.api.domain.dto.contract.Summary;
import kr.co.houmuch.core.domain.building.dto.Building;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpaRepository;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpo;
import kr.co.houmuch.core.domain.code.AreaCodeJpaRepository;
import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import kr.co.houmuch.core.domain.common.jpa.CombinedAreaCodeJpo;
import kr.co.houmuch.core.domain.contract.ContractType;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpaRepository;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpo;
import kr.co.houmuch.core.domain.contract.jpa.ContractSummaryJpaRepository;
import kr.co.houmuch.core.domain.contract.jpa.ContractSummaryJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.co.houmuch.core.util.StreamUtils.map;

@Service
@RequiredArgsConstructor
public class ContractSummaryService {
    private final AreaCodeJpaRepository areaCodeJpaRepository;
    private final ContractJpaRepository contractJpaRepository;
    private final BuildingJpaRepository buildingJpaRepository;
    private final ContractSummaryJpaRepository contractSummaryJpaRepository;

    @Transactional
    public void indexV2() {
        List<ContractJpo> contractList = contractJpaRepository.findByType(ContractType.TRADE);
        Map<AreaCodeJpo, List<ContractJpo>> contractMap = contractList
                .stream()
                .collect(Collectors.groupingBy(contractJpo -> contractJpo.getBuilding().getAreaCode()));
        List<ContractSummaryJpo> contractSummaryList = contractMap.entrySet()
                .stream()
                .map(entry -> ContractSummaryJpo.builder()
                        .id(entry.getKey().getId())
                        .count(entry.getValue().size())
                        .price(entry.getValue()
                                .stream()
                                .mapToInt(value -> value.getDetail().getPrice())
                                .average()
                                .orElse(0))
                        .build())
                .toList();
        contractSummaryJpaRepository.saveAll(contractSummaryList);
    }
    @Transactional
    public void index() {
        List<AreaCodeJpo> areaCodeList = areaCodeJpaRepository.findAllFetchJoin();
        for(AreaCodeJpo areaCodeJpo : areaCodeList){
            CombinedAreaCodeJpo combinedAreaCodeJpo = areaCodeJpo.getCode();
            List<ContractJpo> contractJpoList = contractJpaRepository.findByAreaCodeAndTypeNotOrder(
                    combinedAreaCodeJpo.getSido(),
                    combinedAreaCodeJpo.getSgg(),
                    combinedAreaCodeJpo.getUmd(),
                    ContractType.TRADE,
                    Pageable.unpaged()
            );
            // contractJpoList 가 0 인경우는 어떤 경우지?
            if (contractJpoList.size() != 0) {
                int totalPrice = 0;
                int count = contractJpoList.size();
                for (ContractJpo contractJpo : contractJpoList) {
                    totalPrice += contractJpo.getDetail().getPrice();
                }
                Double price = (totalPrice / count) * 0.0;
                contractSummaryJpaRepository.save(ContractSummaryJpo.builder()
                        .id(areaCodeJpo.getId())
                        .price(price)
                        .count(count)
                        .build());
            }
        }
    }

    public List<Summary> fetch(int type, double maxLatitude, double minLatitude, double maxLongitude, double minLongitude) {
        if (type == 3) {
            return fetchBuilding(maxLatitude, minLatitude, maxLongitude, minLongitude);
        }
        List<AreaCodeJpo> areaCodeList = areaCodeJpaRepository.findByType(type, maxLatitude, minLatitude, maxLongitude, minLongitude);
        List<Long> areaCodes = map(areaCodeList, AreaCodeJpo::getId);
        List<ContractSummaryJpo> contractSummaryList = contractSummaryJpaRepository.findByAreaCode(areaCodes);
        return contractSummaryList.stream().map(AreaSummary::entityOf).collect(Collectors.toList());
    }

    public List<Summary> fetchBuilding(double maxLatitude, double minLatitude, double maxLongitude, double minLongitude) {
        List<ContractJpo> contractJpoList = buildingJpaRepository.findAllByContract(maxLatitude, minLatitude, maxLongitude, minLongitude);
        Map<BuildingJpo, List<ContractJpo>> buildingMap = contractJpoList
                .stream()
                .collect(Collectors.groupingBy(contractJpo1 -> contractJpo1.getBuilding()));
        List<Summary> buildingSummaryList = buildingMap.entrySet()
                    .stream()
                    .map(entry -> BuildingSummary.builder()
                            .building(Building.entityOf(entry.getKey()))
                            .count(entry.getValue().size())
                            .price(entry.getValue()
                                    .stream()
                                    .mapToInt(value -> value.getDetail().getPrice())
                                    .average()
                                    .orElse(0))
                            .build())
                .collect(Collectors.toList());
        return buildingSummaryList;
    }
}
