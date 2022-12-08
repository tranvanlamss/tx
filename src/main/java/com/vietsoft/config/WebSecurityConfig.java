package com.vietsoft.config;

import com.vietsoft.security.LocalTokenAuthFilter;
import com.vietsoft.security.OAuthEntryPointRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  @Autowired
  private OAuthEntryPointRest unauthorizedHandler;

  @Autowired
  LocalTokenAuthFilter localTokenAuthFilter;

  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    logger.info("Configuration for http security");
    http.cors().and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()//
        .antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css",
            "/**/*.js", "/api/v1/socks/**").permitAll()
        .antMatchers("/login/**",
                "/api/v1/login/**",
                "/api/v1/auth/**",
                "/api/v1/cropjob/**", // TODO remove when golive
                "/logout/**",
                "/api/v1/reg/**",
                "/api/v1/prods/**").permitAll()
        .antMatchers("/swagger-resources/**", "/webjars/**", "/swagger-ui.html", "/v2/api-docs").permitAll()
        .antMatchers("/api/v1/predict/data/raw", "/api/v1/products/**").permitAll()
        
        .antMatchers(
        		"/api/v1/master/**",
        		"/api/v1/cropjobs/**",
        		"/api/v1/crops/**",
        		"/api/v1/jobs/**",
        		"/api/v1/apiAcl/**",
        		"/api/v1/assignments/**",
        		"/api/v1/blockchain/**",
//        		"/api/v1/countries/**",
//        		"/api/v1/provinces/**",
        		"/api/v1/customers/**",
        		"/api/v1/deliveryOrders/**",
        		"/api/v1/encroachments/**",
        		"/api/v1/requests/**",
        		"/api/v1/users/**",
        		"/api/v1/roles/**",
        		"/api/v1/warranties/**",
        		"/api/v1/qrcodes/**",
        		"/api/v1/qrcodesets/**",
        		"/api/v1/saleprogram/**",
        		"/api/v1/prodCodes/**",
        		"/api/v1/notes/**",
        		"/api/v1/orgs/**",
        		"/api/v1/plant/**",
        		"/api/v1/sale-program-promotion/**",
        		"/api/v1/serveylink/**",
        		"/api/v1/production-logs/**",
        		"/api/v1/profile/**",
        		"/api/v1/reports/**",
        		"/api/v1/productions/**",
        		"/api/v1/deliveryOrders/**",
        		"/api/v1/reports/**",
        		"/api/v1/predict/data/**"
        ).authenticated()
    ;
    http.addFilterBefore(localTokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
  }
}