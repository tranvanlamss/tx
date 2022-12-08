package com.vietsoft.repo;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vietsoft.model.master.District;

@RepositoryRestResource(exported = false)
@Cacheable(value = "GlobalCache")
public interface DistrictRepo extends PagingAndSortingRepository<District, String> {
//    List<District> findAllByNameIn(@Param("names") List<String> names);
    List<District> findByProvinceId(@Param("provinceId") String provinceId);
}