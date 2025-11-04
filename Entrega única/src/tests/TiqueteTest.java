package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.usuario.Cliente;
import logica.tiquete.Tiquete;

public class TiqueteTest {

    private Tiquete tiquete;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("Andre", "1234", "Andrea", 50000);
       
        tiquete = new Tiquete(1, 100, 0.1, 5, null, null, "10/03/2025", "10:00", "disponible") {
            private static final long serialVersionUID = 1L;

            @Override
            public double calcularPrecioFinal() {
                return precioBase + precioBase * cargoPorcentual + cuotaAdicionalEmision;
            }

            @Override
            public boolean isDeluxe() {
                return false;
            }
        };
        
        tiquete.setDueno(cliente);
    }

    @Test
    void testAtributosTiquete() {
        assertEquals(1, tiquete.getIdentificador());
        assertEquals(100, tiquete.getPrecioBase());
        assertEquals(0.1, tiquete.getCargoPorcentual());
        assertEquals(5, tiquete.getCuotaAdicionalEmision());
        assertEquals("10/03/2025", tiquete.getFecha());
        assertEquals("10:00", tiquete.getHora());
        assertEquals("disponible", tiquete.getEstado());
        assertEquals(cliente, tiquete.getDueno());
    }

    @Test
    void testCalcularPrecioFinal() {
        double resultado = 100 + 100 * 0.1 + 5; 
        assertEquals(resultado, tiquete.calcularPrecioFinal(), 0.01);
    }

    @Test
    void testIsDeluxe() {
        assertFalse(tiquete.isDeluxe());
    }
}
