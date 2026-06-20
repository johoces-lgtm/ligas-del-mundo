package duoc.cl.clubes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import duoc.cl.clubes.model.ClubesModel;

@Repository

public interface ClubesRepository extends JpaRepository<ClubesModel, Long>{ 
   List<ClubesModel> findByLigaId(Long ligaId);

}
