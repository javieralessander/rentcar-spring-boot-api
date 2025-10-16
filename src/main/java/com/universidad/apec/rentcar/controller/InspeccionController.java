package com.universidad.apec.rentcar.controller;

import com.universidad.apec.rentcar.entity.Inspeccion;
import com.universidad.apec.rentcar.entity.Vehiculo;
import com.universidad.apec.rentcar.entity.Cliente;
import com.universidad.apec.rentcar.entity.Empleado;
import com.universidad.apec.rentcar.repository.InspeccionRepository;
import com.universidad.apec.rentcar.repository.VehiculoRepository;
import com.universidad.apec.rentcar.repository.ClienteRepository;
import com.universidad.apec.rentcar.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/inspecciones")
@CrossOrigin(origins = "*")
public class InspeccionController {

    @Autowired
    private InspeccionRepository inspeccionRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Inspeccion> getAllInspecciones() {
        return inspeccionRepository.findByEstadoTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inspeccion> getInspeccionById(@PathVariable Long id) {
        return inspeccionRepository.findById(id)
                .map(inspeccion -> ResponseEntity.ok().body(inspeccion))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Inspeccion> createInspeccion(@Valid @RequestBody Inspeccion inspeccion) {
        // Validar Vehiculo
        if (inspeccion.getVehiculo() == null || inspeccion.getVehiculo().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Vehiculo vehiculo = vehiculoRepository.findById(inspeccion.getVehiculo().getId())
                .orElse(null);
        if (vehiculo == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validar Cliente
        if (inspeccion.getCliente() == null || inspeccion.getCliente().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Cliente cliente = clienteRepository.findById(inspeccion.getCliente().getId())
                .orElse(null);
        if (cliente == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validar Empleado
        if (inspeccion.getEmpleado() == null || inspeccion.getEmpleado().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Empleado empleado = empleadoRepository.findById(inspeccion.getEmpleado().getId())
                .orElse(null);
        if (empleado == null) {
            return ResponseEntity.badRequest().build();
        }

        // Asignar las entidades existentes
        inspeccion.setVehiculo(vehiculo);
        inspeccion.setCliente(cliente);
        inspeccion.setEmpleado(empleado);

        Inspeccion inspeccionGuardada = inspeccionRepository.save(inspeccion);
        return ResponseEntity.ok(inspeccionGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inspeccion> updateInspeccion(@PathVariable Long id, @Valid @RequestBody Inspeccion inspeccionDetails) {
        return inspeccionRepository.findById(id)
                .map(inspeccion -> {
                    // Validar y asignar Vehiculo
                    if (inspeccionDetails.getVehiculo() != null && inspeccionDetails.getVehiculo().getId() != null) {
                        Vehiculo vehiculo = vehiculoRepository.findById(inspeccionDetails.getVehiculo().getId())
                                .orElse(null);
                        if (vehiculo != null) {
                            inspeccion.setVehiculo(vehiculo);
                        }
                    }

                    // Validar y asignar Cliente
                    if (inspeccionDetails.getCliente() != null && inspeccionDetails.getCliente().getId() != null) {
                        Cliente cliente = clienteRepository.findById(inspeccionDetails.getCliente().getId())
                                .orElse(null);
                        if (cliente != null) {
                            inspeccion.setCliente(cliente);
                        }
                    }

                    // Validar y asignar Empleado
                    if (inspeccionDetails.getEmpleado() != null && inspeccionDetails.getEmpleado().getId() != null) {
                        Empleado empleado = empleadoRepository.findById(inspeccionDetails.getEmpleado().getId())
                                .orElse(null);
                        if (empleado != null) {
                            inspeccion.setEmpleado(empleado);
                        }
                    }

                    inspeccion.setTieneRalladuras(inspeccionDetails.getTieneRalladuras());
                    inspeccion.setCantidadCombustible(inspeccionDetails.getCantidadCombustible());
                    inspeccion.setTieneGomaRespuesta(inspeccionDetails.getTieneGomaRespuesta());
                    inspeccion.setTieneGato(inspeccionDetails.getTieneGato());
                    inspeccion.setTieneRoturasCristal(inspeccionDetails.getTieneRoturasCristal());
                    inspeccion.setEstadoGomaDelanteraIzq(inspeccionDetails.getEstadoGomaDelanteraIzq());
                    inspeccion.setEstadoGomaDelanteraDer(inspeccionDetails.getEstadoGomaDelanteraDer());
                    inspeccion.setEstadoGomaTraseraIzq(inspeccionDetails.getEstadoGomaTraseraIzq());
                    inspeccion.setEstadoGomaTraseraDer(inspeccionDetails.getEstadoGomaTraseraDer());
                    inspeccion.setEstado(inspeccionDetails.getEstado());
                    return ResponseEntity.ok(inspeccionRepository.save(inspeccion));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vehiculo/{vehiculoId}")
    public List<Inspeccion> getInspeccionesByVehiculo(@PathVariable Long vehiculoId) {
        return inspeccionRepository.findAll().stream()
                .filter(i -> i.getVehiculo().getId().equals(vehiculoId))
                .toList();
    }
}