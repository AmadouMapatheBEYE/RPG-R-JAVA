import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EcranTitre {

    private JFrame fenetre;

    public EcranTitre() {
        construire();
    }

    private void construire() {

        fenetre = new JFrame("Love Story 💕");
        fenetre.setSize(700, 600);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLayout(new BorderLayout());

        // === PANNEAU PRINCIPAL avec dégradé ===
        JPanel panneau = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Dessine un dégradé du haut vers le bas
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint degrade = new GradientPaint(
                    0, 0,               new Color(20, 10, 40),   // haut
                    0, getHeight(),     new Color(80, 20, 60)    // bas
                );
                g2d.setPaint(degrade);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panneau.setLayout(new GridBagLayout());  // centre tout automatiquement

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(15, 0, 15, 0);  // espacement vertical

        // === TITRE ===
        JLabel titre = new JLabel("Love Story");
        titre.setFont(new Font("Serif", Font.BOLD, 60));
        titre.setForeground(new Color(255, 180, 210));

        // === SOUS-TITRE ===
        JLabel sousTitre = new JLabel("✨ Un roman graphique interactif ✨");
        sousTitre.setFont(new Font("Serif", Font.ITALIC, 18));
        sousTitre.setForeground(new Color(200, 160, 220));

        // === COEUR DÉCORATIF ===
        JLabel coeur = new JLabel("💕");
        coeur.setFont(new Font("Serif", Font.PLAIN, 50));

        // === BOUTON JOUER ===
        JButton boutonJouer = creerBouton("▶  Nouvelle partie", 
            new Color(150, 60, 120), 200, 55);

        // === BOUTON QUITTER ===
        JButton boutonQuitter = creerBouton("✕  Quitter",
            new Color(80, 40, 80), 200, 45);

        // === VERSION ===
        JLabel version = new JLabel("v1.0  —  Fait avec Java 💜");
        version.setFont(new Font("Serif", Font.PLAIN, 12));
        version.setForeground(new Color(150, 120, 160));

        // Ajouter tous les éléments au panneau
        gbc.gridy = 0; panneau.add(coeur, gbc);
        gbc.gridy = 1; panneau.add(titre, gbc);
        gbc.gridy = 2; panneau.add(sousTitre, gbc);
        gbc.gridy = 3; panneau.add(boutonJouer, gbc);
        gbc.gridy = 4; panneau.add(boutonQuitter, gbc);
        gbc.gridy = 5; panneau.add(version, gbc);

        fenetre.add(panneau);

        // === ACTIONS ===
        boutonJouer.addActionListener(e -> {
            fenetre.dispose();              // ferme l'écran titre
            GameWindow jeu = new GameWindow();
            jeu.afficher();                 // ouvre le jeu
        });

        boutonQuitter.addActionListener(e -> System.exit(0));
    }

    private JButton creerBouton(String texte, Color couleur, 
                                 int largeur, int hauteur) {
        JButton b = new JButton(texte);
        b.setPreferredSize(new Dimension(largeur, hauteur));
        b.setBackground(couleur);
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Serif", Font.BOLD, 16));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Effet hover
        Color hover = couleur.brighter();
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(hover);
            }
            public void mouseExited(MouseEvent e) {
                b.setBackground(couleur);
            }
        });

        return b;
    }

    public void afficher() {
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
    }
}