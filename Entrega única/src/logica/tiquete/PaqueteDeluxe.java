package logica.tiquete;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ArrayList;

import logica.evento.Evento;
import logica.evento.Localidad;

public class PaqueteDeluxe extends Tiquete implements Serializable{
	private static final long serialVersionUID = 1L;
	private String identificadorPaquete;
	private HashMap<String, Tiquete> tiquetesIncluidos;
	private double precioPaquete;
	private ArrayList<String> beneficiosAdicionales;
	
	public PaqueteDeluxe(String identificador, double precioBase, double cargoPorcentual, double cuotaAdicionalEmision,
			Localidad localidad, Evento evento, String fecha, String hora, String estado, String identificadorPaquete,
			HashMap<String, Tiquete> tiquetesIncluidos, double precioPaquete, ArrayList<String> beneficiosAdicionales) {
		super(identificador, precioBase, cargoPorcentual, cuotaAdicionalEmision, localidad, evento, fecha, hora,
				estado);
		this.identificadorPaquete = identificadorPaquete;
		this.tiquetesIncluidos = tiquetesIncluidos;
		this.precioPaquete = precioPaquete;
		this.beneficiosAdicionales = beneficiosAdicionales;
	}

	public String getIdentificadorPaquete() {
		return identificadorPaquete;
	}

	public void setIdentificadorPaquete(String identificadorPaquete) {
		this.identificadorPaquete = identificadorPaquete;
	}

	public HashMap<String, Tiquete> getTiquetesIncluidos() {
		return tiquetesIncluidos;
	}

	public void setTiquetesIncluidos(HashMap<String, Tiquete> tiquetesIncluidos) {
		this.tiquetesIncluidos = tiquetesIncluidos;
	}

	public double getPrecioPaquete() {
		return precioPaquete;
	}

	public void setPrecioPaquete(double precioPaquete) {
		this.precioPaquete = precioPaquete;
	}

	public ArrayList<String> getBeneficiosAdicionales() {
		return beneficiosAdicionales;
	}

	public void setBeneficiosAdicionales(ArrayList<String> beneficiosAdicionales) {
		this.beneficiosAdicionales = beneficiosAdicionales;
	}
	
	@Override
	public double calcularPrecioFinal() {
		return precioBase + (precioBase * cargoPorcentual) + cuotaAdicionalEmision;
	}
	
	
}
