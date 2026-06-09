package cl.duoc.jugadores.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.jugadores.model.JugadoresModel;

@Repository
public interface JugadoresRepository extends JpaRepository<JugadoresModel, Long> {
    List<JugadoresModel> findByClubId(Long clubId);

}
