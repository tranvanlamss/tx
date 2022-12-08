package com.vietsoft.common;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.util.StringUtils;

public final class TokenUtil {
	static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

	/** different dictionaries used */
	public static final String ALPHA_CAPS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
	public static final String NUMERIC = "0123456789";
	public static final String SPECIAL_CHARS = "!@#$%^&*_=+-/.?<>)";// "!@#$%^&*_=+-/";
	public static final String DICTIONARY = ALPHA_CAPS + ALPHA + NUMERIC;
	
	public static final int BCrypt_SALT = 10;
	public static final SimpleDateFormat dateFormater = new SimpleDateFormat("yyMM");
	

	
	public static String generateToken(int size, String... dictionary) {
		String chars = (!ArrayUtils.isEmpty(dictionary) ? String.join("", dictionary) : DICTIONARY);
		String pw = RandomStringUtils.random(size, chars);
		return pw;
	}

	public static String getUuid() {
		return UUID.randomUUID().toString();
	}

	public static String newObjectID() {
		ObjectId id = new ObjectId();
		String sid = id.toHexString();
		return sid;
	}

	public static String generateNumber(int size) {
		String pw = RandomStringUtils.random(size, NUMERIC);
		return pw;
	}
	public static String generateAlphaNumber(int size) {
		String pw = RandomStringUtils.random(size, DICTIONARY);
		return pw;
	}

	public static String encodePw(String password) throws NoSuchAlgorithmException {
		String generatedSecuredPasswordHash = BCrypt.hashpw(password, BCrypt.gensalt(BCrypt_SALT));
		// logger.info(generatedSecuredPasswordHash);

		// boolean matched = BCrypt.checkpw(password, generatedSecuredPasswordHash);
		// logger.info(matched);
		return generatedSecuredPasswordHash;
	}

	public static String getTokenFrom(HttpServletRequest request) {
		String authToken = null;

		if (!StringUtils.hasText(authToken)) {
			authToken = request.getParameter("access_token");
		}
		if (!StringUtils.hasText(authToken)) {
			authToken = request.getHeader("Authorization");
		}
		if (!StringUtils.hasText(authToken)) {
			authToken = getCookieValue(request, getRefSuper(request) + "access_token");
		}

		return authToken;
	}

	public static String getRefSuper(HttpServletRequest request) {
		String referrer = request.getHeader("referer");
		String type = request.getHeader("x-client-type");
		logger.info("type {}, refer {}", type, referrer);
		if ("super".equals(type) || (referrer != null && referrer.contains("/admin/"))) {
			return "super";
		}
		return "";
	}

	public static String getCookieValue(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		return Arrays.stream(cookies).filter(c -> c.getName().equals(cookieName)).findFirst().map(Cookie::getValue)
				.orElse(null);
	}

//  public static void setCookieValue(HttpServletResponse response, String cookieName, String cookieValue, String domain,
//      String path, boolean secure, boolean httpOnly, int maxAge) {
//    final Cookie cookie = new Cookie(cookieName, cookieValue);
//    cookie.setDomain(domain);
//    cookie.setPath(path);
//    cookie.setSecure(secure);
//    cookie.setHttpOnly(httpOnly);
//    cookie.setMaxAge(maxAge);
//    response.addCookie(cookie);
//  }

	public static void setCookieValue(HttpServletResponse response, String cookieName, String cookieValue, int maxAge) {
		final Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");
		cookie.setSecure(false);
		cookie.setHttpOnly(false);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	public static void removeCookie(HttpServletResponse response, String cookieName) {
		final Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}

}
