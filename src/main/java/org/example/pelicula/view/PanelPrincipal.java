package org.example.pelicula.view;

import org.example.pelicula.model.Pelicula;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

/**
 * Vista principal que muestra la cartelera de películas disponibles.
 * Permite al usuario navegar a través de las películas y seleccionar una para ver sus detalles.
 */
public class PanelPrincipal extends JFrame {

    private JPanel movieGridPanel;
    private InterfazPanelPrincipal listener;

    /**
     * Constructor por defecto de la vista principal.
     * Se encarga de inicializar la interfaz de usuario sin mostrar películas.
     * Es llamado por el constructor que recibe películas.
     */
    public PanelPrincipal() {
        super("Multicines EPN - Cartelera");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(26, 26, 26));
        setLayout(new BorderLayout());

        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(new Color(26, 26, 26));
        mainContentPanel.setLayout(new BorderLayout());

        JPanel secondaryNavPanel = createSecondaryNavPanel();
        mainContentPanel.add(secondaryNavPanel, BorderLayout.NORTH);

        movieGridPanel = new JPanel();
        movieGridPanel.setBackground(new Color(26, 26, 26));
        movieGridPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 30));

        JScrollPane scroll = new JScrollPane(movieGridPanel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(new Color(26, 26, 26));
        mainContentPanel.add(scroll, BorderLayout.CENTER);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    /**
     * Constructor de la vista principal que carga y muestra una lista de películas.
     * @param peliculas La lista de películas a mostrar en la cartelera.
     */
    public PanelPrincipal(List<Pelicula> peliculas) {
        this(); // Llama al constructor por defecto para construir la ventana.
        displayPeliculas(peliculas); // Muestra las películas en la cartelera.
    }

    /**
     * Asigna un oyente de eventos para las acciones de la vista principal.
     * @param listener El objeto que implementa la interfaz InterfazPanelPrincipal.
     */
    public void setPeliculaActionListener(InterfazPanelPrincipal listener) {
        this.listener = listener;
    }

    private JPanel createHeaderPanel() {
        // ... (resto del código sin cambios) ...
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.BLACK);
        headerPanel.setLayout(new BorderLayout());

        JLabel logo = new JLabel("Multicines EPN :p");
        logo.setFont(new Font("Arial", Font.BOLD, 24));
        logo.setForeground(new Color(255, 204, 0));
        logo.setBorder(new EmptyBorder(15, 30, 15, 0));
        headerPanel.add(logo, BorderLayout.WEST);

        JPanel topNavPanel = new JPanel();
        topNavPanel.setBackground(Color.BLACK);
        topNavPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 20, 0));

        String[] topNavItems = {"CARTELERA"};
        for (String item : topNavItems) {
            JButton navButton = new JButton(item);
            navButton.setForeground(Color.WHITE);
            navButton.setBackground(Color.BLACK);
            navButton.setBorderPainted(false);
            navButton.setFocusPainted(false);
            navButton.setFont(new Font("Arial", Font.PLAIN, 14));
            navButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            if (item.equals("Cartelera")) {
                navButton.setForeground(new Color(255, 204, 0));
                navButton.setFont(navButton.getFont().deriveFont(Font.BOLD));
            }
            topNavPanel.add(navButton);
        }

        JButton ingresarButton = new JButton("Ingresar");
        ingresarButton.setBackground(new Color(255, 204, 0));
        ingresarButton.setForeground(new Color(26, 26, 26));
        ingresarButton.setFont(new Font("Arial", Font.BOLD, 14));
        ingresarButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        ingresarButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        topNavPanel.add(ingresarButton);

        headerPanel.add(topNavPanel, BorderLayout.CENTER);
        return headerPanel;
    }

    private JPanel createSecondaryNavPanel() {
        // ... (resto del código sin cambios) ...
        JPanel secondaryNavPanel = new JPanel();
        secondaryNavPanel.setBackground(new Color(34, 34, 34));
        secondaryNavPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10));

        String[] secondaryNavItems = {"CARTELERA"};
        for (String item : secondaryNavItems) {
            JLabel navLabel = new JLabel(item);
            navLabel.setForeground(new Color(170, 170, 170));
            navLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            if (item.equals("Cartelera")) {
                navLabel.setForeground(new Color(240, 240, 240));
                navLabel.setFont(navLabel.getFont().deriveFont(Font.BOLD));
                Border redBorder = BorderFactory.createMatteBorder(0, 0, 3, 0, Color.RED);
                navLabel.setBorder(BorderFactory.createCompoundBorder(
                        new EmptyBorder(0,0,5,0),
                        redBorder));
            }
            secondaryNavPanel.add(navLabel);
        }
        return secondaryNavPanel;
    }

    /**
     * Muestra las películas en el panel de la cartelera.
     * Crea una "tarjeta" visual para cada película y la agrega a la cuadrícula.
     * @param peliculas La lista de objetos Pelicula a mostrar.
     */
    public void displayPeliculas(List<Pelicula> peliculas) {
        SwingUtilities.invokeLater(() -> {
            movieGridPanel.removeAll();
            for (Pelicula p : peliculas) {
                JPanel movieCard = createMovieCardPanel(p);
                movieGridPanel.add(movieCard);
            }
            movieGridPanel.revalidate();
            movieGridPanel.repaint();
        });
    }

    private JPanel createMovieCardPanel(Pelicula pelicula) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(51, 51, 51));
        card.setPreferredSize(new Dimension(220, 420));
        card.setMaximumSize(new Dimension(220, 420));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        Border originalBorder;
        if ("Pre_venta".equals(pelicula.getEstado()) || "Estreno".equals(pelicula.getEstado())) {
            originalBorder = BorderFactory.createLineBorder(Color.RED, 2);
        } else {
            originalBorder = BorderFactory.createLineBorder(new Color(85, 85, 85), 2);
        }
        card.setBorder(BorderFactory.createCompoundBorder(originalBorder, new EmptyBorder(5, 5, 5, 5)));

        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (listener != null) {
                    listener.onPeliculaSelected(pelicula);
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(255, 204, 0), 2),
                        new EmptyBorder(5, 5, 5, 5)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(originalBorder, new EmptyBorder(5, 5, 5, 5)));
            }
        });

        JLabel posterLabel = new JLabel();
        posterLabel.setPreferredSize(new Dimension(200, 300));
        posterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadImage(pelicula.getImagen(), posterLabel);
        card.add(posterLabel);

        JLabel titleLabel = new JLabel(pelicula.getTitulo());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(titleLabel);

        JLabel statusLabel = new JLabel(pelicula.getEstado());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(170, 170, 170));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(statusLabel);

        card.add(Box.createVerticalGlue());

        return card;
    }

    private void loadImage(String imageName, JLabel label) {
        ImageIcon originalIcon = null;
        try {
            originalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/" + imageName)));
        } catch (Exception e) {
            BufferedImage placeholderImage = new BufferedImage(
                    label.getPreferredSize().width,
                    label.getPreferredSize().height,
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
                label.getPreferredSize().width,
                label.getPreferredSize().height,
                Image.SCALE_SMOOTH
        );
        label.setIcon(new ImageIcon(scaledImage));
    }
}