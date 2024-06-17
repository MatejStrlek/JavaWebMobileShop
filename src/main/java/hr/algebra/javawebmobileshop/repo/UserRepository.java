package hr.algebra.javawebmobileshop.repo;

import hr.algebra.javawebmobileshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
