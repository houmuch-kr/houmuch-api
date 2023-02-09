package kr.co.houmuch.api.domain.dto.contract;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class ContractSummary {
    protected double priceMonth;
    protected double priceYear;
    protected int tradeCount;
    protected int rentCount;
}
