package com.vietsoft.repo;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vietsoft.model.master.Province;


@RepositoryRestResource(exported = false)
@Cacheable(value = "GlobalCache")
public interface ProvinceRepo extends PagingAndSortingRepository<Province, String> {
    @Query(value = "select distinct p.area from Province p")
    List<String> getAllAreas();
    List<Province> getProvinceByName(@Param("name")String name);
}