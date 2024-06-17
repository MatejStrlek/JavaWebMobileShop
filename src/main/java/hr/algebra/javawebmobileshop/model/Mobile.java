package hr.algebra.javawebmobileshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mobiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mobile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String brand;
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private MobileCategory category;
}