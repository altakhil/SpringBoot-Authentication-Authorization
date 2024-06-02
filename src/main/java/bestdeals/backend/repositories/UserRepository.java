package bestdeals.backend.repositories;

import java.util.Optional;

import bestdeals.backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import bestdeals.backend.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

	Optional<User> findByEmail(String email);

	@Query("SELECT u FROM User u JOIN u.roles r WHERE r = :role")
	Optional<User> findByRole(Role role);
}
