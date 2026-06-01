package cl.duoc.football_api.repository;

import cl.duoc.football_api.model.ApiModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends JpaRepository<ApiModel, Long> {
    
}