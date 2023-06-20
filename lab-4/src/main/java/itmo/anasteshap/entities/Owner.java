package itmo.anasteshap.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "owner")
@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Transient
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Cat> cats;

    @Transient
    @OneToOne(mappedBy = "owner")
    private User user;

    public Owner(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.cats = new ArrayList<>();
    }
}