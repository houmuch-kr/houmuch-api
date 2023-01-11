package kr.co.houmuch.api.controller.map;

import kr.co.houmuch.core.domain.code.AreaCodeJpaRepository;
import kr.co.houmuch.core.domain.code.AreaCodeJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/v1/map")
@RequiredArgsConstructor
public class MapController {
    private final AreaCodeJpaRepository areaCodeJpaRepository;
    @GetMapping(path = "/fetchSido")
    public List<?> fetchSido(){
        PageRequest pageRequest = PageRequest.of(0,5);
        System.out.println("11111--->" + areaCodeJpaRepository.findSidoAll(pageRequest).getContent());
        return areaCodeJpaRepository.findSidoAll(pageRequest).getContent();
    }

    @GetMapping(path = "/fetchSgg")
    public void fetchSgg() {
    }
    @GetMapping(path = "/fetchUmd")
    public void fetchUmd() {
    }
    @GetMapping(path = "/fetchIndividualHouse")
    public void fetchIndividualHouse() {
    }
}
