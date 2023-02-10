package kr.co.houmuch.api.service;

import kr.co.houmuch.api.domain.dto.map.AreaContract;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpaRepository;
import kr.co.houmuch.core.domain.code.AreaCodeJpaRepository;
import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import kr.co.houmuch.core.domain.code.dto.AreaCode;
import kr.co.houmuch.core.domain.common.jpa.CombinedAreaCodeJpo;
import kr.co.houmuch.core.domain.contract.jpa.ContractDetailJpo;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpaRepository;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapService{
    private final AreaCodeJpaRepository areaCodeJpaRepository;
    private final ContractJpaRepository contractJpaRepository;

    public List<AreaContract> fetch(int type) {
        List<AreaCodeJpo> areaCodeList = areaCodeJpaRepository.findByType(type);

        List<Integer> sidoLists = areaCodeList.stream()
                .map(AreaCodeJpo::getCode)
                .map(CombinedAreaCodeJpo::getSido)
                .toList();

        // 모든 거래내역들
        List<ContractJpo> contractList = contractJpaRepository.findByBuildingAreaCode(sidoLists);

        List<AreaContract> areaContractList = new ArrayList<>();

        switch (type) {
            case 0:
                for (AreaCodeJpo areaCodeJpo : areaCodeList){
                    int count = 0;
                    double contractPrice = 0.0;
                    for (ContractJpo contractJpo : contractList) {
                        int contractAreaCode = contractJpo.getBuilding().getAreaCode().getCode().getSido();
                        int areaCodeSido = areaCodeJpo.getCode().getSido();
                        if (contractAreaCode == areaCodeSido) {
                            count++;
                            contractPrice += contractJpo.getDetail().getPrice();
                        }
                    }
                    contractPrice = contractPrice / count;

                    AreaContract areaContract = AreaContract.builder()
                            .areaCode(AreaCode.entityOf(areaCodeJpo))
                            .count(count)
                            .price(contractPrice)
                            .build();
                    areaContractList.add(areaContract);
                }
                break;

            case 1:
                for (AreaCodeJpo areaCodeJpo : areaCodeList){
                    AreaContract areaContract = AreaContract.builder()
                            .areaCode(AreaCode.entityOf(areaCodeJpo))
//                            .count(contractList.size() == 0 ? contractList
//                                    .stream()
//                                    .collect(Collectors.groupingBy(contractJpo -> contractJpo.getBuilding().getAreaCode().getCode().getSgg()))
//                                    .get(areaCodeJpo.getCode().getSgg())
//                                    .size() : 0
//                            )
//                            .price(contractList
//                                    .stream()
//                                    .collect(Collectors.groupingBy(contractJpo -> contractJpo.getBuilding().getAreaCode().getCode().getSgg()))
//                                    .get(areaCodeJpo.getCode().getSgg())
//                                    .stream()
//                                    .map(ContractJpo::getDetail)
//                                    .mapToInt(ContractDetailJpo::getPrice)
//                                    .average()
//                                    .orElse(0)
//                            )
                            .build();
                    areaContractList.add(areaContract);
                }

                break;

            case 2:
                for (ContractJpo contractJpo : contractList) {
                    AreaContract areaContract = AreaContract.builder()
                            .areaCode(AreaCode.entityOf(contractJpo.getBuilding().getAreaCode()))
//            .count(contractList.size())
                            .count(contractList
                                    .stream()
                                    .collect(Collectors.groupingBy(contractJpo1 -> contractJpo1.getBuilding().getId()))
                                    .get(contractJpo.getBuilding().getId())

                                    .size()
                            )
//            .price(contractList.stream()
//                    .map(ContractJpo::getDetail)
//                    .mapToInt(ContractDetailJpo::getPrice)
//                    .average()
//                    .orElse(0))
//            .contracts(contractList.stream().map(Contract::entityOf).collect(Collectors.toList()))
                            .price(contractList
                                    .stream()
                                    .collect(Collectors.groupingBy(contractJpo1 -> contractJpo1.getBuilding().getId()))
                                    .get(contractJpo.getBuilding().getId())
                                    .stream()
                                    .map(ContractJpo::getDetail)
                                    .mapToInt(ContractDetailJpo::getPrice)
                                    .average()
                                    .orElse(0)
                            )
//                            .building(contractJpo.getBuilding().getCoordinate() != null ?
//                                    Building.of(contractJpo.getBuilding().getName()
//                                            , Coordinate.of(contractJpo.getBuilding().getCoordinate().getCoordinate().getLatitude()
//                                                    , contractJpo.getBuilding().getCoordinate().getCoordinate().getLongitude()))
//                                    : null)
                            .build();
                    areaContractList.add(areaContract);
                }
                break;
        }

        return areaContractList;
    }
}
