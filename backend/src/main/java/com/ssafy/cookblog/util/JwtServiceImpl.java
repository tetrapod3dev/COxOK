package com.ssafy.cookblog.util;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtServiceImpl implements JwtService{
	private final String secret = "watermelon";
	private final long validTime = 2*60*60*1000L;
	@Override
	public String generateToken(String id) {
		Claims claims = Jwts.claims().setSubject(id);
		Date now =new Date();
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(new Date(now.getTime()+validTime))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	@Override
	public boolean isValid(String id, String token) throws Exception {
		try {
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			return claims.getSubject().equals(id);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean isValid(String token) throws Exception {
		try {
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token.substring(7)).getBody();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public String getEmailFromToken(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
			return claims.getSubject();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
