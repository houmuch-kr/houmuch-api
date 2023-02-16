package kr.co.houmuch.api.controller.contract;

import kr.co.houmuch.api.controller.response.ApiResponse;
import kr.co.houmuch.api.domain.dto.contract.*;
import kr.co.houmuch.api.service.contract.ContractAreaFetchService;
import kr.co.houmuch.api.service.contract.ContractBuildingFetchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/contract")
@CrossOrigin
public class ContractController {
    private final ContractAreaFetchService contractAreaFetchService;
    private final ContractBuildingFetchService contractBuildingFetchService;

    @GetMapping
    public ResponseEntity<ApiResponse<AreaContractList>> get(@RequestParam long areaCode) {
        return ResponseEntity.ok(
                ApiResponse.of(contractAreaFetchService.fetch(areaCode)));
    }

    @GetMapping(path = "/{buildingId}")
    public ResponseEntity<ApiResponse<BuildingContractList>> getBuilding(@PathVariable("buildingId") String buildingId) {
        return ResponseEntity.ok(
                ApiResponse.of(contractBuildingFetchService.fetch(buildingId)));
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
