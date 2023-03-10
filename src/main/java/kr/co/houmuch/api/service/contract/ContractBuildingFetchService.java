package kr.co.houmuch.api.service.contract;

import kr.co.houmuch.api.domain.dto.contract.BuildingContractList;
import kr.co.houmuch.api.domain.dto.contract.BuildingContractSummary;
import kr.co.houmuch.api.domain.dto.contract.BuildingContractTrend;
import kr.co.houmuch.api.domain.dto.contract.ContractTrend;
import kr.co.houmuch.core.domain.building.dto.Building;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpaRepository;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpo;
import kr.co.houmuch.core.domain.contract.ContractType;
import kr.co.houmuch.core.domain.contract.dto.Contract;
import kr.co.houmuch.core.domain.contract.jpa.ContractDetailJpo;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpaRepository;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpo;
import kr.co.houmuch.core.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.co.houmuch.core.util.StreamUtils.filter;

@Service
@RequiredArgsConstructor
public class ContractBuildingFetchService {
    private final ContractJpaRepository contractJpaRepository;
    private final BuildingJpaRepository buildingJpaRepository;

    @Transactional
    public BuildingContractList fetch(String buildingId, int page, int size) {
        BuildingJpo buildingJpo = ensureBuilding(buildingId);
        List<ContractJpo> contractList = contractJpaRepository
                .findByBuilding(buildingJpo, PageRequest.of(page, size));
        return BuildingContractList.builder()
                .building(Building.entityOf(buildingJpo))
                .contractList(contractList.stream()
                        .map(Contract::entityOf)
                        .toList())
                .build();
    }

    @Transactional
    public BuildingContractSummary fetchSummary(String buildingId) {
        BuildingJpo buildingJpo = ensureBuilding(buildingId);
        List<ContractJpo> contractList = contractJpaRepository
                .findByBuilding(buildingJpo, Pageable.unpaged());
        List<ContractJpo> tradeList = filter(contractList, contractJpo -> ContractType.TRADE.equals(contractJpo.getType()));
        List<ContractJpo> rentList = filter(contractList, contractJpo -> ContractType.RENT.equals(contractJpo.getType()));
        return BuildingContractSummary.builder()
                .building(Building.entityOf(buildingJpo))
                .priceMonth(tradeList.stream()
                        .filter(contractJpo -> DateUtils.isFromMonth(contractJpo.getContractedAt(), 1))
                        .map(ContractJpo::getDetail)
                        .mapToDouble(ContractDetailJpo::getPrice)
                        .average()
                        .orElse(0))
                .priceYear(tradeList.stream()
                        .filter(contractJpo -> DateUtils.isFromYear(contractJpo.getContractedAt(), 1))
                        .map(ContractJpo::getDetail)
                        .mapToDouble(ContractDetailJpo::getPrice)
                        .average()
                        .orElse(0))
                .tradeCount(tradeList.size())
                .rentCount(rentList.size())
                .build();
    }

    @Transactional
    public BuildingContractTrend fetchTrend(String buildingId) {
        BuildingJpo buildingJpo = ensureBuilding(buildingId);
        List<ContractJpo> contractList = contractJpaRepository
                .findByBuilding(buildingJpo, Pageable.unpaged());
        Map<YearMonth, List<ContractJpo>> monthMap = contractList.stream()
                .collect(Collectors.groupingBy(contractJpo -> YearMonth.from(contractJpo.getContractedAt())));
        return BuildingContractTrend.builder()
                .building(Building.entityOf(buildingJpo))
                .monthList(monthMap.entrySet().stream()
                        .map(entry -> ContractTrend.MonthSummary.builder()
                                .yearMonth(entry.getKey())
                                .price(entry.getValue().stream()
                                        .mapToDouble(contractJpo -> contractJpo.getDetail().getPrice())
                                        .average()
                                        .orElseThrow())
                                .count(entry.getValue().size())
                                .build())
                        .sorted(Comparator.comparing(ContractTrend.MonthSummary::getYearMonth))
                        .toList())
                .build();
    }

    @Transactional
    public BuildingJpo ensureBuilding(String buildingId) {
        //  TODO : implementation exception
        return buildingJpaRepository.findById(buildingId)
                .orElseThrow();
    }
}
