package kr.co.houmuch.api.service.contract;

import kr.co.houmuch.api.domain.dto.contract.AreaContractList;
import kr.co.houmuch.api.domain.dto.contract.AreaContractSummary;
import kr.co.houmuch.api.domain.dto.contract.AreaContractTrend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContractAreaFetchService {
    @Transactional
    public AreaContractList fetch(long areaCode) {
        return AreaContractList.builder()
                .build();
    }

    @Transactional
    public AreaContractSummary fetchSummary(long areaCode) {
        return AreaContractSummary.builder()
                .build();
    }

    @Transactional
    public AreaContractTrend fetchTrend(long areaCode) {
        return AreaContractTrend.builder()
                .build();
    }
}
