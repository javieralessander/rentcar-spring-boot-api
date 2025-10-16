package com.universidad.apec.rentcar.repository;

import com.universidad.apec.rentcar.entity.Modelo;
import com.universidad.apec.rentcar.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Modelo> findByEstadoTrue();
    List<Modelo> findByMarcaAndEstadoTrue(Marca marca);
}