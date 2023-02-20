package kr.co.houmuch.api.controller.contract;

import kr.co.houmuch.api.controller.response.ApiResponse;
import kr.co.houmuch.api.domain.dto.contract.*;
import kr.co.houmuch.api.domain.dto.map.AreaContract;
import kr.co.houmuch.api.service.MapService;
import kr.co.houmuch.api.service.contract.ContractAreaFetchService;
import kr.co.houmuch.api.service.contract.ContractBuildingFetchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/contract")
@CrossOrigin
public class ContractController {
    private final ContractAreaFetchService contractAreaFetchService;
    private final ContractBuildingFetchService contractBuildingFetchService;
    private final MapService mapService;
    @GetMapping(path = "/fetchList")
    public ResponseEntity<ApiResponse<List<AreaContract>>> fetchList(@RequestParam(name = "type", required = false, defaultValue = "0") int type){
        return ResponseEntity.ok(
                ApiResponse.of(mapService.fetch(type)));
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
