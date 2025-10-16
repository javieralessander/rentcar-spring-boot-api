package com.universidad.apec.rentcar.controller;

import com.universidad.apec.rentcar.entity.TipoVehiculo;
import com.universidad.apec.rentcar.repository.TipoVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-vehiculos")
@CrossOrigin(origins = "*")
public class TipoVehiculoController {

    @Autowired
    private TipoVehiculoRepository tipoVehiculoRepository;

    @GetMapping
    public List<TipoVehiculo> getAllTiposVehiculos() {
        return tipoVehiculoRepository.findByEstadoTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoVehiculo> getTipoVehiculoById(@PathVariable Long id) {
        return tipoVehiculoRepository.findById(id)
                .map(tipo -> ResponseEntity.ok().body(tipo))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoVehiculo createTipoVehiculo(@Valid @RequestBody TipoVehiculo tipoVehiculo) {
        return tipoVehiculoRepository.save(tipoVehiculo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoVehiculo> updateTipoVehiculo(@PathVariable Long id, @Valid @RequestBody TipoVehiculo tipoDetails) {
        return tipoVehiculoRepository.findById(id)
                .map(tipo -> {
                    tipo.setDescripcion(tipoDetails.getDescripcion());
                    tipo.setEstado(tipoDetails.getEstado());
                    return ResponseEntity.ok(tipoVehiculoRepository.save(tipo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTipoVehiculo(@PathVariable Long id) {
        return tipoVehiculoRepository.findById(id)
                .map(tipo -> {
                    tipo.setEstado(false);
                    tipoVehiculoRepository.save(tipo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}