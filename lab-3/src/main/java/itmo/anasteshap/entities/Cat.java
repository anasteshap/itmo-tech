package itmo.anasteshap.entities;

import lombok.*;

import jakarta.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "cat")
@NoArgsConstructor
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @NotBlank
    @Column(nullable = false)
    private String breed;

    @NotBlank
    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    Integer tailLength;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Transient
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Flea> fleas;

    public Cat(String name, LocalDate birthDate, String breed, String color) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
    }
}