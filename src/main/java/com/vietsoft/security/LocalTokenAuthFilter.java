package com.vietsoft.security;

import com.vietsoft.common.TokenUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalTokenAuthFilter extends OncePerRequestFilter {
  static final Logger logger = LoggerFactory.getLogger(LocalTokenAuthFilter.class);

  @Autowired
  LocalTokenProvider tokenProvider;

  @Autowired
  AuditorAware<String> auditor;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if(auditor.getCurrentAuditor().isPresent()){
      filterChain.doFilter(request, response);
      return;
    }
    try {
      String token = TokenUtil.getTokenFrom(request);
      if (token == null || !token.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }
      token = token.substring(7).trim();
      Claims claims = tokenProvider.getClaimFromToken(token);
      if(claims == null){
        filterChain.doFilter(request, response);
        return;
      }
      UserPrincipal userDetails = new UserPrincipal();
      userDetails.setUsername(claims.getId());
      List<GrantedAuthority> authorities = new ArrayList<>();
      String sub = claims.getSubject();
      logger.info("sub {}", sub);
      String[] roles = sub.split(",");
      if(roles == null || roles.length < 1){
        return;
      }
      for (String r : roles) {
        if(r == null || r.isEmpty()){
          continue;
        }
        authorities.add(new SimpleGrantedAuthority(r));
      }

      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
          null, authorities);
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } catch (Exception ex) {
      logger.error("Could not set user authentication in security context(Local)", ex);
    }

    filterChain.doFilter(request, response);
  }

}
