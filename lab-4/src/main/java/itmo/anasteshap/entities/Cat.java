package itmo.anasteshap.entities;

import itmo.anasteshap.entities.models.Breed;
import itmo.anasteshap.entities.models.Color;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
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
    @Enumerated(value = EnumType.STRING)
    private Breed breed;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Color color;

    @Column(nullable = false)
    Integer tailLength;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @Min(1)
    @ToString.Exclude
    private Owner owner;

    @Transient
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Flea> fleas;

    public Cat(String name, LocalDate birthDate, Breed breed, Color color) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.fleas = new ArrayList<>();
    }
}