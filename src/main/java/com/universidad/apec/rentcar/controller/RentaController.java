package com.universidad.apec.rentcar.controller;

import com.universidad.apec.rentcar.entity.Renta;
import com.universidad.apec.rentcar.entity.Empleado;
import com.universidad.apec.rentcar.entity.Vehiculo;
import com.universidad.apec.rentcar.entity.Cliente;
import com.universidad.apec.rentcar.repository.RentaRepository;
import com.universidad.apec.rentcar.repository.EmpleadoRepository;
import com.universidad.apec.rentcar.repository.VehiculoRepository;
import com.universidad.apec.rentcar.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rentas")
@CrossOrigin(origins = "*")
public class RentaController {

    @Autowired
    private RentaRepository rentaRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public List<Renta> getAllRentas() {
        return rentaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Renta> getRentaById(@PathVariable Long id) {
        return rentaRepository.findById(id)
                .map(renta -> ResponseEntity.ok().body(renta))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Renta> createRenta(@Valid @RequestBody Renta renta) {
        // Validar Empleado
        if (renta.getEmpleado() == null || renta.getEmpleado().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Empleado empleado = empleadoRepository.findById(renta.getEmpleado().getId())
                .orElse(null);
        if (empleado == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validar Vehiculo
        if (renta.getVehiculo() == null || renta.getVehiculo().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Vehiculo vehiculo = vehiculoRepository.findById(renta.getVehiculo().getId())
                .orElse(null);
        if (vehiculo == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validar Cliente
        if (renta.getCliente() == null || renta.getCliente().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Cliente cliente = clienteRepository.findById(renta.getCliente().getId())
                .orElse(null);
        if (cliente == null) {
            return ResponseEntity.badRequest().build();
        }

        // Asignar las entidades existentes
        renta.setEmpleado(empleado);
        renta.setVehiculo(vehiculo);
        renta.setCliente(cliente);

        Renta rentaGuardada = rentaRepository.save(renta);
        return ResponseEntity.ok(rentaGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Renta> updateRenta(@PathVariable Long id, @Valid @RequestBody Renta rentaDetails) {
        return rentaRepository.findById(id)
                .map(renta -> {
                    // Validar y asignar Empleado
                    if (rentaDetails.getEmpleado() != null && rentaDetails.getEmpleado().getId() != null) {
                        Empleado empleado = empleadoRepository.findById(rentaDetails.getEmpleado().getId())
                                .orElse(null);
                        if (empleado != null) {
                            renta.setEmpleado(empleado);
                        }
                    }

                    // Validar y asignar Vehiculo
                    if (rentaDetails.getVehiculo() != null && rentaDetails.getVehiculo().getId() != null) {
                        Vehiculo vehiculo = vehiculoRepository.findById(rentaDetails.getVehiculo().getId())
                                .orElse(null);
                        if (vehiculo != null) {
                            renta.setVehiculo(vehiculo);
                        }
                    }

                    // Validar y asignar Cliente
                    if (rentaDetails.getCliente() != null && rentaDetails.getCliente().getId() != null) {
                        Cliente cliente = clienteRepository.findById(rentaDetails.getCliente().getId())
                                .orElse(null);
                        if (cliente != null) {
                            renta.setCliente(cliente);
                        }
                    }

                    renta.setFechaRenta(rentaDetails.getFechaRenta());
                    renta.setFechaDevolucion(rentaDetails.getFechaDevolucion());
                    renta.setMontoDia(rentaDetails.getMontoDia());
                    renta.setCantidadDias(rentaDetails.getCantidadDias());
                    renta.setComentario(rentaDetails.getComentario());
                    renta.setEstado(rentaDetails.getEstado());
                    return ResponseEntity.ok(rentaRepository.save(renta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<Renta> devolverVehiculo(@PathVariable Long id) {
        return rentaRepository.findById(id)
                .map(renta -> {
                    renta.setFechaDevolucion(LocalDate.now());
                    renta.setEstado(Renta.EstadoRenta.DEVUELTA);
                    return ResponseEntity.ok(rentaRepository.save(renta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRenta(@PathVariable Long id) {
        return rentaRepository.findById(id)
                .map(renta -> {
                    rentaRepository.delete(renta);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Renta> getRentasByCliente(@PathVariable Long clienteId) {
        return rentaRepository.findAll().stream()
                .filter(r -> r.getCliente().getId().equals(clienteId))
                .toList();
    }

    @GetMapping("/reporte")
    public List<Renta> getReporteRentas(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {

        if (fechaInicio != null && fechaFin != null) {
            LocalDate inicio = LocalDate.parse(fechaInicio);
            LocalDate fin = LocalDate.parse(fechaFin);
            return rentaRepository.findByFechaRentaBetween(inicio, fin);
        }

        return rentaRepository.findAll();
    }
}