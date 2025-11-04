package logica.reventa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import logica.usuario.Cliente;
import logica.usuario.Administrador;
import logica.tiquete.Tiquete;

public class Marketplace {
	private List<Cliente> clientesInscritos;
	private Administrador administrador;
	private List<Registro> logRegistros;
	private HashMap<Integer, Tiquete> tiquetesEnVenta;
	
	public Marketplace(List<Cliente> clientesInscritos, Administrador administrador) {
		super();
		this.clientesInscritos = clientesInscritos;
		this.administrador = administrador;
		this.logRegistros = new ArrayList<>();
		this.tiquetesEnVenta = new HashMap<>();
	}

	public List<Cliente> getClientesInscritos() {
		return clientesInscritos;
	}

	public void setClientesInscritos(List<Cliente> clientesInscritos) {
		this.clientesInscritos = clientesInscritos;
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public List<Registro> getLogRegistros() {
		return logRegistros;
	}

	public void setLogRegistros(List<Registro> logRegistros) {
		this.logRegistros = logRegistros;
	}

	public HashMap<Integer, Tiquete> getTiquetesEnVenta() {
		return tiquetesEnVenta;
	}

	public void setTiquetesEnVenta(HashMap<Integer, Tiquete> tiquetesEnVenta) {
		this.tiquetesEnVenta = tiquetesEnVenta;
	}

	public void registrarCliente(Cliente cliente) {
		clientesInscritos.add(cliente);
	}
	
	public void agregarTiqueteEnVenta(Tiquete tiquete, double precio) {
		tiquetesEnVenta.put(tiquete.getIdentificador(), tiquete);
		Registro registro = new Registro(logRegistros.size() + 1, precio, "Poner tiquete en venta a un precio de " + precio + ".", tiquete.getDueno(), tiquete);
		logRegistros.add(registro);
	}
	
	public void eliminarTiqueteDeVenta(Tiquete tiquete) {
		tiquetesEnVenta.remove(tiquete.getIdentificador());
		Registro registro = new Registro(logRegistros.size() + 1, 0, "Eliminar tiquete de la venta.", tiquete.getDueno(), tiquete);
		logRegistros.add(registro);
	}
	
	public void contraOfertaTiquete(Tiquete tiquete, double precioOfertado, Cliente cliente) {
		Registro registro = new Registro(logRegistros.size() + 1, precioOfertado, "Hacer una contraoferta de " + precioOfertado + " por el tiquete.", cliente, tiquete);
		logRegistros.add(registro);
	}
	
	public void aceptarOfertaTiquete(Tiquete tiquete, double precioOfertado, Cliente cliente) {
		Registro registro = new Registro(logRegistros.size() + 1, precioOfertado, "Aceptar la oferta de " + precioOfertado + " del cliente " + cliente.getNombreUsuario() + ".", tiquete.getDueno(), tiquete);
		logRegistros.add(registro);
		tiquetesEnVenta.remove(tiquete.getIdentificador());
		tiquete.getDueno().aumentarSaldo(precioOfertado);
		tiquete.getDueno().transferirTiquete(tiquete, cliente);
		cliente.descontarSaldo(precioOfertado);
	}
	
	public void rechazarOfertaTiquete(Tiquete tiquete, double precioOfrecido) {
		Registro registro = new Registro(logRegistros.size() + 1, precioOfrecido, "Rechazar la oferta de " + precioOfrecido + " por el tiquete.", tiquete.getDueno(), tiquete);
		logRegistros.add(registro);
	}
	
	public void registrarVentaTiquete(Tiquete tiquete, double precio, Cliente cliente) {
		Registro registro = new Registro(logRegistros.size() + 1, precio, "Vender el tiquete por " + precio + " al cliente " + cliente.getNombreUsuario() + ".", tiquete.getDueno(), tiquete);
		logRegistros.add(registro);
		tiquetesEnVenta.remove(tiquete.getIdentificador());
		tiquete.getDueno().aumentarSaldo(precio);
		tiquete.getDueno().transferirTiquete(tiquete, cliente);
		cliente.descontarSaldo(precio);
	}
	
	public void eliminarContraOfertaPorId(int registroId, Administrador administrador) {
	    
		Registro registroAEliminar = null;
	    for (Registro registro : logRegistros) {
	        if (registro.getId() == registroId && registro.getDetalle().toLowerCase().contains("contraoferta")) {
	            registroAEliminar = registro;
	        }
	    }
	    logRegistros.remove(registroAEliminar);
	    Registro registro = new Registro(logRegistros.size() + 1, 0, "El administrador eliminó la contraoferta #" + registroId, administrador, registroAEliminar.getTiqueteInvolucrado());
	    logRegistros.add(registro);
	}
	
	

	
	
}
