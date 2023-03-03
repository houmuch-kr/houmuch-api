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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.co.houmuch.core.util.StreamUtils.map;

@Service
@RequiredArgsConstructor
public class MapService{
    private final AreaCodeJpaRepository areaCodeJpaRepository;
    private final ContractJpaRepository contractJpaRepository;
    private final BuildingJpaRepository buildingJpaRepository;
    private final ContractSummaryJpaRepository contractSummaryJpaRepository;

    @Transactional
    public void indexV2Stream() {
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
    public void indexV2() {
        List<AreaCodeJpo> areaCodeList = areaCodeJpaRepository.findAllFetchJoin();
        List<ContractJpo> contractList = contractJpaRepository.findByType(ContractType.TRADE);
        Map<AreaCodeJpo, List<ContractJpo>> contractMap = new HashMap<>();
        for (AreaCodeJpo areaCodeJpo : areaCodeList) {
            List<ContractJpo> areaContractList = contractList
                    .stream()
                    .filter(contractJpo -> {
                        AreaCodeJpo contractAreaCode = contractJpo.getBuilding().getAreaCode();
                        return areaCodeJpo.isHierarchy(contractAreaCode);
                    })
                    .collect(Collectors.toList());
            contractMap.put(areaCodeJpo, areaContractList);
        }
        List<ContractSummaryJpo> contractSummaryList = new ArrayList<>();
        for (Map.Entry<AreaCodeJpo, List<ContractJpo>> entry : contractMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                ContractSummaryJpo contractSummary = ContractSummaryJpo.builder()
                        .id(entry.getKey().getId())
                        .count(0)
                        .price(0.0)
                        .build();
                contractSummaryList.add(contractSummary);
                continue;
            }
            int totalPrice = 0;
            int count = entry.getValue().size();
            for (ContractJpo contractJpo : entry.getValue()) {
                totalPrice += contractJpo.getDetail().getPrice();
            }
            double price = (double) totalPrice / count;
            ContractSummaryJpo contractSummary = ContractSummaryJpo.builder()
                    .id(entry.getKey().getId())
                    .count(count)
                    .price(price)
                    .build();
            contractSummaryList.add(contractSummary);
        }
        contractSummaryJpaRepository.saveAll(contractSummaryList);
    }

    @Transactional
    public void index() {
//        전국 area_code 조회해서
//        price 와 count 를 알아내면 됨
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
        System.out.println("buildingMap---->" + buildingMap);
        List<Summary> buildingSummaryList = new ArrayList<>();
        for(Map.Entry<BuildingJpo, List<ContractJpo>> entry : buildingMap.entrySet()){
            if(entry.getValue().isEmpty()){
                buildingSummaryList.add(BuildingSummary.builder()
                        .building(Building.entityOf(entry.getKey()))
                        .price(0.0)
                        .count(0)
                        .build());
                continue;
            }
            int totalPrice = 0;
            int count = entry.getValue().size();
            for(ContractJpo contractJpo : entry.getValue()){
                totalPrice += contractJpo.getDetail().getPrice();
            }
            double price = (double) totalPrice / count;
            buildingSummaryList.add(BuildingSummary.builder()
                    .building(Building.entityOf(entry.getKey()))
                    .price(price)
                    .count(count)
                    .build());
        }
        return buildingSummaryList;
    }
}
