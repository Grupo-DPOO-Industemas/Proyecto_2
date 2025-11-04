package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import logica.reventa.Marketplace;
import logica.reventa.Registro;
import logica.tiquete.Tiquete;
import logica.usuario.Administrador;
import logica.usuario.Cliente;

public class MarketplaceTest {
    private Marketplace marketplace;
    private Administrador administrador;
    private Cliente clienteAndrea;
    private Cliente clienteFelipe;
    private Tiquete tiqueteAndrea;
    private Tiquete tiqueteFelipe;

    @BeforeEach
    void setUp() {
        administrador = new Administrador("hector_admin", "0000", "Hector");
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
            @Override public double calcularPrecioFinal() { 
            	return 0; 
            	}
            @Override public boolean isDeluxe() { 
            	return false; 
            	}
        };

        clienteAndrea.agregarTiquete(tiqueteAndrea);
        clienteFelipe.agregarTiquete(tiqueteFelipe);
    }

    @Test
    void testAgregarTiqueteEnVenta() {
        int inicial = marketplace.getLogRegistros().size();
        marketplace.agregarTiqueteEnVenta(tiqueteAndrea, 200);
        assertTrue(marketplace.getTiquetesEnVenta().containsKey(tiqueteAndrea.getIdentificador()));
        assertEquals(inicial + 1, marketplace.getLogRegistros().size());
    }

    @Test
    void testEliminarTiqueteDeVenta() {
        marketplace.agregarTiqueteEnVenta(tiqueteAndrea, 200);
        int inicial = marketplace.getLogRegistros().size();
        marketplace.eliminarTiqueteDeVenta(tiqueteAndrea);
        assertFalse(marketplace.getTiquetesEnVenta().containsKey(tiqueteAndrea.getIdentificador()));
        assertEquals(inicial + 1, marketplace.getLogRegistros().size());
    }

    @Test
    void testContraOfertaTiquete() {
        marketplace.agregarTiqueteEnVenta(tiqueteFelipe, 300);
        int inicial = marketplace.getLogRegistros().size();
        marketplace.contraOfertaTiquete(tiqueteFelipe, 250, clienteAndrea);
        assertEquals(inicial + 1, marketplace.getLogRegistros().size());
    }

    @Test
    void testAceptarOfertaTiquete() {
        marketplace.agregarTiqueteEnVenta(tiqueteFelipe, 300);
        int inicial = marketplace.getLogRegistros().size();
        marketplace.aceptarOfertaTiquete(tiqueteFelipe, 250, clienteAndrea);
        assertFalse(marketplace.getTiquetesEnVenta().containsKey(tiqueteFelipe.getIdentificador()));
        assertEquals(inicial + 1, marketplace.getLogRegistros().size());
        assertTrue(clienteAndrea.getTiquetesComprados().contains(tiqueteFelipe));
    }
    
    @Test
    void testRechazarOfertaTiquete() {
        int inicial = marketplace.getLogRegistros().size(); // definir inicial
        marketplace.rechazarOfertaTiquete(tiqueteAndrea, 200);
        assertEquals(inicial + 1, marketplace.getLogRegistros().size()); // ahora no debería salir azul
        assertTrue(marketplace.getLogRegistros()
            .get(marketplace.getLogRegistros().size() - 1).getDetalle().contains("Rechazar la oferta"));
    }



    @Test
    void testEliminarContraOfertaPorId() {
        marketplace.agregarTiqueteEnVenta(tiqueteAndrea, 200);
        clienteAndrea.contraOfertar(marketplace, tiqueteAndrea, 150);
        int tamañoInicial = marketplace.getLogRegistros().size();
        int registroId = marketplace.getLogRegistros().get(tamañoInicial - 1).getId();
        marketplace.eliminarContraOfertaPorId(registroId, administrador);
        Registro ultimoRegistro = marketplace.getLogRegistros().get(marketplace.getLogRegistros().size() - 1);
        assertTrue(ultimoRegistro.getDetalle().contains("eliminó la contraoferta"));
        assertEquals(tamañoInicial, marketplace.getLogRegistros().size());
    }


}
