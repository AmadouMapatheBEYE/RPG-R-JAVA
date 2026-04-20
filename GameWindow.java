import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameWindow {

    // Les éléments de la fenêtre
    private JFrame fenetre;
    private JTextArea zoneDialogue;
    private JButton bouton1;
    private JButton bouton2;
    private JButton bouton3;
    private JLabel labelAffection;

    // Les données du jeu
    private int affection = 0;
    private String nomJoueur = "Joueur";

    // Constructeur : appelé quand on fait "new GameWindow()"
    public GameWindow() {
        construireFenetre();
    }

    private void construireFenetre() {

        // === LA FENÊTRE PRINCIPALE ===
        fenetre = new JFrame("Love Story 💕");
        fenetre.setSize(600, 500);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLayout(new BorderLayout());
        fenetre.getContentPane().setBackground(new Color(30, 20, 40));

        // === ZONE DE DIALOGUE (en haut) ===
        zoneDialogue = new JTextArea();
        zoneDialogue.setEditable(false);          // le joueur ne peut pas taper dedans
        zoneDialogue.setLineWrap(true);           // retour à la ligne automatique
        zoneDialogue.setWrapStyleWord(true);
        zoneDialogue.setBackground(new Color(45, 30, 60));
        zoneDialogue.setForeground(new Color(255, 220, 240));
        zoneDialogue.setFont(new Font("Serif", Font.PLAIN, 16));
        zoneDialogue.setMargin(new Insets(20, 20, 20, 20));

        // On met la zone de dialogue dans un "scroll" (au cas où le texte est long)
        JScrollPane scroll = new JScrollPane(zoneDialogue);
        fenetre.add(scroll, BorderLayout.CENTER);

        // === BARRE D'AFFECTION (en haut) ===
        labelAffection = new JLabel("❤️  Affection : 0", SwingConstants.CENTER);
        labelAffection.setForeground(new Color(255, 150, 180));
        labelAffection.setFont(new Font("Serif", Font.BOLD, 14));
        labelAffection.setOpaque(true);
        labelAffection.setBackground(new Color(30, 20, 40));
        labelAffection.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        fenetre.add(labelAffection, BorderLayout.NORTH);

        // === ZONE DES BOUTONS (en bas) ===
        JPanel panneauBoutons = new JPanel();
        panneauBoutons.setLayout(new GridLayout(3, 1, 5, 5));  // 3 lignes, 1 colonne
        panneauBoutons.setBackground(new Color(30, 20, 40));
        panneauBoutons.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        bouton1 = creerBouton("...");
        bouton2 = creerBouton("...");
        bouton3 = creerBouton("...");

        panneauBoutons.add(bouton1);
        panneauBoutons.add(bouton2);
        panneauBoutons.add(bouton3);

        fenetre.add(panneauBoutons, BorderLayout.SOUTH);
    }

    // Méthode utilitaire pour créer un bouton stylisé
    private JButton creerBouton(String texte) {
        JButton bouton = new JButton(texte);
        bouton.setBackground(new Color(100, 60, 120));
        bouton.setForeground(Color.WHITE);
        bouton.setFont(new Font("Serif", Font.PLAIN, 15));
        bouton.setFocusPainted(false);
        bouton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        bouton.setCursor(new Cursor(Cursor.HAND_CURSOR));  // curseur "main" au survol
        return bouton;
    }

    // Met à jour le texte de la zone de dialogue
    public void afficherTexte(String texte) {
        zoneDialogue.setText(texte);
    }

    // Met à jour le score d'affection affiché
    public void mettreAJourAffection() {
        labelAffection.setText("❤️  Affection : " + affection);
    }

    // Affiche la fenêtre
    public void afficher() {

        // Scène de démarrage
        afficherTexte("Bienvenue dans Love Story 💕\n\n"
                + "Tu rencontres Léa pour la première fois dans un café.\n"
                + "Elle lève les yeux vers toi et sourit.\n\n"
                + "\"Salut, je ne t'ai jamais vu ici avant...\"");

        bouton1.setText("1. Lui sourire chaleureusement");
        bouton2.setText("2. Répondre timidement");
        bouton3.setText("3. Regarder ailleurs");

        // Ce qui se passe quand on clique sur chaque bouton
        bouton1.addActionListener(e -> {
            affection += 10;
            afficherTexte("Tu lui offres ton plus beau sourire.\n\n"
                    + "Léa rougit légèrement.\n"
                    + "\"Oh... c'est sympa comme accueil !\"");
            mettreAJourAffection();
            afficherBoutonSuite();
        });

        bouton2.addActionListener(e -> {
            affection += 5;
            afficherTexte("Tu bafouilles un peu en répondant.\n\n"
                    + "Léa trouve ça adorable et rigole doucement.\n"
                    + "\"T'inquiète, je mords pas !\"");
            mettreAJourAffection();
            afficherBoutonSuite();
        });

        bouton3.addActionListener(e -> {
            affection -= 5;
            afficherTexte("Tu détournes le regard.\n\n"
                    + "Léa hausse les sourcils, un peu surprise.\n"
                    + "\"...Ok. Très bien alors.\"");
            mettreAJourAffection();
            afficherBoutonSuite();
        });

        fenetre.setLocationRelativeTo(null);  // centre la fenêtre sur l'écran
        fenetre.setVisible(true);             // rend la fenêtre visible
    }

    // Remplace les 3 boutons par un seul bouton "Continuer"
    private void afficherBoutonSuite() {
        bouton1.setText("Continuer →");
        bouton2.setVisible(false);
        bouton3.setVisible(false);

        // On supprime les anciens clics et on met un nouveau
        for (ActionListener al : bouton1.getActionListeners()) {
            bouton1.removeActionListener(al);
        }

        bouton1.addActionListener(e -> {
            afficherTexte("La suite arrive bientôt...\n\n"
                    + "Score final d'affection : " + affection);
        });
    }
}