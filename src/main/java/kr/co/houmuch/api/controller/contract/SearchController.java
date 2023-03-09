package kr.co.houmuch.api.controller.contract;

import io.swagger.annotations.*;
import kr.co.houmuch.api.controller.response.ApiResponse;
import kr.co.houmuch.api.domain.dto.search.Search;
import kr.co.houmuch.api.service.search.SearchService;
import kr.co.houmuch.api.swagger.SwaggerApiInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/search")
@Api(tags = SwaggerApiInfo.SEARCH)
public class SearchController {
    private final SearchService searchService;
    @GetMapping
    @ApiOperation(value = SwaggerApiInfo.GET_SEARCH_LIST, notes = SwaggerApiInfo.GET_SEARCH_LIST)
    @ApiImplicitParams({@ApiImplicitParam(name = "searchWord", required = true, example = "한강")})
    public ResponseEntity<ApiResponse<List<String> >> searchList(
            @RequestParam(name = "searchWord", required = true, defaultValue = "한강") String searchWord
    )
    {
        return ResponseEntity.ok(
                ApiResponse.of(searchService.searchList(searchWord)));
    }

}
