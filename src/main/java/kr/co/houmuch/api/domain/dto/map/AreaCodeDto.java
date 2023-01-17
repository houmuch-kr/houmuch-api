package kr.co.houmuch.api.domain.dto.map;

import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import kr.co.houmuch.core.domain.common.jpa.CombinedAreaCodeJpo;
import kr.co.houmuch.core.domain.common.jpa.CoordinateJpo;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
public class AreaCodeDto {
    private Long id;
    private int type;
    private String address;
    private String shortAddress;
    private String fullAddress;
    private CombinedAreaCodeJpo code;
    private CoordinateJpo coordinate;

//private AreaCoordinateJpo coordinate;// 무한반복


// 엔티티(JPO)를 Dto로 변경하기 위해 만듦
    public static AreaCodeDto entityOf(AreaCodeJpo areaCodeJpo) {
        return builder()
                .id(areaCodeJpo.getId())
                .type(areaCodeJpo.getType())
                .address(areaCodeJpo.getAddress())
                .shortAddress(areaCodeJpo.getShortAddress())
                .fullAddress(areaCodeJpo.getFullAddress())
                .code(areaCodeJpo.getCode())
                .coordinate(areaCodeJpo.getCoordinate().getCoordinate())
                .build();
    }
}
/* 원하는 형태
[
  {
    "id": 1111010100(코드어쩌죠?),
    "type": 2,
    "address": "서울특별시 종로구 청운동 임효양아파트",
    "shortAddress": "임효양아파트",
    "fullAddress": "서울특별시 종로구 청운동 임효양아파트",
    "code": {
      "sido": 11,
      "sgg": 110,
      "umd": 10100,
      "building name": "임효양아파트"
    },
    "coordinate": {
      "id": 1111010100(코드어쩌죠?),
      "coordinate": {
        "latitude": 100.0000000,
        "longitude": 1000.0000000
      }
    },
    "areaCode": {
      "id": 1111010100,
      "type": 1,
      "address": "서울특별시 종로구 청운동 임효양아파트",
      "shortAddress": "임효양아파트",
      "fullAddress": "서울특별시 종로구 청운동 임효양아파트",
      "code": {
        "sido": 11,
        "sgg": 110,
        "umd": 10100,
        "building name": "임효양아파트"
      }

    ]

* */



