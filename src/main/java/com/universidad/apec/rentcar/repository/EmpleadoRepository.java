package com.universidad.apec.rentcar.repository;

import com.universidad.apec.rentcar.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    List<Empleado> findByEstadoTrue();
    Optional<Empleado> findByCedula(String cedula);
}