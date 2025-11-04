package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.reventa.Marketplace;
import logica.tiquete.Tiquete;
import logica.usuario.Administrador;
import logica.usuario.Cliente;
import logica.usuario.OrganizadorDeEventos;

public class ClienteTest {
    private Administrador administrador;
    private Cliente clienteAndrea;
    private Cliente clienteFelipe;
    private Marketplace marketplace;
    private Tiquete tiqueteAndrea;
    private Tiquete tiqueteFelipe;

    @BeforeEach
    void setUp() {
        administrador = new Administrador("hector_admin", "0000", "Hector");
        new OrganizadorDeEventos("Sebas", "abcd", "Sebastián", 100000);
        clienteAndrea = new Cliente("Andre", "1234", "Andrea", 50000);
        clienteFelipe = new Cliente("Pipe", "4321", "Felipe", 50000);

        List<Cliente> clientes = new ArrayList<>();
        clientes.add(clienteAndrea);
        clientes.add(clienteFelipe);
        marketplace = new Marketplace(clientes, administrador);

        tiqueteAndrea = new Tiquete(1, 100, 0.1, 5, null, null, "10/10/2025", "20:00", "disponible") {
            private static final long serialVersionUID = 1L;
            @Override public double calcularPrecioFinal() { 
            	return 0; 
            	}
            @Override public boolean isDeluxe() {
            	return false; 
            	}
        };

        tiqueteFelipe = new Tiquete(2, 150, 0.2, 10, null, null, "03/10/2025", "21:00", "disponible") {
            private static final long serialVersionUID = 1L;
            @Override public double calcularPrecioFinal() { return 0; }
            @Override public boolean isDeluxe() { return false; }
        };

        clienteAndrea.agregarTiquete(tiqueteAndrea);
        clienteFelipe.agregarTiquete(tiqueteFelipe);
    }

    @Test
    void testAgregarTiquete() {
        Tiquete nuevo = new Tiquete(3, 200, 0.1, 10, null, null, "05/11/2025", "19:00", "disponible") {
            private static final long serialVersionUID = 1L;
            @Override public double calcularPrecioFinal() { return 0; }
            @Override public boolean isDeluxe() { return false; }
        };
        clienteAndrea.agregarTiquete(nuevo);
        assertTrue(clienteAndrea.getTiquetesComprados().contains(nuevo));
    }

    @Test
    void testComprarTiquete() {
        Tiquete tiqueteNuevo = new Tiquete(3, 100, 0.1, 5, null, null, "15/11/2025", "18:00", "disponible") {
            private static final long serialVersionUID = 1L;
            @Override public double calcularPrecioFinal() { return 100; }
            @Override public boolean isDeluxe() { return false; }
        }; 
        boolean comprado = clienteAndrea.comprarTiquete(tiqueteNuevo);
        assertTrue(comprado);
        assertEquals(clienteAndrea, tiqueteNuevo.getDueno());
    }

    @Test
    void testSolicitarReembolso() {
        double saldoInicial = clienteAndrea.getSaldo();
        String mensaje = clienteAndrea.solicitarReembolso(tiqueteAndrea, administrador, "Calamidad");
        assertTrue(mensaje.contains("aprobada"));
        assertEquals(saldoInicial + tiqueteAndrea.getPrecioBase(), clienteAndrea.getSaldo(), 0.01);
    }

    @Test
    void testTransferirTiquete() {
        boolean transferido = clienteAndrea.transferirTiquete(tiqueteAndrea, clienteFelipe);
        assertTrue(transferido);
        assertTrue(clienteFelipe.getTiquetesComprados().contains(tiqueteAndrea));
    }

    @Test
    void testPublicarOferta() {
        clienteAndrea.publicarOferta(marketplace, tiqueteAndrea, 200);
        assertTrue(marketplace.getTiquetesEnVenta().containsKey(tiqueteAndrea.getIdentificador()));
    }

    @Test
    void testBorrarOferta() {
        clienteAndrea.publicarOferta(marketplace, tiqueteAndrea, 200);
        clienteAndrea.borrarOferta(marketplace, tiqueteAndrea);
        assertFalse(marketplace.getTiquetesEnVenta().containsKey(tiqueteAndrea.getIdentificador()));
    }

    @Test
    void testContraOfertar() {
        int tamañoInicial = marketplace.getLogRegistros().size();
        clienteFelipe.publicarOferta(marketplace, tiqueteFelipe, 300);
        clienteAndrea.contraOfertar(marketplace, tiqueteFelipe, 250);
        assertEquals(tamañoInicial + 2, marketplace.getLogRegistros().size()); 
    }

    @Test
    void testAceptarOferta() {
        clienteAndrea.publicarOferta(marketplace, tiqueteAndrea, 200);
        clienteFelipe.aceptarOferta(marketplace, tiqueteAndrea, 200, clienteFelipe);
        assertEquals(clienteFelipe, tiqueteAndrea.getDueno());
    }
}
