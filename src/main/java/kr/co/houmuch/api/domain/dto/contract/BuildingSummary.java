package kr.co.houmuch.api.domain.dto.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.houmuch.core.domain.building.dto.Building;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpo;
import kr.co.houmuch.core.domain.code.dto.AreaCode;
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

    // 엔티티(JPO)를 Dto로 변경하기 위해 만듦
    public static BuildingSummary entityOf(ContractSummaryJpo contractSummaryJpo) {
        List<BuildingJpo> buildingJpoList = contractSummaryJpo.getAreaCode().getBuildingJpo();
        for(BuildingJpo buildingJpo : buildingJpoList) {
            builder()
                    .building(Building.entityOf(buildingJpo))
                    .price(contractSummaryJpo.getPrice())
                    .count(contractSummaryJpo.getCount())
                    .build();
        }
        return builder().build();
    }
}

/* 원하는 형태
[
  {
    "data": {
      "id": 1111010100,
      "type": 2,
      "address": "서울특별시 종로구 청운동 임효양아파트",
      "areaCode": {
        "id": 1111010100,
        "type": 2,
        "address": "서울특별시 종로구 청운동 임효양아파트",
        "shortAddress": "임효양아파트",
        "fullAddress": "서울특별시 종로구 청운동 임효양아파트",
        "coordinate": {
          "latitude": 100.0000000,
          "longitude": 1000.0000000
        },
        "code": {
          "sido": 11,
          "sgg": 110,
          "umd": 10100
        },
        "contract": {
          "id": "000zq6qC9q",
          "type": "RENT",
          "building_type": "APARTMENT",
          "area_code": 4165010101,
          "contracted_at": "2023-1-17",
          "serial_number": null,
          "building": {
            "name": "임효양아파트",
            여기에빌딩정보들......
          },
          "tradePrice": "1000원",
          "cnt": 1000
        }
      }
    ]
* */
