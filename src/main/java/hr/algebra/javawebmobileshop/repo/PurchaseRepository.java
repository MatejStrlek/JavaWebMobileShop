package hr.algebra.javawebmobileshop.repo;

import hr.algebra.javawebmobileshop.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}