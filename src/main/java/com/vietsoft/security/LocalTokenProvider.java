package com.vietsoft.security;

import com.vietsoft.config.TokenSecurityProperties;
import com.vietsoft.model.Role;
import com.vietsoft.model.User;
import com.vietsoft.model.UserRole;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LocalTokenProvider {

	static final Logger logger = LoggerFactory.getLogger(LocalTokenProvider.class);
	
	@Autowired
    TokenSecurityProperties props;

	private final String JWT_SECRET = "VSII-e-traxem-nn";
	private final long JWT_EXPIRATION = 1800000L; // 30 * 60 second

	public String generateToken(User user, List<UserRole> roles) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);
		String sub = "";
		if(roles != null && !roles.isEmpty()){
			for (UserRole r : roles) {
				sub += "," + r.getRole();
			}
		}
		if(!sub.isEmpty()){
			sub.substring(1);
		}
		return Jwts.builder()
				.setSubject(sub)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.setId(user.getUsername())
				.signWith(SignatureAlgorithm.HS512, JWT_SECRET)
				.compact();
	}

	public Long getUserIdFromJWT(String token) {
		Claims claims = Jwts.parser()
				.setSigningKey(JWT_SECRET)
				.parseClaimsJws(token)
				.getBody();
		return Long.parseLong(claims.getSubject());
	}

	public Claims getClaimFromToken(String authToken) {
		try {
			return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken).getBody();
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return null;
	}
}
