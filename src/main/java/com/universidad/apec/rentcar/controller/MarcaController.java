package com.universidad.apec.rentcar.controller;

import com.universidad.apec.rentcar.entity.Marca;
import com.universidad.apec.rentcar.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@CrossOrigin(origins = "*")
public class MarcaController {

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    public List<Marca> getAllMarcas() {
        return marcaRepository.findByEstadoTrue();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> getMarcaById(@PathVariable Long id) {
        return marcaRepository.findById(id)
                .map(marca -> ResponseEntity.ok().body(marca))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Marca createMarca(@Valid @RequestBody Marca marca) {
        return marcaRepository.save(marca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> updateMarca(@PathVariable Long id, @Valid @RequestBody Marca marcaDetails) {
        return marcaRepository.findById(id)
                .map(marca -> {
                    marca.setDescripcion(marcaDetails.getDescripcion());
                    marca.setEstado(marcaDetails.getEstado());
                    return ResponseEntity.ok(marcaRepository.save(marca));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMarca(@PathVariable Long id) {
        return marcaRepository.findById(id)
                .map(marca -> {
                    marca.setEstado(false);
                    marcaRepository.save(marca);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}