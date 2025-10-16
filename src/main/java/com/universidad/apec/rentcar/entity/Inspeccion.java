package com.universidad.apec.rentcar.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Entity
@Table(name = "inspecciones")
public class Inspeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "tiene_ralladuras", nullable = false)
    private Boolean tieneRalladuras = false;

    @Column(name = "cantidad_combustible", nullable = false, precision = 3, scale = 2)
    private BigDecimal cantidadCombustible;

    @Column(name = "tiene_goma_respuesta", nullable = false)
    private Boolean tieneGomaRespuesta = false;

    @Column(name = "tiene_gato", nullable = false)
    private Boolean tieneGato = false;

    @Column(name = "tiene_roturas_cristal", nullable = false)
    private Boolean tieneRoturasCristal = false;

    @Column(name = "estado_goma_delantera_izq", nullable = false)
    private Boolean estadoGomaDelanteraIzq = true;

    @Column(name = "estado_goma_delantera_der", nullable = false)
    private Boolean estadoGomaDelanteraDer = true;

    @Column(name = "estado_goma_trasera_izq", nullable = false)
    private Boolean estadoGomaTraseraIzq = true;

    @Column(name = "estado_goma_trasera_der", nullable = false)
    private Boolean estadoGomaTraseraDer = true;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @Column(nullable = false)
    private Boolean estado = true;

    public String getCantidadCombustibleTexto() {
        if (cantidadCombustible == null) return "Vacío";
        double valor = cantidadCombustible.doubleValue();
        if (valor <= 0.30) return "1/4";
        if (valor <= 0.60) return "1/2";
        if (valor <= 0.85) return "3/4";
        return "Lleno";
    }

    public Inspeccion() {
        this.fecha = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Boolean getTieneRalladuras() {
        return tieneRalladuras;
    }

    public void setTieneRalladuras(Boolean tieneRalladuras) {
        this.tieneRalladuras = tieneRalladuras;
    }

    public BigDecimal getCantidadCombustible() {
        return cantidadCombustible;
    }

    public void setCantidadCombustible(BigDecimal cantidadCombustible) {
        this.cantidadCombustible = cantidadCombustible;
    }

    public Boolean getTieneGomaRespuesta() {
        return tieneGomaRespuesta;
    }

    public void setTieneGomaRespuesta(Boolean tieneGomaRespuesta) {
        this.tieneGomaRespuesta = tieneGomaRespuesta;
    }

    public Boolean getTieneGato() {
        return tieneGato;
    }

    public void setTieneGato(Boolean tieneGato) {
        this.tieneGato = tieneGato;
    }

    public Boolean getTieneRoturasCristal() {
        return tieneRoturasCristal;
    }

    public void setTieneRoturasCristal(Boolean tieneRoturasCristal) {
        this.tieneRoturasCristal = tieneRoturasCristal;
    }

    public Boolean getEstadoGomaDelanteraIzq() {
        return estadoGomaDelanteraIzq;
    }

    public void setEstadoGomaDelanteraIzq(Boolean estadoGomaDelanteraIzq) {
        this.estadoGomaDelanteraIzq = estadoGomaDelanteraIzq;
    }

    public Boolean getEstadoGomaDelanteraDer() {
        return estadoGomaDelanteraDer;
    }

    public void setEstadoGomaDelanteraDer(Boolean estadoGomaDelanteraDer) {
        this.estadoGomaDelanteraDer = estadoGomaDelanteraDer;
    }

    public Boolean getEstadoGomaTraseraIzq() {
        return estadoGomaTraseraIzq;
    }

    public void setEstadoGomaTraseraIzq(Boolean estadoGomaTraseraIzq) {
        this.estadoGomaTraseraIzq = estadoGomaTraseraIzq;
    }

    public Boolean getEstadoGomaTraseraDer() {
        return estadoGomaTraseraDer;
    }

    public void setEstadoGomaTraseraDer(Boolean estadoGomaTraseraDer) {
        this.estadoGomaTraseraDer = estadoGomaTraseraDer;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}