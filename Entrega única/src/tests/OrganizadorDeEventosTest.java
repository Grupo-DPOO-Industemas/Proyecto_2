package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.evento.Evento;
import logica.evento.Localidad;
import logica.evento.Venue;
import logica.usuario.OrganizadorDeEventos;

public class OrganizadorDeEventosTest {
    private OrganizadorDeEventos organizador;
    private Venue venue;

    @BeforeEach
    void setUp() {
        organizador = new OrganizadorDeEventos("Sebas", "abcd", "Sebastián", 100000);
        venue = new Venue("Movistar_arena", 100, "Sin restricciones", organizador, true);
    }

    @Test
    void testSetAprobado() {
        assertFalse(organizador.isAprobado());
        organizador.setAprobado(true);
        assertTrue(organizador.isAprobado());
    }

    @Test
    void testSugerirVenue() {
        Venue nuevoVenue = organizador.sugerirVenue("Coliseo", 200, "Solo eventos culturales");
        assertEquals("Coliseo", nuevoVenue.getUbicacion());
        assertEquals(1, organizador.getVenuesSugeridos().size());
    }

    @Test
    void testCrearEventoSinAprobacion() {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            organizador.crearEvento("Concierto", LocalDateTime.now(), "Música", venue);
        });
        assertTrue(exception.getMessage().contains("No puede crear eventos"));
    }

    @Test
    void testCrearEventoConAprobacion() {
        organizador.setAprobado(true);
        venue.aprobar(null); // aprobamos el venue (null solo para simplificar el test)
        Evento evento = organizador.crearEvento("Concierto", LocalDateTime.now(), "Música", venue);
        assertEquals("Concierto", evento.getNombre());
        assertEquals(1, organizador.getEventosCreados().size());
    }

    @Test
    void testCrearOferta() {
        Localidad localidad = new Localidad("VIP", 50000, 1000, false);
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fin = inicio.plusDays(10);
        var oferta = organizador.crearOferta("Descuento especial", 0.2, inicio, fin, localidad);
        assertEquals("Descuento especial", oferta.getNombre());
        assertEquals(1, organizador.getOfertasCreadas().size());
    }
}
