package socialnw.api.services.impl;

import java.time.Instant;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import socialnw.api.services.JwtService;

@Service
public class JwtServiceImpl implements JwtService {

	@Override
	public String generateToken(String email) {
		return Jwts.builder().subject(email)
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusSeconds(6000)))
				.compact();
	}

}
