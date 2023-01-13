package kr.co.houmuch.api.domain.map;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Map {
    private Long id;
    private int type;
    private String address;
    private String shortAddress;
    private String fullAddress;
    private int sido;
    private int sgg;
    private int umd;
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
