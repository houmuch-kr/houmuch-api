package kr.co.houmuch.api.service;

import kr.co.houmuch.api.domain.dto.map.AreaContract;
import kr.co.houmuch.core.domain.code.AreaCodeJpaRepository;
import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import kr.co.houmuch.core.domain.contract.ContractJpaRepository;
import kr.co.houmuch.core.domain.contract.ContractJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapService {
    private final AreaCodeJpaRepository areaCodeJpaRepository;
    private final ContractJpaRepository contractJpaRepository;
//    private ContractJpo contractJpo;
//    private AreaCodeJpo areaCodeJpo;
    public List<?> fetchAsync(int type){
        PageRequest pageable = PageRequest.of(0,10);
        // 초기화 해주는 것이 맞는가?
        int sido = 0;
        int sgg = 0;
        int umd = 0;


        //List<?> areaList = areaCodeJpaRepository.findByCodeSidoAndCodeSggAndCodeUmd(sido, sgg, umd, pageable).getContent();

        System.out.println("----------findSido----------");
        System.out.println(areaCodeJpaRepository.findSido(pageable).getContent());
        System.out.println("----------findSido----------");

        System.out.println("----------findSgg----------");
        System.out.println(areaCodeJpaRepository.findSgg(pageable).getContent());
        System.out.println("----------findSgg----------");

        System.out.println("----------findUmd----------");
        System.out.println(areaCodeJpaRepository.findUmd(pageable).getContent());
        System.out.println("----------findUmd----------");
        areaCodeJpaRepository.findUmd(pageable).getContent().forEach(areaCodeJpo -> {

            System.out.println("areaCodeJpo--->" + areaCodeJpo);
            System.out.println("sido---->" + sido);

        });

        System.out.println("----------2222----------");
        //System.out.println("areaList---->" + areaList);
        System.out.println("----------2222----------");

//        System.out.println("리스트에 값을 꺼내보자!----->" + areaList.get(0));  // 에러남

        List<ContractJpo> findSido = contractJpaRepository.findAll(pageable).getContent();
        System.out.println(")))))))))))))findSido)))))))))" + findSido);
//        System.out.println("entityOf----->" + AreaContract.entityOf(areaCodeJpo, contractJpo));
//        List<ContractJpo> findContract = contractJpaRepository.findAll();
        List<AreaContract> areaList = findSido.stream().map(AreaContract::entityOf).collect(Collectors.toList());
        return areaList;
    }
}