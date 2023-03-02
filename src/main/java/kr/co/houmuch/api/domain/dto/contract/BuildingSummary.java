package kr.co.houmuch.api.domain.dto.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.houmuch.core.domain.building.dto.Building;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpo;
import kr.co.houmuch.core.domain.code.dto.AreaCode;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpo;
import kr.co.houmuch.core.domain.contract.jpa.ContractSummaryJpo;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@SuperBuilder
public class BuildingSummary extends Summary {
    private Building building;
}