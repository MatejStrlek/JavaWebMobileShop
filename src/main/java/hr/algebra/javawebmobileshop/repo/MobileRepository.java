package hr.algebra.javawebmobileshop.repo;

import hr.algebra.javawebmobileshop.model.Mobile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MobileRepository extends JpaRepository<Mobile, Long> {
    List<Mobile> findByCategoryId(Long categoryId);
}
