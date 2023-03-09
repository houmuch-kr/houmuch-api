package kr.co.houmuch.api.service.search;

import kr.co.houmuch.api.domain.dto.search.Search;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpaRepository;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final BuildingJpaRepository buildingJpaRepository;
    public List<String> searchList(String searchWord){
        List<String> buildingNameList = new ArrayList<>();
        List<BuildingJpo> searchList = buildingJpaRepository.findByName(searchWord);
        for(BuildingJpo buildingName : searchList){
            String name = buildingName.getName();
            buildingNameList.add(name);
        }
        return buildingNameList;
    }
}