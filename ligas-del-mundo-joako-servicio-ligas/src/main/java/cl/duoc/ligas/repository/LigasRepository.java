package cl.duoc.ligas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.ligas.model.LigasModel;

public interface LigasRepository  extends JpaRepository<LigasModel, Long> {

}
