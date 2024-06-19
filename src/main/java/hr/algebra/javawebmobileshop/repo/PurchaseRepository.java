package hr.algebra.javawebmobileshop.repo;

import hr.algebra.javawebmobileshop.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Purchase> findByUserId(Long userId);
    List<Purchase> findByPurchaseDateBetween(String startDate, String endDate);
    List<Purchase> findByPaymentMethod(String paymentMethod);
    List<Purchase> findByUserIdAndPurchaseDateBetweenAndPaymentMethod(Long userId, String startDate, String endDate, String paymentMethod);
}