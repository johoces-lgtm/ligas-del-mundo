package duoc.cl.entrenadores.repository;

import duoc.cl.entrenadores.model.EntrenadorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrenadorRepository extends JpaRepository<EntrenadorModel, Long> {
}