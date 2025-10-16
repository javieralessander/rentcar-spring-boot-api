package com.universidad.apec.rentcar.repository;

import com.universidad.apec.rentcar.entity.Inspeccion;
import com.universidad.apec.rentcar.entity.Vehiculo;
import com.universidad.apec.rentcar.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface InspeccionRepository extends JpaRepository<Inspeccion, Long> {
    List<Inspeccion> findByEstadoTrue();
    List<Inspeccion> findByVehiculo(Vehiculo vehiculo);
    List<Inspeccion> findByCliente(Cliente cliente);
}