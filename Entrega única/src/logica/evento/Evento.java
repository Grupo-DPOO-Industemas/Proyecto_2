package logica.evento;
import java.io.Serializable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import logica.tiquete.Tiquete;
import logica.usuario.OrganizadorDeEventos;

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private LocalDateTime fechaHora;
    private String tipo;
    private OrganizadorDeEventos organizador;
    private Venue venue;
    private List<Localidad> localidades;
    private List<Tiquete> tiquetes;
    private boolean cancelado;

    public Evento() {
        this.localidades = new ArrayList<>();
        this.tiquetes = new ArrayList<>();
        this.cancelado = false;
    }

    public Evento(String nombre, LocalDateTime fechaHora, String tipo,
                  OrganizadorDeEventos organizador, Venue venue) {
        if (venue == null || !venue.isAprobado())
            throw new IllegalArgumentException("El venue debe existir y estar aprobado.");
        this.nombre = nombre;
        this.fechaHora = fechaHora;
        this.tipo = tipo;
        this.organizador = organizador;
        this.venue = venue;
        this.localidades = new ArrayList<>();
        this.tiquetes = new ArrayList<>();
        this.cancelado = false;
        this.venue.asignarEvento(this);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public OrganizadorDeEventos getOrganizador() {
        return organizador;
    }

    public void setOrganizador(OrganizadorDeEventos organizador) {
        this.organizador = organizador;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        if (this.venue != null) {
            this.venue.liberarEvento();
        }
        this.venue = venue;
        if (venue != null) {
            venue.asignarEvento(this);
        }
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<Localidad> localidades) {
        this.localidades = localidades;
    }

    public List<Tiquete> getTiquetes() {
        return tiquetes;
    }

    public void setTiquetes(List<Tiquete> tiquetes) {
        this.tiquetes = tiquetes;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    public void agregarLocalidad(Localidad localidad) {
        if (localidad != null) {
            localidades.add(localidad);
        }
    }

    public Localidad buscarLocalidad(String nombreLocalidad) {
        for (Localidad localidad : localidades) {
            if (localidad.getNombre().equalsIgnoreCase(nombreLocalidad)) {
                return localidad;
            }
        }
        return null;
    }

    public void agregarTiquete(Tiquete tiquete) {
        if (tiquete != null) {
            tiquetes.add(tiquete);
        }
    }

    public Tiquete buscarTiquetePorId(String id) {
        for (Tiquete t : tiquetes) {
            if (t.getIdentificador().equals(id)) {
                return t;
            }
        }
        return null;
    }

    public void cancelarEvento() {
        this.cancelado = true;
        if (venue != null) {
            venue.liberarEvento();
        }
    }

    @Override
    public String toString() {
        return "Evento{" +
                "nombre='" + nombre + '\'' +
                ", fechaHora=" + fechaHora +
                ", tipo='" + tipo + '\'' +
                ", venue=" + (venue != null ? venue.getUbicacion() : "Sin venue") +
                ", cancelado=" + cancelado +
                '}';
    }
}
