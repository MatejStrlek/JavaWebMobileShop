package hr.algebra.javawebmobileshop.service;

import hr.algebra.javawebmobileshop.model.Purchase;
import hr.algebra.javawebmobileshop.model.User;
import hr.algebra.javawebmobileshop.repo.PurchaseRepository;
import hr.algebra.javawebmobileshop.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    @Autowired
    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public void savePurchase(Purchase purchase) {
        purchaseRepository.save(purchase);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseRepository.findAll();
    }

    public List<Purchase> getPurchasesByUserId(Long userId) {
        return purchaseRepository.findByUserId(userId);
    }

    public List<Purchase> getPurchasesByUser(User user) {
        return purchaseRepository.findByUser(user);
    }

    public List<Purchase> getPurchasesByDateRange(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        return purchaseRepository.findByPurchaseDateBetween(
                DateUtils.format(startDateTime),
                DateUtils.format(endDateTime));
    }

    public List<Purchase> getPurchasesByPaymentMethod(String paymentMethod) {
        return purchaseRepository.findByPaymentMethod(paymentMethod);
    }

    public List<Purchase> filterPurchases(Long userId, LocalDate startDate, LocalDate endDate, String paymentMethod) {
        LocalDateTime startDateTime = startDate != null ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = endDate != null ? endDate.atTime(23, 59, 59) : null;

        if (userId != null && startDateTime != null && endDateTime != null && paymentMethod != null) {
            return purchaseRepository
                    .findByUserIdAndPurchaseDateBetweenAndPaymentMethod(
                            userId,
                            DateUtils.format(startDateTime),
                            DateUtils.format(endDateTime),
                            paymentMethod);
        } else if (userId != null) {
            return getPurchasesByUserId(userId);
        } else if (startDateTime != null && endDateTime != null) {
            return getPurchasesByDateRange(startDate, endDate);
        } else if (paymentMethod != null) {
            return getPurchasesByPaymentMethod(paymentMethod);
        } else {
            return getAllPurchases();
        }
    }
}
