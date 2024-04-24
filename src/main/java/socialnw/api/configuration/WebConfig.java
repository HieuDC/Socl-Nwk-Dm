package socialnw.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity security) throws Exception {
		// @formatter:off
		security.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(request -> request.requestMatchers("/api/login", "/api/authenticate").permitAll()
				.requestMatchers("/api/reset-password/**").permitAll()
				.requestMatchers("/api/register").permitAll()
				.requestMatchers("api-testing.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
				.requestMatchers("/error/**").permitAll()
				.anyRequest().authenticated()).logout(logout -> logout.permitAll());
		security.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		// @formater:on
		return security.build();
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
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:message");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
}
