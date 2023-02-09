package kr.co.houmuch.api.domain.dto.contract;

import kr.co.houmuch.core.domain.contract.dto.Contract;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class ContractList {
    protected List<Contract> contractList;
}
