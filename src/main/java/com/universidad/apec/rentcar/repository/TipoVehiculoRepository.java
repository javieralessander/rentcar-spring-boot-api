package com.universidad.apec.rentcar.repository;

import com.universidad.apec.rentcar.entity.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoVehiculoRepository extends JpaRepository<TipoVehiculo, Long> {
    List<TipoVehiculo> findByEstadoTrue();
}