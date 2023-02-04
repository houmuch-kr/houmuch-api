package kr.co.houmuch.api.service;

import kr.co.houmuch.api.domain.dto.map.AreaContract;
import kr.co.houmuch.core.domain.code.AreaCodeJpaRepository;
import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import kr.co.houmuch.core.domain.code.dto.AreaCode;
import kr.co.houmuch.core.domain.contract.dto.Contract;
import kr.co.houmuch.core.domain.contract.jpa.ContractDetailJpo;
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
		private final ContractJpaRepository contractJpaRepository;

		public List<AreaContract> fetch(int type) {
				List<AreaCodeJpo> areaCodeList = areaCodeJpaRepository
								.findByType2(type);
				List<AreaContract> areaContractList = new ArrayList<>();
				List<ContractJpo> contractList = contractJpaRepository
								.findByBuildingAreaCode2(areaCodeList.subList(0, 100));
				for (ContractJpo contractJpo : contractList) {
						AreaContract areaContract = AreaContract.builder()
										.areaCode(AreaCode.entityOf(contractJpo.getBuilding().getAreaCode()))
										.count(contractList.size())
										.price(contractList.stream()
														.map(ContractJpo::getDetail)
														.mapToInt(ContractDetailJpo::getPrice)
														.average()
														.orElse(0))
										.contracts(contractList.stream().map(Contract::entityOf).collect(Collectors.toList()))
										.build();
						areaContractList.add(areaContract);
				}
				return areaContractList;
		}

		public List<AreaContract> fetchList(int type) {
				PageRequest pageable = PageRequest.of(0, 30);
				System.out.println("getContent----->" + areaCodeJpaRepository.findSido(pageable).getContent());
				System.out.println("getTotalElements----->" + areaCodeJpaRepository.findSido(pageable).getTotalElements());

//				List<Long> areaCodeList = areaCodeJpaRepository
//								.findSido(pageable)
//								.stream()
//								.map(areaCodeJpo -> areaCodeJpo.getId())
//								.collect(Collectors.toList());
//				System.out.println("areaCodeList--->" + areaCodeList);
//

//				List<AreaCodeJpo> areaCodeList22 = areaCodeJpaRepository.findAll(pageable).getContent();
//				System.out.println("areaCodeList22->"+areaCodeList22);
//
//				List<ContractJpo> findContractList = contractJpaRepository.findByBuildingAreaCode(areaCodeList22.get(0), pageable).getContent();
//				System.out.println("findContractList----->" + findContractList);


				List<AreaCodeJpo> findAreaCodeList = new ArrayList<>();

				List<Integer> sidoList = areaCodeJpaRepository
								.findSido(pageable)
								.stream()
								.map(areaCodeJpo -> areaCodeJpo.getCode().getSido())
								.collect(Collectors.toList());
				for (int sidoInt : sidoList
				) {
						findAreaCodeList.addAll(areaCodeJpaRepository.findByCodeSido(sidoInt, pageable).getContent());
						System.out.println("findAreaCodeList----->" + findAreaCodeList);
				}
//    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
				List<ContractJpo> findContractList = new ArrayList<>();
				for (AreaCodeJpo areaCodeJpo : findAreaCodeList
				) {
						findContractList.addAll(contractJpaRepository.findByBuildingAreaCode(areaCodeJpo, pageable).getContent());
						System.out.println("findContractList---->" + findContractList);
				}

//				List<Integer> sidoList = areaCodeJpaRepository
//								.findSido(pageable)
//								.stream()
//								.map(areaCodeJpo -> areaCodeJpo.getCode().getSido())
//								.collect(Collectors.toList());
//				System.out.println("sidoList---->" + sidoList);
//
//				List<Integer> sggList = areaCodeJpaRepository
//								.findSgg(pageable)
//								.stream()
//								.map(areaCodeJpo -> areaCodeJpo.getCode().getSgg())
//								.collect(Collectors.toList());
//				System.out.println("sggList---->" + sggList);
//
//				List<AreaCodeJpo> findAreaCodeList = new ArrayList<>();
//
//				for (int sidoInt : sidoList) {
////						System.out.println("sidoInt--->" + sidoInt);
//						System.out.println("00000------>" + buildingJpaRepository.findById(sidoList));
//						for (int sggInt : sggList) {
//								findAreaCodeList.addAll(areaCodeJpaRepository.findByCodeSidoAndCodeSgg(sidoInt, sggInt, pageable).getContent());
////								System.out.println("뭐나올랑가?----->" + findAreaCodeList);
//						}
//				}
//
//				List<ContractJpo> findConstractList = new ArrayList<>();
//				findConstractList = contractJpaRepository.findById(pageable);
//				contractJpaRepository.findAll(findAreaCodeList, pageable);
//				System.out.println("findConstractList---->" + contractJpaRepository.findAll(findAreaCodeList, pageable));

				List<AreaContract> areaList = findContractList.stream().map(AreaContract::entityOf).collect(Collectors.toList());
				return areaList;
//				return null;
		}
}
