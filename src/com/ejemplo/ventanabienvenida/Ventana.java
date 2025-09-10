package com.ejemplo.ventanabienvenida;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class Ventana {
    private JFrame frame;
    private ResourceBundle mensajes;
    private JPanel panel;
    private JButton boton;
    private JComboBox<String> comboIdiomas;

    public Ventana(ResourceBundle mensajes) {
        this.mensajes = mensajes;
        inicializar();
    }

    private void inicializar() {
        frame = new JFrame(mensajes.getString("titulo"));
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Icono (opcional)
        ImageIcon icono = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
        frame.setIconImage(icono.getImage());

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(135, 206, 250);
                Color color2 = new Color(25, 25, 112);
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        panel.setLayout(new BorderLayout(10, 10));

        // Combo para seleccionar idioma
        comboIdiomas = new JComboBox<>(new String[] {"Español", "English"});
        comboIdiomas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboIdiomas.setSelectedIndex(mensajes.getLocale().getLanguage().equals("en") ? 1 : 0);

        comboIdiomas.addActionListener(e -> {
            String idiomaSeleccionado = (String) comboIdiomas.getSelectedItem();
            Locale nuevoLocale;
            if ("English".equals(idiomaSeleccionado)) {
                nuevoLocale = new Locale("en");
            } else {
                nuevoLocale = new Locale("es");
            }
            cambiarIdioma(nuevoLocale);
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(comboIdiomas);

        // Botón estilizado
        boton = new JButton(mensajes.getString("boton_saludo"));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(70, 130, 180));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(new Color(100, 149, 237));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(new Color(70, 130, 180));
            }
        });
        boton.addActionListener(e -> mostrarSaludo());

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(boton, BorderLayout.CENTER);

        frame.setContentPane(panel);
    }

    private void cambiarIdioma(Locale locale) {
        mensajes = ResourceBundle.getBundle("Mensajes", locale);
        frame.setTitle(mensajes.getString("titulo"));
        boton.setText(mensajes.getString("boton_saludo"));
        // Si hay más componentes con texto, actualízalos aquí

        // Refrescar ventana
        frame.invalidate();
        frame.validate();
        frame.repaint();
    }

    private void mostrarSaludo() {
        String nombre = JOptionPane.showInputDialog(frame, mensajes.getString("pregunta_nombre"));
        if (nombre != null) {
            nombre = nombre.trim();
            if (!nombre.isEmpty()) {
                ImageIcon saludoIcon = new ImageIcon(getClass().getClassLoader().getResource("icon.png"));
                JOptionPane.showMessageDialog(frame,
                        mensajes.getString("saludo") + " " + nombre + "! " + mensajes.getString("mensaje_buen_trabajo"),
                        mensajes.getString("titulo"),
                        JOptionPane.INFORMATION_MESSAGE,
                        saludoIcon);
            } else {
                JOptionPane.showMessageDialog(frame,
                        mensajes.getString("error_nombre"),
                        mensajes.getString("titulo_error"),
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}
