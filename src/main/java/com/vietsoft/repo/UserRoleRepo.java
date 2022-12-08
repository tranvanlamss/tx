package com.vietsoft.repo;

import com.vietsoft.model.UserRole;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(exported = false)
@Cacheable(value = "GlobalCache")
public interface UserRoleRepo extends PagingAndSortingRepository<UserRole, String> {

    List<UserRole> findByUserId(String userId);
}