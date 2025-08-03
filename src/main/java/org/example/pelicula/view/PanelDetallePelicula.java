package org.example.pelicula.view;
import org.example.pelicula.model.Pelicula;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.List;

public class PanelDetallePelicula extends JFrame {

    private final Pelicula pelicula;
    private JLabel posterLabel;
    private final InterfazPanelPrincipal listener;

    public PanelDetallePelicula(Pelicula pelicula, InterfazPanelPrincipal listener) {
        super("Detalle de la Película: " + pelicula.getTitulo());
        this.pelicula = pelicula;
        this.listener = listener;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(26, 26, 26));
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(26, 26, 26));
        topPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        JButton backButton = new JButton("← Volver a Cartelera");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setForeground(new Color(255, 204, 0));
        backButton.setBackground(new Color(51, 51, 51));
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> dispose());
        topPanel.add(backButton, BorderLayout.WEST);

        JLabel mainTitleLabel = new JLabel(pelicula.getTitulo());
        mainTitleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        mainTitleLabel.setForeground(new Color(255, 204, 0));
        mainTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(mainTitleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(26, 26, 26));
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        posterLabel = new JLabel();
        posterLabel.setPreferredSize(new Dimension(250, 375));
        posterLabel.setBorder(BorderFactory.createLineBorder(new Color(85, 85, 85), 2));
        loadImage(pelicula.getImagen());
        contentPanel.add(posterLabel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(26, 26, 26));
        infoPanel.setBorder(new EmptyBorder(0, 20, 0, 0));

        JLabel titleLabel = new JLabel("Título: " + pelicula.getTitulo());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel durationLabel = new JLabel("Duración: " + pelicula.getDuracion());
        durationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        durationLabel.setForeground(new Color(170, 170, 170));
        durationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ageRatingLabel = new JLabel("Clasificación: " + pelicula.getClasificacionedad());
        ageRatingLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        ageRatingLabel.setForeground(new Color(170, 170, 170));
        ageRatingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statusLabel = new JLabel("Estado: " + pelicula.getEstado());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        statusLabel.setForeground(new Color(170, 170, 170));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel functionOptionsPanel = new JPanel();
        functionOptionsPanel.setBackground(new Color(34, 34, 34));
        functionOptionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        functionOptionsPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel funcionesLabel = new JLabel("Funciones:");
        funcionesLabel.setForeground(Color.WHITE);
        funcionesLabel.setFont(new Font("Arial", Font.BOLD, 14));
        functionOptionsPanel.add(funcionesLabel);

        String[] idiomas = {"Todos Los Idiomas", "Español", "Inglés"};
        JComboBox<String> idiomaComboBox = new JComboBox<>(idiomas);
        idiomaComboBox.setBackground(new Color(51, 51, 51));
        idiomaComboBox.setForeground(Color.WHITE);
        functionOptionsPanel.add(idiomaComboBox);

        String[] formatos = {"Todos Los Formatos", "2D", "3D"};
        JComboBox<String> formatoComboBox = new JComboBox<>(formatos);
        formatoComboBox.setBackground(new Color(51, 51, 51));
        formatoComboBox.setForeground(Color.WHITE);
        functionOptionsPanel.add(formatoComboBox);

        String[] salas = {"Todas Las Salas", "Sala 1", "Sala 2", "Sala VIP"};
        JComboBox<String> salaComboBox = new JComboBox<>(salas);
        salaComboBox.setBackground(new Color(51, 51, 51));
        salaComboBox.setForeground(Color.WHITE);
        functionOptionsPanel.add(salaComboBox);

        JPanel dateSelectionPanel = new JPanel();
        dateSelectionPanel.setBackground(new Color(34, 34, 34));
        dateSelectionPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dateSelectionPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel monthLabel = new JLabel("Julio");
        monthLabel.setFont(new Font("Arial", Font.BOLD, 18));
        monthLabel.setForeground(Color.WHITE);
        dateSelectionPanel.add(monthLabel);

        String[] days = {"Mié. 23", "Jue. 24", "Vie. 25", "Sáb. 26", "Dom. 27", "Lun. 28", "Mar. 29"};
        for (String day : days) {
            JButton dayButton = new JButton(day);
            dayButton.setBackground(new Color(51, 51, 51));
            dayButton.setForeground(Color.WHITE);
            dayButton.setFont(new Font("Arial", Font.BOLD, 14));
            dayButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
            dayButton.setFocusPainted(false);
            dayButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            if (day.contains("24")) {
                dayButton.setBackground(new Color(255, 204, 0));
                dayButton.setForeground(new Color(26, 26, 26));
            }
            dateSelectionPanel.add(dayButton);
        }

        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        legendPanel.setBackground(new Color(26, 26, 26));
        legendPanel.add(createLegendItem(new Color(0, 150, 0), "Disponibles"));
        legendPanel.add(createLegendItem(new Color(170, 170, 170), "No Disponible"));
        legendPanel.add(createLegendItem(Color.RED, "Agotado"));

        JPanel schedulesPanel = new JPanel();
        schedulesPanel.setBackground(new Color(26, 26, 26));
        schedulesPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
        schedulesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(85, 85, 85)), "Horarios Disponibles",
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16), Color.WHITE));

        for (Pelicula.Funcion funcion : pelicula.getFunciones()) {
            boolean available = funcion.getAsientosOcupados().size() < 80;
            addScheduleButton(schedulesPanel, funcion.getHorario(), available);
        }

        infoPanel.add(titleLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(durationLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(ageRatingLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(statusLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoPanel.add(functionOptionsPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoPanel.add(dateSelectionPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(legendPanel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        infoPanel.add(schedulesPanel);

        contentPanel.add(infoPanel);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void loadImage(String imageName) {
        ImageIcon originalIcon = null;
        try {
            originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/" + imageName)));
        } catch (Exception e) {
            BufferedImage placeholderImage = new BufferedImage(
                    posterLabel.getPreferredSize().width,
                    posterLabel.getPreferredSize().height,
                    BufferedImage.TYPE_INT_ARGB
            );
            Graphics2D g = placeholderImage.createGraphics();
            g.setColor(new Color(51, 51, 51));
            g.fillRect(0, 0, placeholderImage.getWidth(), placeholderImage.getHeight());
            g.setColor(new Color(200, 200, 200));
            String message = "Imagen no disponible";
            FontMetrics fm = g.getFontMetrics();
            int x = (placeholderImage.getWidth() - fm.stringWidth(message)) / 2;
            int y = (fm.getAscent() + (placeholderImage.getHeight() - (fm.getAscent() + fm.getDescent())) / 2);
            g.drawString(message, x, y);
            g.dispose();
            originalIcon = new ImageIcon(placeholderImage);
        }

        Image scaledImage = originalIcon.getImage().getScaledInstance(
                posterLabel.getPreferredSize().width,
                posterLabel.getPreferredSize().height,
                Image.SCALE_SMOOTH
        );
        posterLabel.setIcon(new ImageIcon(scaledImage));
    }

    private JPanel createLegendItem(Color color, String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(new Color(26, 26, 26));
        JLabel colorDot = new JLabel("●");
        colorDot.setForeground(color);
        colorDot.setFont(new Font("Arial", Font.BOLD, 18));
        JLabel textLabel = new JLabel(text);
        textLabel.setForeground(new Color(170, 170, 170));
        textLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        panel.add(colorDot);
        panel.add(textLabel);
        return panel;
    }

    private void addScheduleButton(JPanel parentPanel, String time, boolean available) {
        JButton scheduleButton = new JButton(time);
        scheduleButton.setFont(new Font("Arial", Font.BOLD, 14));
        scheduleButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        scheduleButton.setFocusPainted(false);
        scheduleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (available) {
            scheduleButton.setBackground(new Color(51, 51, 51));
            scheduleButton.setForeground(Color.WHITE);
            scheduleButton.addActionListener(e -> {
                if (listener != null) {
                    listener.onScheduleSelected(pelicula, time);
                }
                dispose();
            });
        } else {
            scheduleButton.setBackground(new Color(80, 80, 80));
            scheduleButton.setForeground(new Color(120, 120, 120));
            scheduleButton.setEnabled(false);
        }
        parentPanel.add(scheduleButton);
    }
}