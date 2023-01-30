package kr.co.houmuch.api.service;

import kr.co.houmuch.api.domain.dto.map.AreaContract;
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
    private final ContractJpaRepository contractJpaRepository;
    public AreaContract fetchList(){
        PageRequest pageable = PageRequest.of(0,5);

        // sido, sgg, umg 전국 리스트 조회
        List<AreaCodeJpo> findAreaCodeList = new ArrayList<>();
        findAreaCodeList.addAll(areaCodeJpaRepository.findSido(pageable).getContent());
        findAreaCodeList.addAll(areaCodeJpaRepository.findSgg(pageable).getContent());
        findAreaCodeList.addAll(areaCodeJpaRepository.findUmd(pageable).getContent());

        // building 전체 리스트 조회
        List<ContractJpo> findContractList=contractJpaRepository.findAll(pageable).getContent();

        AreaContract FetchAll = AreaContract.of(findAreaCodeList,findContractList);

        return FetchAll;
    }
}