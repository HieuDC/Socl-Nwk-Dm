package socialnw.api.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebConfig {
	
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(request -> request.requestMatchers("/api/login", "/api/authenticate", "/api/register", "api-testing.html", "/swagger-ui/*").permitAll()
				.anyRequest().authenticated()).logout(logout -> logout.permitAll());
		return http.build();
	}
	
//	@Bean
//	public UserDetailsManager users(DataSource dataSource) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		UserDetails user = User.withUsername("email").password("password").passwordEncoder(encoder::encode).roles("USER").build();
//		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
//		manager.createUser(user);
//		return manager;
//	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
