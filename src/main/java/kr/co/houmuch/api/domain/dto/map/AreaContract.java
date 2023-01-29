package kr.co.houmuch.api.domain.dto.map;

import kr.co.houmuch.core.domain.common.dto.CombinedAreaCode;
import kr.co.houmuch.core.domain.common.dto.Coordinate;
import kr.co.houmuch.core.domain.contract.ContractType;
import kr.co.houmuch.core.domain.contract.dto.Contract;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpo;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
public class AreaContract {
    private Long id;
    private ContractType type;
    private String address;
    private String shortAddress;
    private String fullAddress;
    private Coordinate coordinate;
    private CombinedAreaCode code;
    private Contract contract;


//    private Coordinate coordinate;


// 엔티티(JPO)를 Dto로 변경하기 위해 만듦
    public static AreaContract entityOf(ContractJpo contractJpo) {
//        Optional<AreaCodeJpo> areaCodeO = Optional.of(new AreaCodeJpo());
//        Optional<Coordinate> coordinateO = Optional.of(new Coordinate(0L, 0L));

//                Coordinate.of(
//                contractJpo.getAreaCode().getCoordinate().getCoordinate().getLatitude(),
//                contractJpo.getAreaCode().getCoordinate().getCoordinate().getLongitude()));

//        if(contractJpo.getAreaCode() == null){
//            System.out.println("AreaCode가 null임!!!!!!!!!");
//            return null;
//        }
//        if(contractJpo.getAreaCode().getCoordinate().getCoordinate() == null){
//            System.out.println("coorinate이 null임!!!!!!!!!!!!");
//            return null;
//        }

        System.out.println("00000----->" + contractJpo.getBuilding().getAreaCode());
        System.out.println("id----->" + contractJpo.getBuilding().getAreaCode().getId());

        AreaContractBuilder builder = builder();
        if(contractJpo.getBuilding().getAreaCode() != null){
            builder
                    .id(contractJpo.getBuilding().getAreaCode().getId())
                    .type(contractJpo.getType())
                    .address(contractJpo.getBuilding().getAreaCode().getAddress())
                    .shortAddress(contractJpo.getBuilding().getAreaCode().getShortAddress())
                    .fullAddress(contractJpo.getBuilding().getAreaCode().getFullAddress());
        }

        System.out.println("1111 builder----->" + builder);
        System.out.println("");

        if(contractJpo.getBuilding().getAreaCode().getCoordinate() != null){
            builder
                    .coordinate(Coordinate.of(
                            contractJpo.getBuilding().getAreaCode().getCoordinate().getCoordinate().getLatitude(),
                            contractJpo.getBuilding().getAreaCode().getCoordinate().getCoordinate().getLongitude()));
        }

        System.out.println("2222 builder----->" + builder);

        if(contractJpo.getBuilding().getAreaCode().getCode() != null){
            builder
                    .code(CombinedAreaCode.of(
                            contractJpo.getBuilding().getAreaCode().getCode().getSido()
                            ,contractJpo.getBuilding().getAreaCode().getCode().getSgg()
                            ,contractJpo.getBuilding().getAreaCode().getCode().getUmd()));
        }

        System.out.println("3333 builder----->" + builder);

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

        System.out.println("완성된 builder----->" + builder);

        return builder.build();

//        return builder()
//                .id(contractJpo.getAreaCode().getId())
//                .type(contractJpo.getType())
//                .address(contractJpo.getAreaCode().getAddress())
//                .shortAddress(contractJpo.getAreaCode().getShortAddress())
//                .fullAddress(contractJpo.getAreaCode().getFullAddress())
//                .coordinate(Coordinate.of(
//                        contractJpo.getAreaCode().getCoordinate().getCoordinate().getLatitude(),
//                        contractJpo.getAreaCode().getCoordinate().getCoordinate().getLongitude()))
//                .code(CombinedAreaCode.builder()
//                        .sido(contractJpo.getAreaCode().getCode().getSido())
//                        .sgg(contractJpo.getAreaCode().getCode().getSgg())
//                        .umd(contractJpo.getAreaCode().getCode().getUmd())
//                        .build())
//                .contract(Contract.builder()
//                        .id(contractJpo.getId())
//                        .type(contractJpo.getType())
//                        .buildingType(contractJpo.getBuildingType())
//                        .contractedAt(contractJpo.getContractedAt())
//                        .serialNumber(contractJpo.getSerialNumber())
//                        .name(contractJpo.getName())
//                        .build())
//                .build();
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
