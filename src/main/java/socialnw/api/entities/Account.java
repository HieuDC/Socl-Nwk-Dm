package socialnw.api.entities;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity for an account
 */
@Entity
@Data
@Table(name = "accounts")
public class Account {
	
	@Id
	@GeneratedValue
	@Column(name = "account_id")
	private Long accountId;
	
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "access_token")
	private String accessToken;
	
	@Column(name = "otp")
	private String otp;
	
	@Column(name = "otp_exp_time")
	private Date otpExpTime;
}
