package kr.co.houmuch.api.service.contract;

import kr.co.houmuch.api.domain.dto.contract.BuildingContractList;
import kr.co.houmuch.api.domain.dto.contract.BuildingContractSummary;
import kr.co.houmuch.api.domain.dto.contract.BuildingContractTrend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContractBuildingFetchService {
    @Transactional
    public BuildingContractList fetch(long buildingId) {
        return BuildingContractList.builder().build();
    }

    @Transactional
    public BuildingContractSummary fetchSummary(long buildingId) {
        return BuildingContractSummary.builder().build();
    }

    @Transactional
    public BuildingContractTrend fetchTrend(long buildingId) {
        return BuildingContractTrend.builder().build();
    }
}
