package kr.co.houmuch.api.domain.dto.map;

import kr.co.houmuch.core.domain.building.dto.Building;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpo;
import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import kr.co.houmuch.core.domain.common.dto.CombinedAreaCode;
import kr.co.houmuch.core.domain.common.dto.Coordinate;
import kr.co.houmuch.core.domain.contract.dto.Contract;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
public class AreaContract {
    private Long id;
    private int type;
    private String address;
    private String shortAddress;
    private String fullAddress;
    private Coordinate coordinate;
    private CombinedAreaCode code;
    private Contract contract;

// 엔티티(JPO)를 Dto로 변경하기 위해 만듦
    public static AreaContract entityOf(AreaCodeJpo areaCodeJpo) {
        System.out.println("areaCodeJpo----->" + areaCodeJpo);
        System.out.println("id----->" + areaCodeJpo.getId());

        AreaContractBuilder builder = builder();
        if(areaCodeJpo != null){
            builder
                    .id(areaCodeJpo.getId())
                    .type(areaCodeJpo.getType())
                    .address(areaCodeJpo.getAddress())
                    .shortAddress(areaCodeJpo.getShortAddress())
                    .fullAddress(areaCodeJpo.getFullAddress());

			System.out.println("1111 builder----->" + builder);

			if(areaCodeJpo.getCoordinate() != null){
				builder
						.coordinate(Coordinate.of(
								areaCodeJpo.getCoordinate().getCoordinate().getLatitude(),
								areaCodeJpo.getCoordinate().getCoordinate().getLongitude()));
			}

			System.out.println("2222 builder----->" + builder);

			if(areaCodeJpo.getCode() != null){
				builder
						.code(CombinedAreaCode.of(
								areaCodeJpo.getCode().getSido()
								,areaCodeJpo.getCode().getSgg()
								,areaCodeJpo.getCode().getUmd()));
			}

			System.out.println("3333 builder----->" + builder);

			if(Contract.builder().build() != null){
				builder
						.contract(Contract.builder().build());
			}

			System.out.println("4444 builder----->" + builder);

        if(contractJpo != null){
            builder
                    .contract(Contract.builder()
                            .id(contractJpo.getId())
                            .type(contractJpo.getType())
                            .buildingType(contractJpo.getBuilding().getType())
                            .areaCode(contractJpo.getBuilding().getAreaCode().getId())
                            .contractedAt(contractJpo.getContractedAt())
                            .serialNumber(contractJpo.getSerialNumber())
                            .name(contractJpo.getBuilding().getName())
                            .build());
        }

        System.out.println("완성된 builder----->" + builder.build());

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
