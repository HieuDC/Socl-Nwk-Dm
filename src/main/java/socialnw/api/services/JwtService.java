package socialnw.api.services;

public interface JwtService {
	
	String generateToken(String email);
}
