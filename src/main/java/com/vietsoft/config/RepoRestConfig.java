package com.vietsoft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.ExposureConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;

import com.vietsoft.model.Role;

@Configuration
public class RepoRestConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config);
		
		ExposureConfiguration expConfig = config.getExposureConfiguration();
		expConfig.forDomainType(Role.class).withItemExposure((metadata, httpMethods) ->
          httpMethods.disable(HttpMethod.PATCH));
		
		config.setReturnBodyForPutAndPost(true);
		config.setReturnBodyOnCreate(true);
		config.setReturnBodyOnUpdate(true);
		config.setBasePath("/api/v1");
		
		//config.setDefaultMediaType(MediaType.APPLICATION_JSON);
        //config.useHalAsDefaultJsonMediaType(false);
	}
	
	
}
