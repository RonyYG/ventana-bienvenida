package com.ejemplo.ventanabienvenida;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class VentanaBienvenidaAvanzada {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Locale locale = new Locale("es"); // Español por defecto
        ResourceBundle mensajes = ResourceBundle.getBundle("Mensajes", locale);

        SwingUtilities.invokeLater(() -> {
            Ventana ventana = new Ventana(mensajes);
            ventana.mostrar();
        });
    }
}
