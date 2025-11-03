package logica.evento;
import java.io.Serializable;

import java.time.LocalDateTime;
import logica.usuario.OrganizadorDeEventos;

public class Oferta implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private double porcentajeDescuento;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private boolean activa;
    private OrganizadorDeEventos creador;
    private Localidad localidad;

    public Oferta(String nombre, double porcentajeDescuento, LocalDateTime fechaInicio,
                  LocalDateTime fechaFin, OrganizadorDeEventos creador, Localidad localidad) {
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100)
            throw new IllegalArgumentException("El descuento debe estar entre 0% y 100%.");
        if (fechaFin.isBefore(fechaInicio))
            throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la de inicio.");

        this.nombre = nombre;
        this.porcentajeDescuento = porcentajeDescuento;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.creador = creador;
        this.localidad = localidad;
        this.activa = true;

        if (localidad != null)
            localidad.agregarOferta(this);
    }

    public String getNombre() {
        return nombre;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(double porcentajeDescuento) {
        if (porcentajeDescuento < 0 || porcentajeDescuento > 100)
            throw new IllegalArgumentException("El descuento debe estar entre 0% y 100%.");
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isActiva() {
        return activa;
    }

    public void activarOferta() {
        this.activa = true;
    }

    public void desactivarOferta() {
        this.activa = false;
    }

    public OrganizadorDeEventos getCreador() {
        return creador;
    }

    public void setCreador(OrganizadorDeEventos creador) {
        this.creador = creador;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public boolean esVigente() {
        LocalDateTime ahora = LocalDateTime.now();
        return activa && (ahora.isEqual(fechaInicio) || ahora.isAfter(fechaInicio))
                && ahora.isBefore(fechaFin);
    }

    public double aplicarDescuento(double precioOriginal) {
        if (esVigente()) {
            return precioOriginal * (1 - (porcentajeDescuento / 100));
        }
        return precioOriginal;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "nombre='" + nombre + '\'' +
                ", descuento=" + porcentajeDescuento + "%" +
                ", activa=" + activa +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", localidad=" + (localidad != null ? localidad.getNombre() : "sin localidad") +
                '}';
    }
}
