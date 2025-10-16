package com.universidad.apec.rentcar.controller;

import com.universidad.apec.rentcar.entity.Vehiculo;
import com.universidad.apec.rentcar.entity.TipoVehiculo;
import com.universidad.apec.rentcar.entity.Marca;
import com.universidad.apec.rentcar.entity.Modelo;
import com.universidad.apec.rentcar.entity.TipoCombustible;
import com.universidad.apec.rentcar.repository.VehiculoRepository;
import com.universidad.apec.rentcar.repository.TipoVehiculoRepository;
import com.universidad.apec.rentcar.repository.MarcaRepository;
import com.universidad.apec.rentcar.repository.ModeloRepository;
import com.universidad.apec.rentcar.repository.TipoCombustibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private TipoCombustibleRepository tipoCombustibleRepository;

    @GetMapping
    public List<Vehiculo> getAllVehiculos() {
        return vehiculoRepository.findByEstadoTrue();
    }

    @GetMapping("/disponibles")
    public List<Vehiculo> getVehiculosDisponibles() {
        return vehiculoRepository.findVehiculosDisponibles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> getVehiculoById(@PathVariable Long id) {
        return vehiculoRepository.findById(id)
                .map(vehiculo -> ResponseEntity.ok().body(vehiculo))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vehiculo> createVehiculo(@Valid @RequestBody Vehiculo vehiculo) {
        // Validar TipoVehiculo
        if (vehiculo.getTipoVehiculo() == null || vehiculo.getTipoVehiculo().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(vehiculo.getTipoVehiculo().getId())
                .orElse(null);
        if (tipoVehiculo == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validar Marca
        if (vehiculo.getMarca() == null || vehiculo.getMarca().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Marca marca = marcaRepository.findById(vehiculo.getMarca().getId())
                .orElse(null);
        if (marca == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validar Modelo
        if (vehiculo.getModelo() == null || vehiculo.getModelo().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Modelo modelo = modeloRepository.findById(vehiculo.getModelo().getId())
                .orElse(null);
        if (modelo == null) {
            return ResponseEntity.badRequest().build();
        }

        // Validar TipoCombustible
        if (vehiculo.getTipoCombustible() == null || vehiculo.getTipoCombustible().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        TipoCombustible tipoCombustible = tipoCombustibleRepository.findById(vehiculo.getTipoCombustible().getId())
                .orElse(null);
        if (tipoCombustible == null) {
            return ResponseEntity.badRequest().build();
        }

        // Asignar las entidades existentes
        vehiculo.setTipoVehiculo(tipoVehiculo);
        vehiculo.setMarca(marca);
        vehiculo.setModelo(modelo);
        vehiculo.setTipoCombustible(tipoCombustible);

        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);
        return ResponseEntity.ok(vehiculoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> updateVehiculo(@PathVariable Long id, @Valid @RequestBody Vehiculo vehiculoDetails) {
        return vehiculoRepository.findById(id)
                .map(vehiculo -> {
                    vehiculo.setDescripcion(vehiculoDetails.getDescripcion());
                    vehiculo.setNoChasis(vehiculoDetails.getNoChasis());
                    vehiculo.setNoMotor(vehiculoDetails.getNoMotor());
                    vehiculo.setNoPlaca(vehiculoDetails.getNoPlaca());

                    // Validar y asignar TipoVehiculo
                    if (vehiculoDetails.getTipoVehiculo() != null && vehiculoDetails.getTipoVehiculo().getId() != null) {
                        TipoVehiculo tipoVehiculo = tipoVehiculoRepository.findById(vehiculoDetails.getTipoVehiculo().getId())
                                .orElse(null);
                        if (tipoVehiculo != null) {
                            vehiculo.setTipoVehiculo(tipoVehiculo);
                        }
                    }

                    // Validar y asignar Marca
                    if (vehiculoDetails.getMarca() != null && vehiculoDetails.getMarca().getId() != null) {
                        Marca marca = marcaRepository.findById(vehiculoDetails.getMarca().getId())
                                .orElse(null);
                        if (marca != null) {
                            vehiculo.setMarca(marca);
                        }
                    }

                    // Validar y asignar Modelo
                    if (vehiculoDetails.getModelo() != null && vehiculoDetails.getModelo().getId() != null) {
                        Modelo modelo = modeloRepository.findById(vehiculoDetails.getModelo().getId())
                                .orElse(null);
                        if (modelo != null) {
                            vehiculo.setModelo(modelo);
                        }
                    }

                    // Validar y asignar TipoCombustible
                    if (vehiculoDetails.getTipoCombustible() != null && vehiculoDetails.getTipoCombustible().getId() != null) {
                        TipoCombustible tipoCombustible = tipoCombustibleRepository.findById(vehiculoDetails.getTipoCombustible().getId())
                                .orElse(null);
                        if (tipoCombustible != null) {
                            vehiculo.setTipoCombustible(tipoCombustible);
                        }
                    }

                    vehiculo.setEstado(vehiculoDetails.getEstado());
                    return ResponseEntity.ok(vehiculoRepository.save(vehiculo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehiculo(@PathVariable Long id) {
        return vehiculoRepository.findById(id)
                .map(vehiculo -> {
                    vehiculo.setEstado(false);
                    vehiculoRepository.save(vehiculo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}