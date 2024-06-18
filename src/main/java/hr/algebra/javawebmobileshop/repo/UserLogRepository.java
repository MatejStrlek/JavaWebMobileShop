package hr.algebra.javawebmobileshop.repo;

import hr.algebra.javawebmobileshop.model.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
