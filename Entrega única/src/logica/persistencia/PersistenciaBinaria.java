package logica.persistencia;

import java.io.*;
import java.util.List;

public class PersistenciaBinaria {

    public static <T> void guardarDatos(List<T> lista, String nombreArchivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            oos.writeObject(lista);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> cargarDatos(String nombreArchivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            return (List<T>) ois.readObject();
        }
    }
}
