package com.universidad.apec.rentcar.controller;

import com.universidad.apec.rentcar.entity.TipoCombustible;
import com.universidad.apec.rentcar.repository.TipoCombustibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-combustibles")
@CrossOrigin(origins = "*")
public class TipoCombustibleController {

    @Autowired
    private TipoCombustibleRepository tipoCombustibleRepository;

    @GetMapping
    public List<TipoCombustible> getAllTiposCombustibles() {
        return tipoCombustibleRepository.findByEstadoTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCombustible> getTipoCombustibleById(@PathVariable Long id) {
        return tipoCombustibleRepository.findById(id)
                .map(tipo -> ResponseEntity.ok().body(tipo))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TipoCombustible createTipoCombustible(@Valid @RequestBody TipoCombustible tipoCombustible) {
        return tipoCombustibleRepository.save(tipoCombustible);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCombustible> updateTipoCombustible(@PathVariable Long id, @Valid @RequestBody TipoCombustible tipoDetails) {
        return tipoCombustibleRepository.findById(id)
                .map(tipo -> {
                    tipo.setDescripcion(tipoDetails.getDescripcion());
                    tipo.setEstado(tipoDetails.getEstado());
                    return ResponseEntity.ok(tipoCombustibleRepository.save(tipo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTipoCombustible(@PathVariable Long id) {
        return tipoCombustibleRepository.findById(id)
                .map(tipo -> {
                    tipo.setEstado(false);
                    tipoCombustibleRepository.save(tipo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}