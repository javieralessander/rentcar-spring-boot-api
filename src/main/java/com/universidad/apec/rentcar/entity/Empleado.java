package com.universidad.apec.rentcar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La cédula es obligatoria")
    @Column(nullable = false, unique = true)
    private String cedula;

    @Enumerated(EnumType.STRING)
    @Column(name = "tanda_labor", nullable = false)
    private TandaLabor tandaLabor;

    @DecimalMin(value = "0.0", message = "El porcentaje de comisión debe ser mayor o igual a 0")
    @DecimalMax(value = "100.0", message = "El porcentaje de comisión debe ser menor o igual a 100")
    @Column(name = "porciento_comision", nullable = false, precision = 5, scale = 2)
    private BigDecimal porcientoComision;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Column(nullable = false)
    private Boolean estado = true;

    public enum TandaLabor {
        MATUTINA, VESPERTINA, NOCTURNA
    }

    public Empleado() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public TandaLabor getTandaLabor() {
        return tandaLabor;
    }

    public void setTandaLabor(TandaLabor tandaLabor) {
        this.tandaLabor = tandaLabor;
    }

    public BigDecimal getPorcientoComision() {
        return porcientoComision;
    }

    public void setPorcientoComision(BigDecimal porcientoComision) {
        this.porcientoComision = porcientoComision;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}