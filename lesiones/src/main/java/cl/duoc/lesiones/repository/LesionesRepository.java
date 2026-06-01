package cl.duoc.lesiones.repository;

import cl.duoc.lesiones.model.LesionesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LesionesRepository extends JpaRepository<LesionesModel, Long> {
    List<LesionesModel> findByJugadorId(Long jugadorId);
}