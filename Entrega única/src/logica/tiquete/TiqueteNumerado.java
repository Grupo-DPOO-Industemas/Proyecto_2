package logica.tiquete;
import java.io.Serializable;

import logica.evento.Evento;
import logica.evento.Localidad;

public class TiqueteNumerado extends Tiquete implements Serializable {
    private static final long serialVersionUID = 1L;
	
	private int numeroAsiento;
	
	public TiqueteNumerado(String identificador, double precioBase, double cargoPorcentual, double cuotaAdicionalEmision, Localidad localidad, Evento evento, String fecha, String hora, String estado, int numeroAsiento) {
		super(identificador, precioBase, cargoPorcentual, cuotaAdicionalEmision, localidad, evento, fecha, hora, estado);
		this.numeroAsiento = numeroAsiento;
	}
	
	@Override
	public double calcularPrecioFinal() {
		return precioBase + (precioBase * cargoPorcentual) + cuotaAdicionalEmision;
	}

	public int getNumeroAsiento() {
		return numeroAsiento;
	}
	
	public void setNumeroAsiento(int numeroAsiento) {
        this.numeroAsiento = numeroAsiento;
    }	

}
