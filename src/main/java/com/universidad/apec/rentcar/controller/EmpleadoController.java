package com.universidad.apec.rentcar.controller;

import com.universidad.apec.rentcar.entity.Empleado;
import com.universidad.apec.rentcar.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @GetMapping
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findByEstadoTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> getEmpleadoById(@PathVariable Long id) {
        return empleadoRepository.findById(id)
                .map(empleado -> ResponseEntity.ok().body(empleado))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Empleado createEmpleado(@Valid @RequestBody Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empleado> updateEmpleado(@PathVariable Long id, @Valid @RequestBody Empleado empleadoDetails) {
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleado.setNombre(empleadoDetails.getNombre());
                    empleado.setCedula(empleadoDetails.getCedula());
                    empleado.setTandaLabor(empleadoDetails.getTandaLabor());
                    empleado.setPorcientoComision(empleadoDetails.getPorcientoComision());
                    empleado.setFechaIngreso(empleadoDetails.getFechaIngreso());
                    empleado.setEstado(empleadoDetails.getEstado());
                    return ResponseEntity.ok(empleadoRepository.save(empleado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmpleado(@PathVariable Long id) {
        return empleadoRepository.findById(id)
                .map(empleado -> {
                    empleado.setEstado(false);
                    empleadoRepository.save(empleado);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<Empleado> getEmpleadoByCedula(@PathVariable String cedula) {
        return empleadoRepository.findByCedula(cedula)
                .map(empleado -> ResponseEntity.ok().body(empleado))
                .orElse(ResponseEntity.notFound().build());
    }
}