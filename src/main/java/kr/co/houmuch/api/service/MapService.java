package kr.co.houmuch.api.service;

import kr.co.houmuch.api.domain.dto.map.AreaContract;
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

        List<Integer> sggLists = areaCodeList.stream()
                .map(AreaCodeJpo::getCode)
                .map(CombinedAreaCodeJpo::getSgg)
                .toList();

        List<Integer> umdLists = areaCodeList.stream()
                .map(AreaCodeJpo::getCode)
                .map(CombinedAreaCodeJpo::getUmd)
                .toList();

        // 모든 거래내역들
        List<ContractJpo> contractList =  new ArrayList<>();
        List<AreaContract> areaContractList = new ArrayList<>();

        switch (type) {
            case 0:
                contractList = contractJpaRepository.findByBuildingAreaCodeSido(sidoLists);
                for (AreaCodeJpo areaCodeJpo : areaCodeList){
                    int count = 0;
                    double contractPrice = 0.0;
                    for (ContractJpo contractJpo : contractList) {
                        int contractAreaCodeSido = contractJpo.getBuilding().getAreaCode().getCode().getSido();
                        int areaCodeSido = areaCodeJpo.getCode().getSido();
                        if (contractAreaCodeSido == areaCodeSido) {
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
                contractList = contractJpaRepository.findByBuildingAreaCodeSgg(sggLists);
                for (AreaCodeJpo areaCodeJpo : areaCodeList){
                    int count = 0;
                    double contractPrice = 0.0;
                    for (ContractJpo contractJpo : contractList) {
                        int contractAreaCodeSido = contractJpo.getBuilding().getAreaCode().getCode().getSido();
                        int areaCodeSido = areaCodeJpo.getCode().getSido();
                        int contractAreaCodeSgg = contractJpo.getBuilding().getAreaCode().getCode().getSgg();
                        int areaCodeSgg = areaCodeJpo.getCode().getSgg();
                        if (contractAreaCodeSido == areaCodeSido) {
                            if(contractAreaCodeSgg == areaCodeSgg) {
                                count++;
                                contractPrice += contractJpo.getDetail().getPrice();
                            }
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

            case 2:
                contractList = contractJpaRepository.findByBuildingAreaCodeUmd(umdLists);
                for (AreaCodeJpo areaCodeJpo : areaCodeList){
                    int count = 0;
                    double contractPrice = 0.0;
                    for (ContractJpo contractJpo : contractList) {
                        int contractAreaCodeSido = contractJpo.getBuilding().getAreaCode().getCode().getSido();
                        int areaCodeSido = areaCodeJpo.getCode().getSido();
                        int contractAreaCodeSgg = contractJpo.getBuilding().getAreaCode().getCode().getSgg();
                        int areaCodeSgg = areaCodeJpo.getCode().getSgg();
                        int contractAreaCodeUmd = contractJpo.getBuilding().getAreaCode().getCode().getUmd();
                        int areaCodeUmd = areaCodeJpo.getCode().getUmd();
                        if (contractAreaCodeSido == areaCodeSido) {
                            if(contractAreaCodeSgg == areaCodeSgg) {
                                if(contractAreaCodeUmd == areaCodeUmd) {
                                    count++;
                                    contractPrice += contractJpo.getDetail().getPrice();
                                }
                            }
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
        }

        return areaContractList;
    }
}
