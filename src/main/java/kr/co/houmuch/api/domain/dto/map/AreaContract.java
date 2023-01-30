package kr.co.houmuch.api.domain.dto.map;

import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import kr.co.houmuch.core.domain.code.dto.AreaCode;
import kr.co.houmuch.core.domain.contract.dto.Contract;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpo;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
public class AreaContract {
    private List<AreaCode> areaCode;
    private List<Contract> contract;

    public static AreaContract of(List<AreaCodeJpo> areaCodeJpos, List<ContractJpo> contractJpos) {
        AreaContractBuilder builder = builder();
        builder
                .areaCode(areaCodeJpos.stream().map(AreaCode::entityOf).collect(Collectors.toList()))
                .contract(contractJpos.stream().map(Contract::entityOf).collect(Collectors.toList()));
        return builder.build();
    }
}
/* 원하는 형태
[
  {
    "id": 1111010100,
    "type": 2,
    "address": "서울특별시 종로구 청운동 임효양아파트",
    "shortAddress": "임효양아파트",
    "fullAddress": "서울특별시 종로구 청운동 임효양아파트",
    "coordinate": {
      "latitude": 100.0000000,
      "longitude": 1000.0000000
    },
    "areaCode": {
      "sido": 11,
      "sgg": 110,
      "umd": 10100,
      "building name": "임효양아파트"
    },
    "contract": {
      "id": "000zq6qC9q",
      "type": "RENT",
      "building_type": "APARTMENT",
      "area_code": 4165010101,
      "contracted_at": "2023-1-17",
      "serial_number": null,
      "name": "임효양아파트"
    }
  }
]
* */
