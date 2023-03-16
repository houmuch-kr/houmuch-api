package kr.co.houmuch.api.service.hospital;

import kr.co.houmuch.core.domain.hospital.dto.Hospital;
import kr.co.houmuch.core.domain.hospital.jpa.HospitalJpaRepository;
import kr.co.houmuch.core.domain.hospital.jpa.HospitalJpo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalJpaRepository hospitalJpaRepository;
    public List<Hospital> fetch(double maxLatitude, double minLatitude, double maxLongitude, double minLongitude){
        List<HospitalJpo> hospitalJpoList = hospitalJpaRepository.findByCoordinate(maxLatitude, minLatitude, maxLongitude, minLongitude);
        List<Hospital> hospitalList = hospitalJpoList.stream().map(Hospital::entityOf).toList();
        return hospitalList;
    }
}
