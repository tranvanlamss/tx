package com.vietsoft.repo;


import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.vietsoft.model.master.Country;

@RepositoryRestResource(exported = false)
//@Cacheable(value = "GlobalCache")
public interface CountryRepo extends PagingAndSortingRepository<Country, String> {
}
