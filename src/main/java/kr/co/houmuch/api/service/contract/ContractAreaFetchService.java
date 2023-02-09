package kr.co.houmuch.api.service.contract;

import kr.co.houmuch.api.domain.dto.contract.AreaContractList;
import kr.co.houmuch.api.domain.dto.contract.AreaContractSummary;
import kr.co.houmuch.api.domain.dto.contract.AreaContractTrend;
import kr.co.houmuch.api.domain.dto.contract.ContractTrend;
import kr.co.houmuch.core.domain.code.AreaCodeJpaRepository;
import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import kr.co.houmuch.core.domain.code.dto.AreaCode;
import kr.co.houmuch.core.domain.contract.ContractType;
import kr.co.houmuch.core.domain.contract.dto.Contract;
import kr.co.houmuch.core.domain.contract.jpa.ContractDetailJpo;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpaRepository;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kr.co.houmuch.core.util.StreamUtils.filter;

@Service
@RequiredArgsConstructor
public class ContractAreaFetchService {
    private final AreaCodeJpaRepository areaCodeJpaRepository;
    private final ContractJpaRepository contractJpaRepository;

    @Transactional
    public AreaContractList fetch(long areaCode) {
        AreaCodeJpo areaCodeJpo = ensureAreaCode(areaCode);
        List<ContractJpo> contractList = contractJpaRepository.findByAreaCode(areaCodeJpo);
        return AreaContractList.builder()
                .contractList(contractList.stream()
                        .map(Contract::entityOf)
                        .toList())
                .areaCode(AreaCode.entityOf(areaCodeJpo))
                .build();
    }

    @Transactional
    public AreaContractSummary fetchSummary(long areaCode) {
        AreaCodeJpo areaCodeJpo = ensureAreaCode(areaCode);
        List<ContractJpo> contractList = contractJpaRepository.findByAreaCode(areaCodeJpo);
        List<ContractJpo> tradeList = filter(contractList, contractJpo -> ContractType.TRADE.equals(contractJpo.getType()));
        List<ContractJpo> rentList = filter(contractList, contractJpo -> ContractType.RENT.equals(contractJpo.getType()));
        return AreaContractSummary.builder()
                .areaCode(AreaCode.entityOf(areaCodeJpo))
                .priceMonth(tradeList.stream()
                        .map(ContractJpo::getDetail)
                        .mapToDouble(ContractDetailJpo::getPrice)
                        .average()
                        .orElseThrow())
                .priceYear(tradeList.stream()
                        .map(ContractJpo::getDetail)
                        .mapToDouble(ContractDetailJpo::getPrice)
                        .average()
                        .orElseThrow())
                .tradeCount(tradeList.size())
                .rentCount(rentList.size())
                .build();
    }

    @Transactional
    public AreaContractTrend fetchTrend(long areaCode) {
        AreaCodeJpo areaCodeJpo = ensureAreaCode(areaCode);
        List<ContractJpo> contractList = contractJpaRepository.findByAreaCode(areaCodeJpo);
        Map<YearMonth, List<ContractJpo>> monthMap = contractList.stream()
                .collect(Collectors.groupingBy(contractJpo -> YearMonth.from(contractJpo.getContractedAt())));
        return AreaContractTrend.builder()
                .areaCode(AreaCode.entityOf(areaCodeJpo))
                .monthList(monthMap.entrySet().stream()
                        .map(entry -> ContractTrend.MonthSummary.builder()
                                .yearMonth(entry.getKey())
                                .price(entry.getValue().stream()
                                        .mapToDouble(contractJpo -> contractJpo.getDetail().getPrice())
                                        .average()
                                        .orElseThrow())
                                .count(entry.getValue().size())
                                .build())
                        .toList())
                .build();
    }

    @Transactional
    public AreaCodeJpo ensureAreaCode(long areaCode) {
        //  TODO : implementation exception
        return areaCodeJpaRepository.findById(areaCode)
                .orElseThrow();
    }
}
