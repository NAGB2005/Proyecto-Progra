
package org.example.pelicula.view;
import org.example.pelicula.model.Asiento; // Importa la clase Asiento

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PanelAsientos extends JFrame {

    // Mantener la información básica para display
    public String peliculaTitulo;
    public String horaSeleccionada;

    private JPanel seatsGridPanel;
    private JLabel selectedSeatsLabel;
    private JLabel totalPriceLabel;
    // El precio ya no es una variable local, sino que se pasará
    private double ticketPrice;

    // Referencia al listener (que será el controlador)
    private InterfazPanelAsientos listener;

    // Se recibe el precio por ticket desde el controlador
    public PanelAsientos(String peliculaTitulo, String horaSeleccionada, double ticketPrice) {
        super("Selección de Asientos para " + peliculaTitulo + " - " + horaSeleccionada);
        this.peliculaTitulo = peliculaTitulo;
        this.horaSeleccionada = horaSeleccionada;
        this.ticketPrice = ticketPrice;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(26, 26, 26));
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(34, 34, 34));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Selecciona tus Asientos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 204, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel movieInfoLabel = new JLabel(peliculaTitulo + " - " + horaSeleccionada);
        movieInfoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        movieInfoLabel.setForeground(new Color(170, 170, 170));
        movieInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(movieInfoLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(26, 26, 26));
        centerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel screenPanel = new JPanel();
        screenPanel.setBackground(new Color(80, 80, 80));
        screenPanel.setPreferredSize(new Dimension(600, 30));
        JLabel screenLabel = new JLabel("PANTALLA");
        screenLabel.setForeground(Color.WHITE);
        screenLabel.setFont(new Font("Arial", Font.BOLD, 18));
        screenPanel.add(screenLabel);
        centerPanel.add(screenPanel, BorderLayout.NORTH);

        seatsGridPanel = new JPanel(new GridLayout(8, 10, 5, 5));
        seatsGridPanel.setBackground(new Color(26, 26, 26));
        seatsGridPanel.setBorder(new EmptyBorder(20, 0, 20, 0));
        // populateSeats() ya no se llama aquí, lo hará el controlador al recibir los datos del modelo
        centerPanel.add(seatsGridPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(34, 34, 34));
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JPanel summaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        summaryPanel.setBackground(new Color(34, 34, 34));

        selectedSeatsLabel = new JLabel("Asientos seleccionados: Ninguno");
        selectedSeatsLabel.setForeground(Color.WHITE);
        selectedSeatsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        summaryPanel.add(selectedSeatsLabel);

        totalPriceLabel = new JLabel("Total: $0.00");
        totalPriceLabel.setForeground(new Color(255, 204, 0));
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        summaryPanel.add(totalPriceLabel);

        bottomPanel.add(summaryPanel, BorderLayout.WEST);

        JButton buyButton = new JButton("Comprar Entradas");
        buyButton.setBackground(new Color(0, 150, 0));
        buyButton.setForeground(Color.WHITE);
        buyButton.setFont(new Font("Arial", Font.BOLD, 16));
        buyButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        buyButton.setFocusPainted(false);
        buyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buyButton.addActionListener(e -> {
            // Notificar al controlador que se hizo clic en "Comprar"
            if (listener != null) {
                listener.onBuyTickets();
            }
        });
        bottomPanel.add(buyButton, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Método para que el controlador se "registre" como listener
    public void setPeliculaActionListener(InterfazPanelAsientos listener) {
        this.listener = listener;
    }

    // Nuevo método para que el controlador actualice el display de asientos
    public void updateSeatsDisplay(List<Asiento> asientos) {
        seatsGridPanel.removeAll(); // Limpiar antes de redibujar
        char rowChar = 'A';
        int seatCount = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 1; col <= 10; col++) {
                String seatName = String.valueOf(rowChar) + col;
                // Buscar el asiento por nombre en la lista recibida
                Asiento currentAsiento = null;
                if (seatCount < asientos.size()) {
                    currentAsiento = asientos.get(seatCount);
                    seatCount++;
                }

                JButton seatButton = new JButton(seatName);
                seatButton.setPreferredSize(new Dimension(60, 40));
                seatButton.setFont(new Font("Arial", Font.BOLD, 12));
                seatButton.setFocusPainted(false);

                if (currentAsiento != null) {
                    if (currentAsiento.isOcupado()) {
                        seatButton.setBackground(new Color(100, 100, 100)); // Gris oscuro para ocupado
                        seatButton.setForeground(Color.WHITE);
                        seatButton.setEnabled(false);
                    } else if (currentAsiento.isSeleccionado()) {
                        seatButton.setBackground(new Color(255, 204, 0)); // Amarillo de selección
                        seatButton.setForeground(Color.BLACK); // Texto negro para contraste
                        seatButton.addActionListener(new SeatSelectionListener(seatName)); // Pasa solo el nombre
                    } else {
                        seatButton.setBackground(new Color(50, 50, 50)); // Gris para disponible
                        seatButton.setForeground(Color.WHITE);
                        seatButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        seatButton.addActionListener(new SeatSelectionListener(seatName)); // Pasa solo el nombre
                    }
                } else {
                    // Si no hay información de asiento, deshabilitar o manejar como vacío
                    seatButton.setBackground(new Color(80, 80, 80));
                    seatButton.setForeground(Color.WHITE);
                    seatButton.setEnabled(false);
                }
                seatsGridPanel.add(seatButton);
            }
            rowChar++;
        }
        seatsGridPanel.revalidate();
        seatsGridPanel.repaint();
    }

    // Nuevo método para que el controlador actualice el resumen de la compra
    public void updateSummaryDisplay(List<Asiento> selectedAsientos, double totalPrice) {
        String seatsText = "Asientos seleccionados: ";
        if (selectedAsientos.isEmpty()) {
            seatsText += "Ninguno";
        } else {
            for (int i = 0; i < selectedAsientos.size(); i++) {
                seatsText += selectedAsientos.get(i).getNombre();
                if (i < selectedAsientos.size() - 1) {
                    seatsText += ", ";
                }
            }
        }
        selectedSeatsLabel.setText(seatsText);
        totalPriceLabel.setText(String.format("Total: $%.2f", totalPrice));
    }

    // El listener de los botones de asiento solo notifica al controlador el nombre del asiento
    private class SeatSelectionListener implements ActionListener {
        private String seatName;

        public SeatSelectionListener(String seatName) {
            this.seatName = seatName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (listener != null) {
                listener.onSeatToggle(seatName);
            }
        }
    }
}