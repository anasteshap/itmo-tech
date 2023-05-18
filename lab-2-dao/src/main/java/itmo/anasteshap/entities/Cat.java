package itmo.anasteshap.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cat")
@Data
@NoArgsConstructor
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String breed;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Cat(String name, LocalDate birthDate, String breed, String color, Owner owner) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.owner = owner;
    }
}
