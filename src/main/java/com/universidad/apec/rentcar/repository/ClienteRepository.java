package com.universidad.apec.rentcar.repository;

import com.universidad.apec.rentcar.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByEstadoTrue();
    Optional<Cliente> findByCedula(String cedula);
}