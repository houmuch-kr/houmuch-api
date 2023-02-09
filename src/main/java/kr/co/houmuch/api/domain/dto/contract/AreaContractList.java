package kr.co.houmuch.api.domain.dto.contract;

import kr.co.houmuch.core.domain.code.dto.AreaCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AreaContractList extends ContractList {
    private AreaCode areaCode;
}
