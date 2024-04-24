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
import socialnw.api.entities.Account;
import socialnw.api.services.JwtService;

@Service
public class JwtServiceImpl implements JwtService {

	@Value("${security.jwt.secret-key}")
	private String secretKey;
	
	@Override
	public String generateAccessToken(String email) {
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

	@Override
	public String generatePasswordResetToken(Account account) {
		byte[] keyBytes = account.getPassword().getBytes();
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);
		return Jwts.builder().subject(account.getEmail())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusSeconds(300)))
				.signWith(key, Jwts.SIG.HS256)
				.compact();
	}

	@Override
	public boolean validatePasswordResetToken(String token, Account account) {
		byte[] keyBytes = account.getPassword().getBytes();
		SecretKey key = Keys.hmacShaKeyFor(keyBytes);
		Claims claims = Jwts.parser().verifyWith(key)
				.build().parseSignedClaims(token).getPayload();
		return account.getEmail().equals(claims.getSubject()) && claims.getExpiration().after(new Date());
	}
}
