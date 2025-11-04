package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.evento.Venue;
import logica.reventa.Marketplace;
import logica.tiquete.Tiquete;
import logica.usuario.Administrador;
import logica.usuario.Cliente;
import logica.usuario.OrganizadorDeEventos;

public class AdministradorTest {
    private Administrador administrador;
    private OrganizadorDeEventos organizador; 
    private Venue venue;
    private Cliente clienteAndrea;
    private Cliente clienteFelipe;
    private Marketplace marketplace;
    private Tiquete tiqueteAndrea;
    private Tiquete tiqueteFelipe;

    @BeforeEach
    void setUp() {
        administrador = new Administrador("hector_admin", "0000", "Hector");
        organizador = new OrganizadorDeEventos("Sebas", "abcd", "Sebastián", 100000);
        venue = new Venue("Movistar_arena", 100, "Sin restricciones", organizador, false);
        clienteAndrea = new Cliente("Andre", "1234", "Andrea", 50000);
        clienteFelipe = new Cliente("Pipe", "4321", "Felipe", 50000);

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(clienteAndrea);
        clientes.add(clienteFelipe);
        marketplace = new Marketplace(clientes, administrador);


        tiqueteAndrea = new Tiquete(1, 100, 0.1, 5, null, null, "10/10/2025", "20:00", "disponible") {
            private static final long serialVersionUID = 1L;
            @Override
            public double calcularPrecioFinal() {
                return 0;
            }
            @Override
            public boolean isDeluxe() {
                return false;
            }
        };

        tiqueteFelipe = new Tiquete(2, 150, 0.2, 10, null, null, "03/10/2025", "21:00", "disponible") {
            private static final long serialVersionUID = 1L;

            @Override
            public double calcularPrecioFinal() {
                return 0;
            }
            @Override
            public boolean isDeluxe() {
                return false;
            }
        };

        clienteAndrea.agregarTiquete(tiqueteAndrea);
        clienteFelipe.agregarTiquete(tiqueteFelipe);
    }

    @Test
    void testSetPorcentajeGananciaValido() {
    	administrador.setPorcentajeGanancia(20);
        assertEquals(20, administrador.getPorcentajeGanancia());
    }

    @Test
    void testSetPorcentajeGananciaInvalido() {
        assertThrows(IllegalArgumentException.class, () -> administrador.setPorcentajeGanancia(-5));
        assertThrows(IllegalArgumentException.class, () -> administrador.setPorcentajeGanancia(150));
    }

    @Test
    void testAprobarVenue() {
    	administrador.aprobarVenue(venue);
        assertTrue(venue.isAprobado());
    }

    @Test
    void testRevocarAprobacionVenue() {
    	administrador.aprobarVenue(venue);
        administrador.revocarAprobacionVenue(venue);
        assertFalse(venue.isAprobado());
    }

    @Test
    void testAprobarReembolsoCliente() {
        assertTrue(administrador.aprobarReembolsoCliente(tiqueteAndrea, "Calamidad"));
        assertFalse(administrador.aprobarReembolsoCliente(tiqueteAndrea, "Otro motivo"));
        assertFalse(administrador.aprobarReembolsoCliente(tiqueteAndrea, null));
    }

    @Test
    void testAprobarOrganizador() {
        assertFalse(organizador.isAprobado());
        administrador.aprobarOrganizador(organizador);
        assertTrue(organizador.isAprobado());
        assertThrows(IllegalStateException.class, () -> administrador.aprobarOrganizador(organizador));
    }


}


