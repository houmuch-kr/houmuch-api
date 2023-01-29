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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapService {
    private final AreaCodeJpaRepository areaCodeJpaRepository;
    public List<AreaContract> fetchList(int type){
        PageRequest pageable = PageRequest.of(0,30);

		List<Integer> sidoList = areaCodeJpaRepository
				.findSido(pageable)
				.stream()
				.map(areaCodeJpo -> areaCodeJpo.getCode().getSido())
				.collect(Collectors.toList());
		System.out.println("sidoList---->" + sidoList);

		List<Integer> sggList = areaCodeJpaRepository
				.findSgg(pageable)
				.stream()
				.map(areaCodeJpo -> areaCodeJpo.getCode().getSgg())
				.collect(Collectors.toList());
		System.out.println("sggList---->" + sggList);

		List<AreaCodeJpo> findList = new ArrayList<>();

		for(int sidoInt : sidoList){
			System.out.println("sidoInt--->" + sidoInt);
			for(int sggInt : sggList){
				findList.addAll(areaCodeJpaRepository.findByCodeSidoAndCodeSgg(sidoInt, sggInt, pageable).getContent());
				System.out.println("뭐나올랑가?----->" + findList);
			}
		}

		List<AreaContract> areaList = findList.stream().map(AreaContract::entityOf).collect(Collectors.toList());
		return areaList;
    }
}