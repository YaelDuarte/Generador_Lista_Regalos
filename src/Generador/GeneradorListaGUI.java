package Generador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("serial")
public class GeneradorListaGUI extends JFrame {
    private JTextField[] camposRegalos = new JTextField[15];
    private JTextArea areaResultado;
    private JButton botonGenerar, botonLimpiar;
    private ListaRegalos listaRegalos;

    public GeneradorListaGUI() {
        listaRegalos = new ListaRegalos();

        // Configuración básica de la ventana
        setTitle("🎁 Generador de Lista de Regalos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 245, 255));

        // Título principal
        JLabel titulo = new JLabel("Generador de Lista de Regalos", JLabel.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(new Color(40, 90, 190));
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel con campos de texto
        JPanel panelCampos = new JPanel(new GridLayout(15, 2, 8, 8));
        panelCampos.setBackground(new Color(240, 245, 255));
        for (int i = 0; i < 15; i++) {
            JLabel etiqueta = new JLabel((i + 1) + ".");
            etiqueta.setHorizontalAlignment(JLabel.RIGHT);
            etiqueta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            camposRegalos[i] = new JTextField();
            camposRegalos[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
            camposRegalos[i].setBackground(new Color(255, 255, 255));
            panelCampos.add(etiqueta);
            panelCampos.add(camposRegalos[i]);
        }
        add(new JScrollPane(panelCampos), BorderLayout.CENTER);

        // Panel inferior con botones y área de resultados
        JPanel panelInferior = new JPanel(new BorderLayout(10, 10));
        panelInferior.setBackground(new Color(240, 245, 255));

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(240, 245, 255));

        botonGenerar = crearBoton("🔀 Generar y Guardar Lista", new Color(46, 134, 222));
        botonGenerar.addActionListener(e -> generarLista());
        panelBotones.add(botonGenerar);

        botonLimpiar = crearBoton("🧹 Limpiar Campos", new Color(231, 76, 60));
        botonLimpiar.addActionListener(e -> limpiarCampos());
        panelBotones.add(botonLimpiar);

        panelInferior.add(panelBotones, BorderLayout.NORTH);

        // Área de texto
        areaResultado = new JTextArea(10, 40);
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Consolas", Font.PLAIN, 14));
        areaResultado.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        areaResultado.setBackground(Color.WHITE);
        panelInferior.add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        return boton;
    }

    private void generarLista() {
        listaRegalos.limpiarLista();
        for (JTextField campo : camposRegalos) {
            listaRegalos.agregarRegalo(campo.getText());
        }

        if (!listaRegalos.estaCompleta(15)) {
            JOptionPane.showMessageDialog(this, "⚠️ Debes ingresar los 15 regalos.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<String> listaMezclada = listaRegalos.obtenerListaMezclada();

        StringBuilder texto = new StringBuilder("--- Lista de regalos desordenada ---\n");
        for (int i = 0; i < listaMezclada.size(); i++) {
            texto.append((i + 1)).append(". ").append(listaMezclada.get(i)).append("\n");
        }
        areaResultado.setText(texto.toString());

        try {
            listaRegalos.guardarEnArchivo(listaMezclada, "lista_regalos.txt");
            JOptionPane.showMessageDialog(this, "✅ Lista guardada en 'lista_regalos.txt'", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al guardar el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void limpiarCampos() {
        for (JTextField campo : camposRegalos) {
            campo.setText("");
        }
        areaResultado.setText("");
        listaRegalos.limpiarLista();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GeneradorListaGUI().setVisible(true);
        });
    }
}
