package com.kh.Palette_BackEnd.service;

import com.kh.Palette_BackEnd.dto.reqdto.DateCourseReqDto;
import com.kh.Palette_BackEnd.dto.resdto.DateCourseResDto;
import com.kh.Palette_BackEnd.entity.DateCourseEntity;
import com.kh.Palette_BackEnd.entity.PlaceEntity;
import com.kh.Palette_BackEnd.repository.DateCourseRepository;
import com.kh.Palette_BackEnd.repository.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DateCourseService {
    @Autowired
    private DateCourseRepository dateCourseRepository;

    @Autowired
    private PlaceRepository placeRepository;

    // 코스와 장소를 생성하고 저장하는 메소드
    @Transactional
    public DateCourseResDto createCourse(DateCourseReqDto reqCourseDTO) {
        DateCourseEntity dateCourseEntity = new DateCourseEntity();
        dateCourseEntity.setTitle(reqCourseDTO.getTitle());
        DateCourseEntity savedCourse = dateCourseRepository.save(dateCourseEntity);

        int order = 1;
        for (DateCourseReqDto.PlaceDTO placeDTO : reqCourseDTO.getPlaces()) {
            PlaceEntity placeEntity = new PlaceEntity();
            placeEntity.setPlace_name(placeDTO.getPlace_name());
            placeEntity.setRoad_address_name(placeDTO.getRoad_address_name());
            placeEntity.setPhone(placeDTO.getPhone());
            placeEntity.setPlace_url(placeDTO.getPlace_url());
            placeEntity.setX(placeDTO.getX());
            placeEntity.setY(placeDTO.getY());
            placeEntity.setDateCourse(savedCourse);
            placeEntity.setPlaceOrder(order++);
            placeRepository.save(placeEntity);
        }

        return convertToResCourseDTO(savedCourse);
    }

    // 모든 코스를 조회하는 메소드
    public List<DateCourseResDto> getAllCourses() {
        return dateCourseRepository.findAll().stream()
                .map(this::convertToResCourseDTO)
                .collect(Collectors.toList());
    }

    // 특정 ID로 코스를 조회하는 메소드
    public Optional<DateCourseResDto> getCourseById(Long id) {
        return dateCourseRepository.findById(id)
                .map(this::convertToResCourseDTO);
    }

    // 코스와 장소를 수정하는 메소드
    @Transactional
    public DateCourseResDto updateCourse(Long id, DateCourseReqDto reqCourseDTO) {
        Optional<DateCourseEntity> courseOpt = dateCourseRepository.findById(id);
        if (courseOpt.isPresent()) {
            DateCourseEntity existingCourse = courseOpt.get();
            existingCourse.setTitle(reqCourseDTO.getTitle());
            existingCourse.getPlaces().clear();
            placeRepository.deleteAllByDateCourseId(id);

            int order = 1;
            for (DateCourseReqDto.PlaceDTO placeDTO : reqCourseDTO.getPlaces()) {
                PlaceEntity placeEntity = new PlaceEntity();
                placeEntity.setPlace_name(placeDTO.getPlace_name());
                placeEntity.setRoad_address_name(placeDTO.getRoad_address_name());
                placeEntity.setPhone(placeDTO.getPhone());
                placeEntity.setPlace_url(placeDTO.getPlace_url());
                placeEntity.setX(placeDTO.getX());
                placeEntity.setY(placeDTO.getY());
                placeEntity.setDateCourse(existingCourse);
                placeEntity.setPlaceOrder(order++);
                placeRepository.save(placeEntity);
            }

            return convertToResCourseDTO(dateCourseRepository.save(existingCourse));
        } else {
            throw new RuntimeException("Course not found with id " + id);
        }
    }

    // 특정 코스를 삭제하는 메소드
    @Transactional
    public void deleteCourse(Long id) {
        placeRepository.deleteAllByDateCourseId(id);
        dateCourseRepository.deleteById(id);
    }

    // DateCourseEntity를 DateCourseResDto로 변환하는 메소드
    private DateCourseResDto convertToResCourseDTO(DateCourseEntity dateCourseEntity) {
        DateCourseResDto resCourseDTO = new DateCourseResDto();
        resCourseDTO.setId(dateCourseEntity.getId());
        resCourseDTO.setTitle(dateCourseEntity.getTitle());
        resCourseDTO.setDate(dateCourseEntity.getDate());
        resCourseDTO.setPlaces(dateCourseEntity.getPlaces().stream()
                .map(this::convertToPlaceDTO)
                .collect(Collectors.toList()));
        return resCourseDTO;
    }

    // PlaceEntity를 DateCourseResDto.PlaceDTO로 변환하는 메소드
    private DateCourseResDto.PlaceDTO convertToPlaceDTO(PlaceEntity placeEntity) {
        DateCourseResDto.PlaceDTO placeDTO = new DateCourseResDto.PlaceDTO();
        placeDTO.setId(placeEntity.getPlaceId());
        placeDTO.setPlace_name(placeEntity.getPlace_name());
        placeDTO.setRoad_address_name(placeEntity.getRoad_address_name());
        placeDTO.setPhone(placeEntity.getPhone());
        placeDTO.setPlace_url(placeEntity.getPlace_url());
        placeDTO.setX(placeEntity.getX());
        placeDTO.setY(placeEntity.getY());
        placeDTO.setPlaceOrder(placeEntity.getPlaceOrder());
        return placeDTO;
    }
}
