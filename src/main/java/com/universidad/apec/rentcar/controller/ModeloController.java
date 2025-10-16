package com.universidad.apec.rentcar.controller;

import com.universidad.apec.rentcar.entity.Modelo;
import com.universidad.apec.rentcar.entity.Marca;
import com.universidad.apec.rentcar.repository.ModeloRepository;
import com.universidad.apec.rentcar.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@CrossOrigin(origins = "*")
public class ModeloController {

    @Autowired
    private ModeloRepository modeloRepository;

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    public List<Modelo> getAllModelos() {
        return modeloRepository.findByEstadoTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> getModeloById(@PathVariable Long id) {
        return modeloRepository.findById(id)
                .map(modelo -> ResponseEntity.ok().body(modelo))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Modelo> createModelo(@Valid @RequestBody Modelo modelo) {
        if (modelo.getMarca() == null || modelo.getMarca().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Buscar la marca existente por ID
        Marca marcaExistente = marcaRepository.findById(modelo.getMarca().getId())
                .orElse(null);

        if (marcaExistente == null) {
            return ResponseEntity.badRequest().build();
        }

        // Asignar la marca existente al modelo
        modelo.setMarca(marcaExistente);

        Modelo modeloGuardado = modeloRepository.save(modelo);
        return ResponseEntity.ok(modeloGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Modelo> updateModelo(@PathVariable Long id, @Valid @RequestBody Modelo modeloDetails) {
        return modeloRepository.findById(id)
                .map(modelo -> {
                    // Buscar la marca existente por ID si se proporcionó
                    if (modeloDetails.getMarca() != null && modeloDetails.getMarca().getId() != null) {
                        Marca marcaExistente = marcaRepository.findById(modeloDetails.getMarca().getId())
                                .orElse(null);
                        if (marcaExistente != null) {
                            modelo.setMarca(marcaExistente);
                        }
                    }
                    modelo.setDescripcion(modeloDetails.getDescripcion());
                    modelo.setEstado(modeloDetails.getEstado());
                    return ResponseEntity.ok(modeloRepository.save(modelo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteModelo(@PathVariable Long id) {
        return modeloRepository.findById(id)
                .map(modelo -> {
                    modelo.setEstado(false);
                    modeloRepository.save(modelo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}