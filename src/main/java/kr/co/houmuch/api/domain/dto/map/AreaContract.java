package kr.co.houmuch.api.domain.dto.map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import kr.co.houmuch.core.domain.code.dto.AreaCode;
import kr.co.houmuch.core.domain.common.dto.CombinedAreaCode;
import kr.co.houmuch.core.domain.common.dto.Coordinate;
import kr.co.houmuch.core.domain.contract.dto.Contract;
import kr.co.houmuch.core.domain.contract.jpa.ContractSummaryJpo;
import lombok.*;

import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AreaContract {
		private String code;
		private String message;
		private AreaCode areaCode;
		private Contract contract;
		private Coordinate coordinate;
		private CombinedAreaCode combinedAreaCode;
		private double price;
		private int count;
		@JsonIgnore
		private List<Contract> contracts;

		// 엔티티(JPO)를 Dto로 변경하기 위해 만듦
		public static AreaContract entityOf(ContractSummaryJpo contractSummaryJpo) {
				AreaContractBuilder builder = builder();
                    builder
                            .code("0")
                            .message("성공!")
                            .price(contractSummaryJpo.getPrice())
                            .count(contractSummaryJpo.getCount())
                            .areaCode(AreaCode.entityOf(contractSummaryJpo.getAreaCode()));
                    // contract 연결해서 만들어줘야함..! -> contract는 List로 존재하는데 들어가는 것이 맞는가 ?
//                            .contract(Contract.entityOf(contractSummaryJpo.getAreaCode().getBuildingJpo()));

                    if (contractSummaryJpo.getAreaCode() != null) {
                            builder
                                    .coordinate(Coordinate.of(
                                            contractSummaryJpo.getAreaCode().getCoordinate().getCoordinate().getLatitude(),
                                            contractSummaryJpo.getAreaCode().getCoordinate().getCoordinate().getLongitude()))
                                    .combinedAreaCode(CombinedAreaCode.of(
                                                    contractSummaryJpo.getAreaCode().getCode().getSido()
                                                    , contractSummaryJpo.getAreaCode().getCode().getSgg()
                                                    , contractSummaryJpo.getAreaCode().getCode().getUmd()));
                    }
				return builder.build();
		}
}

/* 원하는 형태
[
  {
    "code": 0,
    "message": "정상적으로 처리되었습니다.",
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
