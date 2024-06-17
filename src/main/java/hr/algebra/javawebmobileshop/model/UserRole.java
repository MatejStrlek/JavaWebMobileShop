package hr.algebra.javawebmobileshop.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ROLES")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
