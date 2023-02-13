package kr.co.houmuch.api.domain.dto.contract;

import kr.co.houmuch.core.domain.building.dto.Building;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BuildingContractTrend extends ContractTrend {
    private Building building;
}
