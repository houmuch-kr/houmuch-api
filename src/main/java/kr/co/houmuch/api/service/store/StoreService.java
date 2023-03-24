package kr.co.houmuch.api.service.store;

import kr.co.houmuch.core.client.store.StoreApiClient;
import kr.co.houmuch.core.domain.common.jpa.CodeJpaRepository;
import kr.co.houmuch.core.domain.common.jpa.IndustryCodeJpaRepository;
import kr.co.houmuch.core.domain.store.jpa.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    //https://apis.data.go.kr/B553077/api/open/sdsc2/storeListInUpjong?serviceKey=KHEIPmfhDTJdwnPh0WTTMN1YOUMy9rE%2FMJGErHp2pdcfw6uzit5C7yivZg1iUbTVRLXNSgn0A%2Bdi8lCAkdv3Zg%3D%3D&pageNo=1&numOfRows=100&divId=indsSclsCd&key=D03A01&type=json"
    private final StoreApiClient storeApiClient;
    private final StoreJpaRepository storeJpaRepository;
    private final StoreCoordinateJpaRepository storeCoordinateJpaRepository;
    private final StoreAddressJpaRepository storeAddressJpaRepository;
    private final StoreAddressDetailJpaRepository storeAddressDetailJpaRepository;
    private final CodeJpaRepository codeJpaRepository;
    private final IndustryCodeJpaRepository industryCodeJpaRepository;

    @Transactional
    public void fetch() {
        List<StoreJpo> storeList = storeApiClient.fetchStore(1, 1)
               // .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(-1)) // to unlimited memory size
                .map(body -> body.getItems().stream()
                        .map(item -> item.asJpo())
                        .collect(Collectors.toList()))
                .block();
      //  storeJpaRepository.saveAll(storeList); 
    }

}
