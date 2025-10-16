package com.universidad.apec.rentcar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rentas")
public class Renta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no_renta")
    private Long noRenta;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false)
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_renta", nullable = false)
    private LocalDate fechaRenta;

    @Column(name = "fecha_devolucion")
    private LocalDate fechaDevolucion;

    @DecimalMin(value = "0.0", message = "El monto por día debe ser mayor a 0")
    @Column(name = "monto_dia", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoDia;

    @Column(name = "cantidad_dias", nullable = false)
    private Integer cantidadDias;

    @Column(length = 500)
    private String comentario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoRenta estado = EstadoRenta.ACTIVA;

    public enum EstadoRenta {
        RESERVADA,     // Renta reservada pero vehículo no entregado aún
        ACTIVA,        // Vehículo entregado y en uso por el cliente
        DEVUELTA,      // Vehículo devuelto y renta completada
        VENCIDA,       // Renta que pasó la fecha límite sin devolución
        CANCELADA,     // Renta cancelada antes de entregar el vehículo
        PERDIDA        // Vehículo reportado como perdido/robado
    }

    public Renta() {
        this.fechaRenta = LocalDate.now();
    }

    public Long getNoRenta() {
        return noRenta;
    }

    public void setNoRenta(Long noRenta) {
        this.noRenta = noRenta;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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

    public LocalDate getFechaRenta() {
        return fechaRenta;
    }

    public void setFechaRenta(LocalDate fechaRenta) {
        this.fechaRenta = fechaRenta;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public BigDecimal getMontoDia() {
        return montoDia;
    }

    public void setMontoDia(BigDecimal montoDia) {
        this.montoDia = montoDia;
    }

    public Integer getCantidadDias() {
        return cantidadDias;
    }

    public void setCantidadDias(Integer cantidadDias) {
        this.cantidadDias = cantidadDias;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public EstadoRenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoRenta estado) {
        this.estado = estado;
    }

    public BigDecimal getMontoTotal() {
        return montoDia.multiply(new BigDecimal(cantidadDias));
    }

    /**
     * Calcula la fecha límite para devolver el vehículo
     */
    public LocalDate getFechaLimiteDevolucion() {
        return fechaRenta.plusDays(cantidadDias);
    }

    /**
     * Calcula los días reales transcurridos desde la renta
     */
    public int getDiasReales() {
        LocalDate fechaCalculo = fechaDevolucion != null ? fechaDevolucion : LocalDate.now();
        return (int) fechaRenta.until(fechaCalculo).getDays() + 1;
    }

    /**
     * Calcula el costo real basado en días utilizados
     */
    public BigDecimal getCostoReal() {
        return montoDia.multiply(new BigDecimal(getDiasReales()));
    }

    /**
     * Calcula los días de retraso (negativo si se devolvió temprano)
     */
    public int getDiasRetraso() {
        LocalDate fechaCalculo = fechaDevolucion != null ? fechaDevolucion : LocalDate.now();
        return (int) getFechaLimiteDevolucion().until(fechaCalculo).getDays();
    }

    /**
     * Calcula el monto de penalización por retraso (50% del monto diario)
     */
    public BigDecimal getMontoPenalizacion() {
        int diasRetraso = getDiasRetraso();
        if (diasRetraso > 0) {
            return montoDia.multiply(new BigDecimal(diasRetraso))
                          .multiply(new BigDecimal("0.5")); // 50% de penalización
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calcula el monto total incluyendo penalizaciones
     */
    public BigDecimal getMontoTotalConPenalizacion() {
        return getCostoReal().add(getMontoPenalizacion());
    }

    /**
     * Determina si la renta está vencida basado en fechas
     */
    public boolean isVencida() {
        return LocalDate.now().isAfter(getFechaLimiteDevolucion()) &&
               estado != EstadoRenta.DEVUELTA;
    }

    /**
     * Determina si el vehículo ya fue devuelto físicamente
     */
    public boolean isDevuelto() {
        return estado == EstadoRenta.DEVUELTA;
    }

    /**
     * Obtiene el estado calculado automáticamente basado en las fechas
     */
    public EstadoRenta getEstadoCalculado() {
        LocalDate ahora = LocalDate.now();

        switch (estado) {
            case CANCELADA:
            case PERDIDA:
                return estado; // Estados finales que no cambian

            case DEVUELTA:
                return EstadoRenta.DEVUELTA; // Ya fue devuelto

            case RESERVADA:
                // Si ya pasó la fecha de renta, debería estar activa
                if (ahora.isAfter(fechaRenta) || ahora.isEqual(fechaRenta)) {
                    return EstadoRenta.ACTIVA;
                }
                return EstadoRenta.RESERVADA;

            case ACTIVA:
                // Si pasó la fecha límite, está vencida
                if (ahora.isAfter(getFechaLimiteDevolucion())) {
                    return EstadoRenta.VENCIDA;
                }
                return EstadoRenta.ACTIVA;

            case VENCIDA:
                return EstadoRenta.VENCIDA;

            default:
                return estado;
        }
    }
}