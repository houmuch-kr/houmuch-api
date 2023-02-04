package kr.co.houmuch.api.controller.map;

import io.swagger.annotations.Api;
import kr.co.houmuch.api.service.MapService;
import kr.co.houmuch.api.swagger.SwaggerApiInfo;
import kr.co.houmuch.core.domain.code.AreaCodeJpaRepository;
import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping(path = "/v1/map")
@RequiredArgsConstructor
@Api(tags = SwaggerApiInfo.MAP_LIST)
@CrossOrigin(origins = "*", methods = RequestMethod.GET)
public class MapController {
    private final AreaCodeJpaRepository areaCodeJpaRepository;
    private final MapService mapService;
    @GetMapping(path = "/fetchList")
    public List<?> fetchList(@RequestParam(name = "type", required = false, defaultValue = "0") int type){
        return mapService.fetch(type);
    }
}
