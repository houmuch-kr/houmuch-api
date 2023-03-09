package kr.co.houmuch.api.service.search;

import kr.co.houmuch.core.domain.building.jpa.BuildingJpaRepository;
import kr.co.houmuch.core.domain.building.jpa.BuildingJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final BuildingJpaRepository buildingJpaRepository;
    public List<String> searchList(String keyword, Pageable pageable){
        List<String> buildingNameList = new ArrayList<>();
        List<BuildingJpo> searchList = buildingJpaRepository.findByName(keyword, pageable);
        for(BuildingJpo buildingName : searchList){
            String name = buildingName.getName();
            buildingNameList.add(name);
        }
        return buildingNameList;
    }
}