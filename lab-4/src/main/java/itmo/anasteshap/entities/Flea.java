package itmo.anasteshap.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "flea")
@NoArgsConstructor
public class Flea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id")
    @Min(1)
    @ToString.Exclude
    private Cat cat;

    public Flea(String name) {
        this.name = name;
    }
}
