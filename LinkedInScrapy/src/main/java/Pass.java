
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Pass {
	public static void main(String args[]) throws Exception {
		String cryptedPassword = new BCryptPasswordEncoder().encode("12345");
		System.out.println(cryptedPassword);
		
		
		new BCryptPasswordEncoder().matches("12345", "$2a$10$u4YNzVblJ4My7bbFu.O4ZeUEuX8.C6XpojSEpj7l7CGtMQQYKRv8q");
	}
}