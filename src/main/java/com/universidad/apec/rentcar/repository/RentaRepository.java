package com.universidad.apec.rentcar.repository;

import com.universidad.apec.rentcar.entity.Renta;
import com.universidad.apec.rentcar.entity.Cliente;
import com.universidad.apec.rentcar.entity.Vehiculo;
import com.universidad.apec.rentcar.entity.TipoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentaRepository extends JpaRepository<Renta, Long> {
    List<Renta> findByCliente(Cliente cliente);
    List<Renta> findByVehiculo(Vehiculo vehiculo);
    List<Renta> findByFechaRentaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    
    @Query("SELECT r FROM Renta r WHERE r.vehiculo.tipoVehiculo = :tipoVehiculo")
    List<Renta> findByTipoVehiculo(@Param("tipoVehiculo") TipoVehiculo tipoVehiculo);
    
    @Query("SELECT r FROM Renta r WHERE r.fechaRenta BETWEEN :fechaInicio AND :fechaFin AND r.vehiculo.tipoVehiculo = :tipoVehiculo")
    List<Renta> findByFechaRentaBetweenAndTipoVehiculo(
        @Param("fechaInicio") LocalDate fechaInicio, 
        @Param("fechaFin") LocalDate fechaFin, 
        @Param("tipoVehiculo") TipoVehiculo tipoVehiculo);
}