package duoc.cl.estadios.repository;

import duoc.cl.estadios.model.EstadioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadioRepository extends JpaRepository<EstadioModel, Long> {
}
