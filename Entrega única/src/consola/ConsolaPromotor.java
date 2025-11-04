package consola;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import logica.persistencia.PersistenciaBinaria;
import logica.usuario.OrganizadorDeEventos;
import logica.evento.Evento;
import logica.evento.Venue;
import logica.evento.Localidad;
import logica.evento.Oferta;

public class ConsolaPromotor {

    private static final String RUTA_EVENTOS = "data/eventos.dat";
    private static final String RUTA_VENUES = "data/venues.dat";
    private static final String RUTA_OFERTAS = "data/ofertas.dat";

    private Scanner sc;
    private OrganizadorDeEventos promotor;
    private List<Evento> eventos;
    private List<Venue> venues;
    private List<Oferta> ofertas;

    public ConsolaPromotor(OrganizadorDeEventos promotor) {
        this.promotor = promotor;
        this.sc = new Scanner(System.in);

        try {
            this.eventos = PersistenciaBinaria.cargarDatos(RUTA_EVENTOS);
        } catch (Exception e) {
            this.eventos = new java.util.ArrayList<>();
        }

        try {
            this.venues = PersistenciaBinaria.cargarDatos(RUTA_VENUES);
        } catch (Exception e) {
            this.venues = new java.util.ArrayList<>();
        }

        try {
            this.ofertas = PersistenciaBinaria.cargarDatos(RUTA_OFERTAS);
        } catch (Exception e) {
            this.ofertas = new java.util.ArrayList<>();
        }
    }

    public void ejecutarAplicacion() {
        System.out.println("=== SISTEMA PROMOTOR ===");
        if (!autenticar()) {
            System.out.println("Credenciales incorrectas. Saliendo...");
            return;
        }

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n=== MENÚ PROMOTOR ===");
            System.out.println("1. Crear evento");
            System.out.println("2. Sugerir nuevo venue");
            System.out.println("3. Crear oferta");
            System.out.println("4. Ver eventos creados");
            System.out.println("5. Ver venues sugeridos");
            System.out.println("6. Ver ofertas creadas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> crearEvento();
                case 2 -> sugerirVenue();
                case 3 -> crearOferta();
                case 4 -> promotor.mostrarEventos();
                case 5 -> promotor.mostrarVenuesSugeridos();
                case 6 -> promotor.mostrarOfertas();
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
        return promotor.getNombreUsuario().equals(usuario) && promotor.getContrasena().equals(contrasena);
    }

    private void crearEvento() {
        try {
            System.out.print("Nombre del evento: ");
            String nombre = sc.nextLine();
            System.out.print("Fecha y hora (YYYY-MM-DD HH:MM): ");
            String fechaStr = sc.nextLine();
            LocalDateTime fecha = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            System.out.print("Tipo de evento: ");
            String tipo = sc.nextLine();
            System.out.print("Nombre del venue: ");
            String nombreVenue = sc.nextLine();
            Venue v = buscarVenue(nombreVenue);

            if (v == null) {
                System.out.println("El venue no existe o no está aprobado.");
                return;
            }

            Evento nuevo = promotor.crearEvento(nombre, fecha, tipo, v);
            eventos.add(nuevo);
            System.out.println("Evento creado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error creando evento: " + e.getMessage());
        }
    }

    private void sugerirVenue() {
        try {
            System.out.print("Ubicación: ");
            String ubicacion = sc.nextLine();
            System.out.print("Capacidad máxima: ");
            int capacidad = Integer.parseInt(sc.nextLine());
            System.out.print("Restricciones de uso: ");
            String restricciones = sc.nextLine();

            Venue nuevo = promotor.sugerirVenue(ubicacion, capacidad, restricciones);
            venues.add(nuevo);
            System.out.println("Venue sugerido correctamente (pendiente de aprobación).");
        } catch (Exception e) {
            System.out.println("Error sugiriendo venue: " + e.getMessage());
        }
    }

    private void crearOferta() {
        try {
            System.out.print("Nombre de la oferta: ");
            String nombre = sc.nextLine();
            System.out.print("Porcentaje de descuento: ");
            double porcentaje = Double.parseDouble(sc.nextLine());
            System.out.print("Fecha inicio (YYYY-MM-DD HH:MM): ");
            String inicioStr = sc.nextLine();
            System.out.print("Fecha fin (YYYY-MM-DD HH:MM): ");
            String finStr = sc.nextLine();

            LocalDateTime inicio = LocalDateTime.parse(inicioStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime fin = LocalDateTime.parse(finStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            System.out.println("\n--- CREAR LOCALIDAD PARA LA OFERTA ---");
            System.out.print("Nombre de la localidad: ");
            String nombreLoc = sc.nextLine();
            System.out.print("Precio base: ");
            double precioBase = Double.parseDouble(sc.nextLine());
            System.out.print("Cantidad de tiquetes disponibles: ");
            int cantidad = Integer.parseInt(sc.nextLine());
            System.out.print("¿Es numerada? (true/false): ");
            boolean numerada = Boolean.parseBoolean(sc.nextLine());

            Localidad localidad = new Localidad(nombreLoc, precioBase, cantidad, numerada);

            Oferta nueva = promotor.crearOferta(nombre, porcentaje, inicio, fin, localidad);
            ofertas.add(nueva);

            System.out.println("Oferta creada exitosamente para la localidad " + localidad.getNombre() + ".");
        } catch (Exception e) {
            System.out.println("Error creando oferta: " + e.getMessage());
        }
    }


    private Venue buscarVenue(String nombre) {
        for (Venue v : venues) {
            if (v.getNombre().equalsIgnoreCase(nombre) && v.isAprobado()) {
                return v;
            }
        }
        return null;
    }

    private void guardarDatos() {
        try {
            PersistenciaBinaria.guardarDatos(eventos, RUTA_EVENTOS);
            PersistenciaBinaria.guardarDatos(venues, RUTA_VENUES);
            PersistenciaBinaria.guardarDatos(ofertas, RUTA_OFERTAS);
        } catch (Exception e) {
            System.out.println("Error guardando datos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        OrganizadorDeEventos promotor = new OrganizadorDeEventos("promotor", "1234", "Ripe Correa", 1000.0);
        ConsolaPromotor consola = new ConsolaPromotor(promotor);
        consola.ejecutarAplicacion();
    }
}
