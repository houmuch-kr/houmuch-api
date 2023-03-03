package kr.co.houmuch.api.domain.dto.contract;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Summary {
    protected double price;
    protected int count;
}
