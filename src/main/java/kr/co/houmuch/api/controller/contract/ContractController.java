package kr.co.houmuch.api.controller.contract;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import kr.co.houmuch.api.constant.ErrorCode;
import kr.co.houmuch.api.controller.response.ApiResponse;
import kr.co.houmuch.api.domain.dto.contract.*;
import kr.co.houmuch.api.service.ContractSummaryService;
import kr.co.houmuch.api.service.contract.ContractAreaFetchService;
import kr.co.houmuch.api.service.contract.ContractBuildingFetchService;
import kr.co.houmuch.api.swagger.SwaggerApiInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/contract")
@Api(tags = SwaggerApiInfo.CONTRACT)
@CrossOrigin
public class ContractController {
    private final ContractAreaFetchService contractAreaFetchService;
    private final ContractBuildingFetchService contractBuildingFetchService;
    private final ContractSummaryService contractSummaryService;

    @GetMapping(path = "/process/index")
    @ApiOperation(value = SwaggerApiInfo.GET_PROCESS_INDEX, notes = SwaggerApiInfo.GET_PROCESS_INDEX)
    public ResponseEntity<ApiResponse<Void>> processIndex() {
        contractSummaryService.indexV2();
        return ResponseEntity
                .accepted()
                .body(ApiResponse.empty());
    }

    @GetMapping(path = "/fetchList")
    @ApiOperation(value = SwaggerApiInfo.GET_AREA_CONTRACT_LIST, notes = SwaggerApiInfo.GET_AREA_CONTRACT_LIST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", required = true, example = "0")
    })
    public ResponseEntity<ApiResponse<List<? extends Summary>>> fetchList(
            @RequestParam(name = "type", required = false, defaultValue = "0") int type
            , @RequestParam(defaultValue = "0") double maxLatitude
            , @RequestParam(defaultValue = "0") double minLatitude
            , @RequestParam(defaultValue = "0") double maxLongitude
            , @RequestParam(defaultValue = "0") double minLongitude)
    {
        if(maxLatitude != 0 && minLatitude != 0 && maxLongitude != 0 && minLongitude != 0){
            return ResponseEntity.ok(
                    ApiResponse.of(contractSummaryService.fetch(type, maxLatitude, minLatitude, maxLongitude, minLongitude)));
    }
        return ResponseEntity.ok(ApiResponse.failure(ErrorCode.CODE_C1000));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<AreaContractList>> get(
            @RequestParam long areaCode,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(
                ApiResponse.of(contractAreaFetchService.fetch(areaCode, page, size)));
    }

    @GetMapping(path = "/{buildingId}")
    public ResponseEntity<ApiResponse<BuildingContractList>> getBuilding(
            @PathVariable("buildingId") String buildingId,
            @RequestParam int page,
            @RequestParam int size) {
        return ResponseEntity.ok(
                ApiResponse.of(contractBuildingFetchService.fetch(buildingId, page, size)));
    }

    @GetMapping(path = "/summary")
    public ResponseEntity<ApiResponse<AreaContractSummary>> getSummary(@RequestParam long areaCode) {
        return ResponseEntity.ok(
                ApiResponse.of(contractAreaFetchService.fetchSummary(areaCode)));
    }

    @GetMapping(path = "/summary/{buildingId}")
    public ResponseEntity<ApiResponse<BuildingContractSummary>> getBuildingSummary(@PathVariable("buildingId") String buildingId) {
        return ResponseEntity.ok(
                ApiResponse.of(contractBuildingFetchService.fetchSummary(buildingId)));
    }

    @GetMapping(path = "/trend")
    public ResponseEntity<ApiResponse<AreaContractTrend>> getTrend(@RequestParam long areaCode) {
        return ResponseEntity.ok(
                ApiResponse.of(contractAreaFetchService.fetchTrend(areaCode)));
    }

    @GetMapping(path = "/trend/{buildingId}")
    public ResponseEntity<ApiResponse<BuildingContractTrend>> getBuildingTrend(@PathVariable("buildingId") String buildingId) {
        return ResponseEntity.ok(
                ApiResponse.of(contractBuildingFetchService.fetchTrend(buildingId)));
    }
}
