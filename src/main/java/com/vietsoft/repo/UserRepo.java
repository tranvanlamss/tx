package com.vietsoft.repo;

import com.vietsoft.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UserRepo extends PagingAndSortingRepository<User, String> {

	Optional<User> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

	Optional<User> findFirstByEmail(@Param("query") String query);
	Optional<User> findByUsername(@Param("query") String query);
	Optional<User> findByEmailVerifiedToken(@Param("query") String query);
}