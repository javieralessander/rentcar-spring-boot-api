package com.universidad.apec.rentcar.repository;

import com.universidad.apec.rentcar.entity.TipoCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoCombustibleRepository extends JpaRepository<TipoCombustible, Long> {
    List<TipoCombustible> findByEstadoTrue();
}