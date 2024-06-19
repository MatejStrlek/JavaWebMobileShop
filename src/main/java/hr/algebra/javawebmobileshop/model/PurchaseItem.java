package hr.algebra.javawebmobileshop.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchase_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mobileName;
    private String brand;
    private double price;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;
}
