package consola;

import java.util.List;
import java.util.Scanner;

import logica.persistencia.PersistenciaBinaria;
import logica.usuario.Administrador;
import logica.usuario.Cliente;
import logica.tiquete.Tiquete;

public class ConsolaCliente {

    private static final String RUTA_TIQ = "data/tiquetes.dat";
    private static final String RUTA_CLIENTES = "data/clientes.dat";
    private Scanner sc;
    private Cliente cliente;
    private Administrador admin;
    private List<Tiquete> tiquetesDisponibles;
    private List<Cliente> clientes;

    public ConsolaCliente(Cliente cliente, Administrador admin) {
        this.cliente = cliente;
        this.admin = admin;
        this.sc = new Scanner(System.in);

        try {
            this.tiquetesDisponibles = PersistenciaBinaria.cargarDatos(RUTA_TIQ);
        } catch (Exception e) {
            this.tiquetesDisponibles = new java.util.ArrayList<>();
        }

        try {
            this.clientes = PersistenciaBinaria.cargarDatos(RUTA_CLIENTES);
        } catch (Exception e) {
            this.clientes = new java.util.ArrayList<>();
        }
    }

    public void ejecutarAplicacion() {
        System.out.println("=== SISTEMA CLIENTE ===");
        if (!autenticar()) {
            System.out.println("Credenciales incorrectas. Saliendo...");
            return;
        }

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== MENÚ CLIENTE ===");
            System.out.println("1. Ver tiquetes disponibles");
            System.out.println("2. Comprar tiquete");
            System.out.println("3. Ver mis tiquetes");
            System.out.println("4. Solicitar reembolso");
            System.out.println("5. Transferir tiquete");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(sc.nextLine());
            switch (opcion) {
                case 1 -> verTiquetesDisponibles();
                case 2 -> comprarTiquete();
                case 3 -> cliente.mostrarTiquetes();
                case 4 -> solicitarReembolso();
                case 5 -> transferirTiquete();
                case 0 -> {
                    guardarDatos();
                    continuar = false;
                }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private boolean autenticar() {
        System.out.print("Usuario: ");
        String usuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine();
        return cliente.getNombreUsuario().equals(usuario) && cliente.getContrasena().equals(contrasena);
    }

    private void verTiquetesDisponibles() {
        System.out.println("\n--- TIQUETES DISPONIBLES ---");
        boolean hayDisponibles = false;
        for (Tiquete t : tiquetesDisponibles) {
            if (!t.isVendido()) {
                System.out.println(t);
                hayDisponibles = true;
            }
        }
        if (!hayDisponibles)
            System.out.println("No hay tiquetes disponibles actualmente.");
    }

    private void comprarTiquete() {
        System.out.print("Ingrese el ID del tiquete a comprar: ");
        String id = sc.nextLine();
        Tiquete t = buscarTiquete(id);
        if (t != null) {
            cliente.comprarTiquete(t);
        } else {
            System.out.println("No se encontró el tiquete.");
        }
    }

    private void solicitarReembolso() {
        System.out.print("Ingrese el ID del tiquete para reembolso: ");
        String id = sc.nextLine();
        Tiquete t = buscarTiqueteCliente(id);
        if (t != null) {
            System.out.print("Motivo del reembolso: ");
            String motivo = sc.nextLine();
            String resultado = cliente.solicitarReembolso(t, admin, motivo);
            System.out.println(resultado);
        } else {
            System.out.println("No se encontró el tiquete en su lista.");
        }
    }

    private void transferirTiquete() {
        System.out.print("Ingrese el ID del tiquete a transferir: ");
        String id = sc.nextLine();
        Tiquete t = buscarTiqueteCliente(id);
        if (t == null) {
            System.out.println("No posee un tiquete con ese ID.");
            return;
        }

        System.out.print("Ingrese el nombre de usuario del nuevo dueño: ");
        String nombre = sc.nextLine();
        Cliente destino = buscarCliente(nombre);
        if (destino == null) {
            System.out.println("No existe un cliente con ese nombre de usuario.");
            return;
        }

        if (cliente.transferirTiquete(t, destino)) {
            System.out.println("Tiquete transferido correctamente.");
        } else {
            System.out.println("No se pudo transferir el tiquete.");
        }
    }

    private Tiquete buscarTiquete(String id) {
        for (Tiquete t : tiquetesDisponibles) {
        	if (String.valueOf(t.getIdentificador()).equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    private Tiquete buscarTiqueteCliente(String id) {
        for (Tiquete t : cliente.getTiquetesComprados()) {
        	if (String.valueOf(t.getIdentificador()).equalsIgnoreCase(id)) {
                return t;
            }
        }
        return null;
    }

    private Cliente buscarCliente(String nombreUsuario) {
        for (Cliente c : clientes) {
            if (c.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return c;
            }
        }
        return null;
    }

    private void guardarDatos() {
        try {
            PersistenciaBinaria.guardarDatos(tiquetesDisponibles, RUTA_TIQ);
            PersistenciaBinaria.guardarDatos(clientes, RUTA_CLIENTES);
        } catch (Exception e) {
            System.out.println("Error guardando datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Administrador admin = new Administrador("admin", "1234", "Administrador General");
        Cliente cliente = new Cliente("juan", "1234", "Juan Pérez", 500.0);
        ConsolaCliente consola = new ConsolaCliente(cliente, admin);
        consola.ejecutarAplicacion();
    }
}
