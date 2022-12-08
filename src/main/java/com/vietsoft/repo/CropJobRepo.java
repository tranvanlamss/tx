package com.vietsoft.repo;

import com.vietsoft.model.CropJob;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface CropJobRepo extends PagingAndSortingRepository<CropJob, String> {

}