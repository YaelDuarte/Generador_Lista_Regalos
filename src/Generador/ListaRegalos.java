package Generador;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaRegalos {
    private List<String> regalos;

    public ListaRegalos() {
        this.regalos = new ArrayList<>();
    }

    public void agregarRegalo(String regalo) {
        if (regalo != null && !regalo.trim().isEmpty()) {
            regalos.add(regalo.trim());
        }
    }

    public boolean estaCompleta(int cantidadEsperada) {
        return regalos.size() >= cantidadEsperada;
    }

    public List<String> obtenerListaMezclada() {
        List<String> copia = new ArrayList<>(regalos);
        Collections.shuffle(copia);
        return copia;
    }

    public void guardarEnArchivo(List<String> lista, String nombreArchivo) throws IOException {
        FileWriter archivo = new FileWriter(nombreArchivo);
        archivo.write("--- Lista de regalos desordenada ---\n");
        for (int i = 0; i < lista.size(); i++) {
            archivo.write((i + 1) + ". " + lista.get(i) + "\n");
        }
        archivo.close();
    }

    public void limpiarLista() {
        regalos.clear();
    }
}
