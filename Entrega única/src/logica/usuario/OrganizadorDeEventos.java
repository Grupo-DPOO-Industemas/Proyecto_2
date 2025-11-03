
package logica.usuario;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import logica.evento.Evento;
import logica.evento.Localidad;
import logica.evento.Oferta;
import logica.evento.Venue;

public class OrganizadorDeEventos extends UsuarioConSaldo implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Evento> eventosCreados;
    private List<Venue> venuesSugeridos;
    private List<Oferta> ofertasCreadas;

    public OrganizadorDeEventos(String nombreUsuario, String contrasena, String nombreCompleto, double saldoInicial) {
        super(nombreUsuario, contrasena, nombreCompleto, saldoInicial);
        this.eventosCreados = new ArrayList<>();
        this.venuesSugeridos = new ArrayList<>();
        this.ofertasCreadas = new ArrayList<>();
    }

    public List<Evento> getEventosCreados() {
        return eventosCreados;
    }

    public List<Venue> getVenuesSugeridos() {
        return venuesSugeridos;
    }

    public List<Oferta> getOfertasCreadas() {
        return ofertasCreadas;
    }

    public Evento crearEvento(String nombre, LocalDateTime fechaHora, String tipo, Venue venue) {
        if (venue == null || !venue.isAprobado()) {
            throw new IllegalArgumentException("El venue debe estar aprobado antes de asignar un evento.");
        }
        Evento nuevoEvento = new Evento(nombre, fechaHora, tipo, this, venue);
        eventosCreados.add(nuevoEvento);
        return nuevoEvento;
    }

    public Venue sugerirVenue(String ubicacion, int capacidadMaxima, String restriccionesUso) {
        Venue sugerido = new Venue(ubicacion, capacidadMaxima, restriccionesUso, this, true);
        venuesSugeridos.add(sugerido);
        return sugerido;
    }

    public Oferta crearOferta(String nombre, double porcentajeDescuento, LocalDateTime fechaInicio,
                              LocalDateTime fechaFin, Localidad localidad) {
        if (localidad == null)
            throw new IllegalArgumentException("Debe especificar una localidad para aplicar la oferta.");
        Oferta oferta = new Oferta(nombre, porcentajeDescuento, fechaInicio, fechaFin, this, localidad);
        ofertasCreadas.add(oferta);
        return oferta;
    }

    public void mostrarEventos() {
        System.out.println("Eventos creados por " + getNombreCompleto() + ":");
        for (Evento e : eventosCreados) {
            System.out.println(" - " + e);
        }
    }

    public void mostrarVenuesSugeridos() {
        System.out.println("Venues sugeridos por " + getNombreCompleto() + ":");
        for (Venue v : venuesSugeridos) {
            System.out.println(" - " + v);
        }
    }

    public void mostrarOfertas() {
        System.out.println("Ofertas creadas por " + getNombreCompleto() + ":");
        for (Oferta o : ofertasCreadas) {
            System.out.println(" - " + o);
        }
    }

    @Override
    public String toString() {
        return "OrganizadorDeEventos{" +
                "nombre='" + getNombreCompleto() + '\'' +
                ", saldo=" + getSaldo() +
                ", eventosCreados=" + eventosCreados.size() +
                ", venuesSugeridos=" + venuesSugeridos.size() +
                ", ofertasCreadas=" + ofertasCreadas.size() +
                '}';
    }
}
