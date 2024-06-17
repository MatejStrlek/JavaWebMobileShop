package hr.algebra.javawebmobileshop.repo;

import hr.algebra.javawebmobileshop.model.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MobileRepository extends JpaRepository<Mobile, Long> {
}
