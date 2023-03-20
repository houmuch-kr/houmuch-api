package kr.co.houmuch.api.controller.store;

import io.swagger.annotations.Api;
import kr.co.houmuch.api.controller.response.ApiResponse;
import kr.co.houmuch.api.service.store.StoreService;
import kr.co.houmuch.api.swagger.SwaggerApiInfo;
import kr.co.houmuch.core.client.naver.NaverMapApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/store")
@Api(tags = SwaggerApiInfo.STORE)
public class StoreController {
    private final StoreService storeService;
    private final NaverMapApiClient naverMapApiClient;

    @GetMapping(path = "/process/index")
    //@ApiOperation(value = SwaggerApiInfo., notes = SwaggerApiInfo.)
    public ResponseEntity<ApiResponse<Void>> processIndex() {
        storeService.index();

//            naverMapApiClient.fetchGeocode("서울특별시 종로구 익선동").subscribe(v->{
//                System.out.println("v-->"+v);
//            });
        return ResponseEntity
                .accepted()
                .body(ApiResponse.empty());
    }

//    @GetMapping
//    public ResponseEntity<ApiResponse<T>> getStore(){
//        return ResponseEntity.ok(
//                ApiResponse.of(storeService.fetchStore()));
//    }


}
