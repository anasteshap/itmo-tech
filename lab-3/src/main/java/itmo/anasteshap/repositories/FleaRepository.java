package itmo.anasteshap.repositories;

import itmo.anasteshap.entities.Flea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FleaRepository extends JpaRepository<Flea, Long>, JpaSpecificationExecutor<Flea> {
    List<Flea> findAllByCatId(Long id);
}