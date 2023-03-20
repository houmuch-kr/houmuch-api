package kr.co.houmuch.api.service.store;

import kr.co.houmuch.core.client.store.StoreApiClient;
import kr.co.houmuch.core.client.store.payload.StorePayload;
import kr.co.houmuch.core.domain.store.jpa.StoreJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {
    //https://apis.data.go.kr/B553077/api/open/sdsc2/storeListInUpjong?serviceKey=KHEIPmfhDTJdwnPh0WTTMN1YOUMy9rE%2FMJGErHp2pdcfw6uzit5C7yivZg1iUbTVRLXNSgn0A%2Bdi8lCAkdv3Zg%3D%3D&pageNo=1&numOfRows=100&divId=indsSclsCd&key=D03A01&type=json"
    private final StoreJpaRepository storeJpaRepository;
    private final StoreApiClient storeApiClient;
    @Transactional
    public void index() {
        List<StorePayload.Body.Store> storeList = new ArrayList<>();
       storeApiClient.fetchStore(1, 100).subscribe(System.out::println);
    //   storeApiClient.fetchStore(1, 100).subscribe(v->storeList.add(v.getItems()));
    }

}
