package com.universidad.apec.rentcar.repository;

import com.universidad.apec.rentcar.entity.Vehiculo;
import com.universidad.apec.rentcar.entity.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByEstadoTrue();
    List<Vehiculo> findByTipoVehiculoAndEstadoTrue(TipoVehiculo tipoVehiculo);
    
    @Query("SELECT v FROM Vehiculo v WHERE v.estado = true AND v.id NOT IN (SELECT r.vehiculo.id FROM Renta r WHERE r.estado = 'ACTIVA')")
    List<Vehiculo> findVehiculosDisponibles();
}