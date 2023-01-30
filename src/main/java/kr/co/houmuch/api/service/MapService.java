package kr.co.houmuch.api.service;

import kr.co.houmuch.api.domain.dto.map.AreaContract;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpaRepository;
import kr.co.houmuch.core.domain.code.AreaCodeJpaRepository;
import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpaRepository;
import kr.co.houmuch.core.domain.contract.jpa.ContractJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {
    private final AreaCodeJpaRepository areaCodeJpaRepository;
    private final BuildingJpaRepository buildingJpaRepository;
    private final ContractJpaRepository contractJpaRepository;
    public AreaContract fetchList(int type){
        PageRequest pageable = PageRequest.of(0,5);
        // 초기화 해주는 것이 맞는가?
//        int sido = 0;
//        int sgg = 0;
//        int umd = 0;
//		List<ContractJpo> findSido = contractJpaRepository.findAll(pageable).getContent();
//		System.out.println("findSido--->" + findSido);
//
//		List<AreaCodeJpo> findSido2 = areaCodeJpaRepository.findSido(pageable).getContent();
//		System.out.println("findSido2---->" + findSido2);
//
//		List<AreaCodeJpo> findSido3 = areaCodeJpaRepository.findByCodeSido(11, pageable).getContent();
//		System.out.println("findSido3------>" + findSido3);
//
//		List<AreaCodeJpo> findSido4 = areaCodeJpaRepository.findByCodeSidoAndCodeSgg(11, 110, pageable).getContent();
//		System.out.println("findSido4------>" + findSido4);
//
//		List<AreaCodeJpo> findSido5 = areaCodeJpaRepository.findByCodeSidoAndCodeSggAndCodeUmd(11, 110, 10100, pageable).getContent();
//		System.out.println("findSido5----->" + findSido5);

//        List<Integer> sidoList = areaCodeJpaRepository
//                .findSido(pageable)
//                .stream()
//                .map(areaCodeJpo -> areaCodeJpo.getCode().getSido())
//                .collect(Collectors.toList());
//        System.out.println("sidoList---->" + sidoList);
//
//        List<Integer> sggList = areaCodeJpaRepository
//                .findSgg(pageable)
//                .stream()
//                .map(areaCodeJpo -> areaCodeJpo.getCode().getSgg())
//                .collect(Collectors.toList());
//        System.out.println("sggList---->" + sggList);
//
//        List<Integer> umdList = areaCodeJpaRepository
//                .findUmd(pageable)
//                .stream()
//                .map(areaCodeJpo -> areaCodeJpo.getCode().getUmd())
//                .collect(Collectors.toList());
//        System.out.println("umdList---->" + umdList);

//        // SSU 전체 리스트 조회
//        List<AreaCodeJpo> findList = new ArrayList<>();
//        for(int sidoInt : sidoList){
//            System.out.println("sidoInt--->" + sidoInt);
//            for(int sggInt : sggList){
//                findList.addAll(areaCodeJpaRepository.findByCodeSidoAndCodeSgg(sidoInt, sggInt, pageable).getContent());
//                System.out.println("뭐나올랑가?----->" + findList);
//            }
//        }
//
//        System.out.println("findList = " + findList);

// 아,,, 약간 비효율적인가 다 더하는게?
        List<AreaCodeJpo> findAreaCodeList = new ArrayList<>();
        findAreaCodeList.addAll(areaCodeJpaRepository.findSido(pageable).getContent());
        findAreaCodeList.addAll(areaCodeJpaRepository.findSgg(pageable).getContent());
        findAreaCodeList.addAll(areaCodeJpaRepository.findUmd(pageable).getContent());
//        System.out.println("findList = " + findList);
//

        // building 전체 리스트 조회
        List<ContractJpo> findContractList=contractJpaRepository.findAll(pageable).getContent();
//        System.out.println("fetchContract = " + fetchContract);


        AreaContract FetchAll = AreaContract.of(findAreaCodeList,findContractList);


        return FetchAll;
    }
}