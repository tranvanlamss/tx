package com.vietsoft.repo;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vietsoft.model.master.Ward;


@RepositoryRestResource(exported = false)
@Cacheable(value = "GlobalCache")
public interface WardRepo extends PagingAndSortingRepository<Ward, String> {
//    List<Ward> findAllByNameIn(@Param("names") List<String> names);
    List<Ward> findByDistrictId(@Param("districtId")String districtId);
    List<Ward> findByProvinceIdAndDistrictId(@Param("provinceId")String provinceId, @Param("districtId")String districtId);
}