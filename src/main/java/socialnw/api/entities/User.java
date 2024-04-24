package socialnw.api.entities;

import java.sql.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**	
 * Entity for a user
 */
@Entity
@Data
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "user_id")
	private String userId;
	
	@Column(name = "first_name")
	@Schema(example = "Hieu")
	private String firstName;
	
	@Column(name = "last_name")
	@Schema(example = "Do Chi")
	private String lastName;
	
	@Column(name = "phone")
	@Schema(example = "0987654321")
	private String phone;
	
	@Column(name = "birthday")
	@Schema(example = "1994/01/18", format = "yyyy/MM/dd")
	private Date birthday;
	
	@Column(name = "avatar_url")
	private String avatarUrl;
}
