package hr.algebra.javawebmobileshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mobile_categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MobileCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
