package kr.co.houmuch.api.domain.dto.map;

import kr.co.houmuch.core.domain.area.jpa.AreaCoordinateJpo;
import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import kr.co.houmuch.core.domain.common.dto.CombinedAreaCode;
import kr.co.houmuch.core.domain.common.dto.Contract;
import kr.co.houmuch.core.domain.common.jpa.CoordinateJpo;
import kr.co.houmuch.core.domain.contract.ContractJpo;
import kr.co.houmuch.core.domain.contract.ContractType;
import lombok.*;

import java.util.Optional;

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
    private AreaCoordinateJpo coordinate;
    private CombinedAreaCode code;
    private Contract contract;


//    private Coordinate coordinate;


// 엔티티(JPO)를 Dto로 변경하기 위해 만듦
//    public static AreaContract entityOf(AreaCodeJpo areaCodeJpo, Coordinate coordinate) {
    public static AreaContract entityOf(ContractJpo contractJpo) {
        if(contractJpo.getAreaCode() == null){
            System.out.println("AreaCode가 null임!!!!!!!!!");
            return null;
        }
        return builder()
                .id(contractJpo.getAreaCode().getId())
                .type(contractJpo.getType())
                .address(contractJpo.getAreaCode().getAddress())
                .shortAddress(contractJpo.getAreaCode().getShortAddress())
                .fullAddress(contractJpo.getAreaCode().getFullAddress())
                .coordinate(contractJpo.getAreaCode().getCoordinate())
                .code(CombinedAreaCode.builder()
                        .sido(contractJpo.getAreaCode().getCode().getSido())
                        .sgg(contractJpo.getAreaCode().getCode().getSgg())
                        .umd(contractJpo.getAreaCode().getCode().getUmd())
                        .build())
                .contract(Contract.builder()
                        .id(contractJpo.getId())
                        .type(contractJpo.getType())
                        .buildingType(contractJpo.getBuildingType())
                        .contractedAt(contractJpo.getContractedAt())
                        .serialNumber(contractJpo.getSerialNumber())
                        .name(contractJpo.getName())
                        .build())
                .build();
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