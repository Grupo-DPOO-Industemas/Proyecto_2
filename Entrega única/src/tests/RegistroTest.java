package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.reventa.Registro;
import logica.tiquete.Tiquete;
import logica.usuario.Cliente;

public class RegistroTest {
    private Registro registro;
    private Cliente cliente;
    private Tiquete tiquete;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Andre", "1234", "Andrea", 50000);
        tiquete = new Tiquete(1, 100, 0.1, 5, null, null, "10/10/2025", "20:00", "disponible") {
            private static final long serialVersionUID = 1L;
            @Override public double calcularPrecioFinal() { 
            	return 0; 
            	}
            @Override public boolean isDeluxe() { 
            	return false; 
            	}
        };
        cliente.agregarTiquete(tiquete);

        registro = new Registro(1, 200, "Poner tiquete en venta", cliente, tiquete);
    }

    @Test
    void testRegistroCreadoCorrectamente() {
        assertEquals(1, registro.getId());
        assertEquals(200, registro.getPrecio(), 0.01);
        assertEquals("Poner tiquete en venta", registro.getDetalle());
        assertEquals(cliente, registro.getUsuario());
        assertEquals(tiquete, registro.getTiqueteInvolucrado());
        assertNotNull(registro.getFechaHora());
    }

    @Test
    void testSetters() {
        Cliente nuevoCliente = new Cliente("Pipe", "4321", "Felipe", 50000);
        Tiquete nuevoTiquete = new Tiquete(2, 150, 0.2, 10, null, null, "03/10/2025", "21:00", "disponible") {
            private static final long serialVersionUID = 1L;
            @Override public double calcularPrecioFinal() { return 0; }
            @Override public boolean isDeluxe() { return false; }
        };

        registro.setId(5);
        registro.setPrecio(300);
        registro.setDetalle("Nueva acción");
        registro.setUsuario(nuevoCliente);
        registro.setTiqueteInvolucrado(nuevoTiquete);

        assertEquals(5, registro.getId());
        assertEquals(300, registro.getPrecio(), 0.01);
        assertEquals("Nueva acción", registro.getDetalle());
        assertEquals(nuevoCliente, registro.getUsuario());
        assertEquals(nuevoTiquete, registro.getTiqueteInvolucrado());
    }
}
