package socialnw.api.services.impl;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import socialnw.api.services.JwtService;

@Service
public class JwtServiceImpl implements JwtService {

	@Value("${security.jwt.secret-key}")
	private String secretKey;
	
	@Override
	public String generateToken(String email) {
		return Jwts.builder().subject(email)
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusSeconds(6000)))
				.signWith(getEncryptionKey(), Jwts.SIG.HS256)
				.compact();
	}

	@Override
	public Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getEncryptionKey())
				.build().parseEncryptedClaims(token).getPayload();
	}

	@Override
	public boolean isTokenExpired(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getExpiration().before(new Date());
	}

	@Override
	public boolean validateToken(String token, UserDetails userDetail) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject().equals(userDetail.getUsername()) && !isTokenExpired(token);
	}
	
	private SecretKey getEncryptionKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
