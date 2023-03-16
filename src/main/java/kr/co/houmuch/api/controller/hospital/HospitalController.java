package kr.co.houmuch.api.controller.hospital;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.houmuch.api.constant.ErrorCode;
import kr.co.houmuch.api.controller.response.ApiResponse;
import kr.co.houmuch.api.service.hospital.HospitalService;
import kr.co.houmuch.api.swagger.SwaggerApiInfo;
import kr.co.houmuch.core.domain.hospital.dto.Hospital;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/hospital")
@Api(tags = SwaggerApiInfo.HOSPITAL)
@CrossOrigin
public class HospitalController {
    private final HospitalService hospitalService;

    @GetMapping
    @ApiOperation(value = SwaggerApiInfo.GET_HOSPITAL_LIST, notes = SwaggerApiInfo.GET_HOSPITAL_LIST)
    public ResponseEntity<ApiResponse<List<Hospital>>> hospitalFetchList(
            @RequestParam(defaultValue = "0") double maxLatitude
            , @RequestParam(defaultValue = "0") double minLatitude
            , @RequestParam(defaultValue = "0") double maxLongitude
            , @RequestParam(defaultValue = "0") double minLongitude
    ){
        if(maxLatitude != 0 && minLatitude != 0 && maxLongitude != 0 && minLongitude != 0){
            return ResponseEntity.ok(
                    ApiResponse.of(hospitalService.fetch(maxLatitude, minLatitude, maxLongitude, minLongitude)));
        }
        return ResponseEntity.ok(ApiResponse.failure(ErrorCode.CODE_C1000));
    }
}
