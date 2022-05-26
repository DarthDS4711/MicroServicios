package com.moto.service.repository;

import com.moto.service.entity.Moto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMotoRepository extends JpaRepository<Moto, Integer>{
    public List<Moto> findByUserId(int userId); 
}
