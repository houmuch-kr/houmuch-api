package kr.co.houmuch.api.domain.dto.contract;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.YearMonth;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class ContractTrend {
    protected List<MonthSummary> monthList;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MonthSummary {
        private YearMonth yearMonth;
        private double price;
        private int count;
    }
}
