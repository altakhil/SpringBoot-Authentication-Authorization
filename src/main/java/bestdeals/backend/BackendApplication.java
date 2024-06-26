package bestdeals.backend;

import bestdeals.backend.entities.Role;
import bestdeals.backend.entities.User;
import bestdeals.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Optional<User> adminAccount = userRepository.findByRole(Role.ADMIN);
		if(adminAccount.isEmpty())
		{
			User user = new User();

			user.setEmail("admin@gmail.com");
			user.setName("********");
			user.setRoles(List.of(Role.ADMIN));
			user.setPassword(new BCryptPasswordEncoder().encode("*********"));
			userRepository.save(user);
		}
	}
}
