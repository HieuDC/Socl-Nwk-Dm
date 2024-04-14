package socialnw.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfig {

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(request -> request.requestMatchers("/api/login", "/api/authenticate").permitAll()
				.requestMatchers("/api/register").permitAll()
				.requestMatchers("api-testing.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
				.requestMatchers("/error/**").permitAll()
				.anyRequest().authenticated()).logout(logout -> logout.permitAll());
		// @formater:on
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder encoder() {
		// Default rounds: 10
		return new BCryptPasswordEncoder();
	}
}
