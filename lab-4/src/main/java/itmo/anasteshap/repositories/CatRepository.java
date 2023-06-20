package itmo.anasteshap.repositories;

import itmo.anasteshap.entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long>, JpaSpecificationExecutor<Cat> {
    List<Cat> findAllByOwnerId(Long id);
}