import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.*;
import javax.sound.sampled.*;
import javax.swing.*;

public class GameWindow {

    // Interface
    private JFrame fenetre;
    private JTextArea zoneDialogue;
    private JButton bouton1, bouton2, bouton3;
    private JLabel labelAffection, labelRelation;
    private JLabel labelImage;
    private JPanel panneauPrincipal;
    private JLabel labelScene;   // NOUVEAU : indique "Scène X / Y"

    // Données du jeu
    private Personnage lea;
    private Scene[] scenes;
    private int sceneActuelle = 0;

    // Musique
    private Clip clipMusique;

    // Animation de texte
    private Timer timerTexte;
    private String texteComplet = "";
    private int indexLettre = 0;

    public GameWindow() {
        lea = new Personnage("Léa",
            "Une jeune femme douce et passionnée de littérature.");
        creerScenes();
        construireFenetre();
    }

    private void creerScenes() {
        scenes = new Scene[7];   // 6 scènes + 1 fin

        // SCÈNE 0 — La rencontre
        scenes[0] = new Scene(
            "☕ Le Café des Artistes — Mardi matin\n\n"
            + "Tu pousses la porte du café quand tu remarques Léa,\n"
            + "assise seule avec un livre. Elle lève les yeux vers toi.\n\n"
            + "\"Salut... tu cherches une place ?\"",
            new String[]{
                "\"Oui, je peux m'asseoir près de toi ?\"",
                "\"Euh... non merci, je vais bien là.\"",
                "Lui sourire sans rien dire"
            },
            new int[]{ 15, -5, 10 },
            new Color(45, 30, 60),
            "lea_neutre.JPEG"
        );

        // SCÈNE 1 — Les livres
        scenes[1] = new Scene(
            "📚 Léa pose son livre sur la table.\n\n"
            + "\"Tu lis beaucoup toi aussi ?\"\n\n"
            + "Elle montre la couverture — c'est un roman que tu connais bien.",
            new String[]{
                "\"J'adore ce livre ! Tu en es où ?\"",
                "\"Pas vraiment, mais ça a l'air bien.\"",
                "\"Les livres c'est pas trop mon truc.\""
            },
            new int[]{ 20, 5, -10 },
            new Color(30, 45, 60),
            "lea_heureuse.JPEG"
        );

        // SCÈNE 2 — Le café se vide
        scenes[2] = new Scene(
            "🕯️ Le café se vide peu à peu.\n\n"
            + "Vous avez parlé pendant deux heures sans voir le temps passer.\n"
            + "Léa regarde autour d'elle, surprise.\n\n"
            + "\"Je ne savais pas qu'on pouvait autant discuter avec un inconnu...\"",
            new String[]{
                "\"On est plus vraiment des inconnus maintenant.\"",
                "\"Le temps passe vite quand on est bien.\"",
                "Sourire timidement sans répondre"
            },
            new int[]{ 15, 15, 10 },
            new Color(40, 25, 55),
            "lea_heureuse.JPEG"
        );

        // SCÈNE 3 — La proposition
        scenes[3] = new Scene(
            "🌆 Le soleil commence à baisser.\n\n"
            + "Léa range ses affaires et s'arrête avant de partir.\n\n"
            + "\"C'était vraiment sympa... T'as prévu quelque chose ce soir ?\"",
            new String[]{
                "\"Non, rien ! Tu as une idée ?\"",
                "\"Je suis libre mais... on se revoit quand ?\"",
                "\"Malheureusement oui, dommage...\""
            },
            new int[]{ 20, 10, -5 },
            new Color(60, 35, 20),
            "lea_heureuse.JPEG"
        );

        // SCÈNE 4 — La promenade
        scenes[4] = new Scene(
            "🌙 En ville, le soir.\n\n"
            + "Vous marchez le long des quais. Les lumières se reflètent\n"
            + "sur l'eau. Léa s'arrête et te regarde.\n\n"
            + "\"Je suis contente qu'on ait fait ça.\"",
            new String[]{
                "\"Moi aussi. J'espère que c'est pas la dernière fois.\"",
                "Prendre doucement sa main",
                "\"T'es quelqu'un de vraiment bien, Léa.\""
            },
            new int[]{ 20, 25, 15 },
            new Color(15, 20, 50),
            "lea_heureuse.JPEG"
        );

        // SCÈNE 5 — L'au revoir
        scenes[5] = new Scene(
            "🚪 Devant chez elle.\n\n"
            + "Léa s'arrête sur le pas de la porte et se retourne.\n"
            + "Il y a quelque chose dans ses yeux...\n\n"
            + "\"Bonne nuit...\"",
            new String[]{
                "\"Bonne nuit Léa. À très bientôt.\"",
                "\"Je peux te revoir demain ?\"",
                "L'embrasser doucement sur la joue"
            },
            new int[]{ 10, 20, 30 },
            new Color(20, 15, 40),
            "lea_heureuse.JPEG"
        );

        // SCÈNE 6 — FIN
        scenes[6] = new Scene(
            "FIN",
            new String[]{},
            new int[]{},
            new Color(10, 5, 20),
            "lea_neutre.JPEG"
        );
    }

    private void construireFenetre() {

        fenetre = new JFrame("Love Story 💕");
        fenetre.setSize(700, 620);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLayout(new BorderLayout(0, 0));

        // === PANNEAU DU HAUT ===
        JPanel panneauHaut = new JPanel(new GridLayout(3, 1));
        panneauHaut.setBackground(new Color(20, 10, 30));
        panneauHaut.setBorder(
            BorderFactory.createEmptyBorder(8, 20, 8, 20));

        labelScene = new JLabel("Scène 1 / 6", SwingConstants.CENTER);
        labelScene.setForeground(new Color(150, 120, 170));
        labelScene.setFont(new Font("Serif", Font.PLAIN, 12));

        labelAffection = new JLabel("❤️  Affection : 0 / 100",
            SwingConstants.CENTER);
        labelAffection.setForeground(new Color(255, 150, 180));
        labelAffection.setFont(new Font("Serif", Font.BOLD, 14));

        labelRelation = new JLabel("😐 Inconnus", SwingConstants.CENTER);
        labelRelation.setForeground(new Color(200, 200, 255));
        labelRelation.setFont(new Font("Serif", Font.ITALIC, 13));

        panneauHaut.add(labelScene);
        panneauHaut.add(labelAffection);
        panneauHaut.add(labelRelation);
        fenetre.add(panneauHaut, BorderLayout.NORTH);

        // === PANNEAU CENTRAL ===
        panneauPrincipal = new JPanel(new BorderLayout(10, 0));
        panneauPrincipal.setBackground(new Color(45, 30, 60));
        panneauPrincipal.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Portrait
        labelImage = new JLabel();
        labelImage.setPreferredSize(new Dimension(180, 300));
        labelImage.setHorizontalAlignment(SwingConstants.CENTER);
        labelImage.setVerticalAlignment(SwingConstants.CENTER);
        labelImage.setOpaque(true);
        labelImage.setBackground(new Color(60, 40, 80));
        panneauPrincipal.add(labelImage, BorderLayout.WEST);

        // Zone de dialogue
        zoneDialogue = new JTextArea();
        zoneDialogue.setEditable(false);
        zoneDialogue.setLineWrap(true);
        zoneDialogue.setWrapStyleWord(true);
        zoneDialogue.setBackground(new Color(35, 20, 50));
        zoneDialogue.setForeground(new Color(255, 220, 240));
        zoneDialogue.setFont(new Font("Serif", Font.PLAIN, 16));
        zoneDialogue.setMargin(new Insets(20, 20, 20, 20));

        JScrollPane scroll = new JScrollPane(zoneDialogue);
        panneauPrincipal.add(scroll, BorderLayout.CENTER);
        fenetre.add(panneauPrincipal, BorderLayout.CENTER);

        // === BOUTONS ===
        JPanel panneauBoutons = new JPanel(new GridLayout(3, 1, 5, 5));
        panneauBoutons.setBackground(new Color(20, 10, 30));
        panneauBoutons.setBorder(
            BorderFactory.createEmptyBorder(10, 20, 20, 20));

        bouton1 = creerBouton("");
        bouton2 = creerBouton("");
        bouton3 = creerBouton("");

        panneauBoutons.add(bouton1);
        panneauBoutons.add(bouton2);
        panneauBoutons.add(bouton3);
        fenetre.add(panneauBoutons, BorderLayout.SOUTH);
    }

    private JButton creerBouton(String texte) {
        JButton b = new JButton(texte);
        b.setBackground(new Color(100, 60, 120));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Serif", Font.PLAIN, 14));
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                b.setBackground(new Color(140, 90, 160));
            }
            public void mouseExited(MouseEvent e) {
                b.setBackground(new Color(100, 60, 120));
            }
        });
        return b;
    }

    // === ANIMATION DE TEXTE (effet machine à écrire) ===
    private void animerTexte(String texte) {
        // Arrêter l'animation précédente si elle tourne encore
        if (timerTexte != null) timerTexte.cancel();

        texteComplet = texte;
        indexLettre = 0;
        zoneDialogue.setText("");

        timerTexte = new Timer();
        timerTexte.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (indexLettre < texteComplet.length()) {
                    // SwingUtilities : on modifie l'interface depuis le bon thread
                    SwingUtilities.invokeLater(() -> {
                        zoneDialogue.setText(
                            texteComplet.substring(0, indexLettre + 1));
                    });
                    indexLettre++;
                } else {
                    timerTexte.cancel();  // animation terminée
                }
            }
        }, 0, 25);  // une lettre toutes les 25ms
    }

    private void chargerImage(String nomFichier) {
        try {
            File f = new File("assets/images/" + nomFichier);
            if (f.exists()) {
                Image img = ImageIO.read(f)
                    .getScaledInstance(170, 290, Image.SCALE_SMOOTH);
                labelImage.setIcon(new ImageIcon(img));
                labelImage.setText("");
            } else {
                labelImage.setIcon(null);
                labelImage.setText("Léa");
                labelImage.setForeground(new Color(255, 200, 220));
                labelImage.setFont(new Font("Serif", Font.BOLD, 24));
            }
        } catch (IOException e) {
            labelImage.setText("Léa");
        }
    }

    private void lancerMusique(String nomFichier) {
        try {
            if (clipMusique != null && clipMusique.isRunning()) {
                clipMusique.stop();
                clipMusique.close();
            }
            File f = new File("assets/musique/" + nomFichier);
            if (!f.exists()) return;
            AudioInputStream audio = AudioSystem.getAudioInputStream(f);
            clipMusique = AudioSystem.getClip();
            clipMusique.open(audio);
            clipMusique.loop(Clip.LOOP_CONTINUOUSLY);
            clipMusique.start();
        } catch (Exception e) {
            System.out.println("Musique non chargée : " + e.getMessage());
        }
    }

    private void chargerScene(int index) {
        sceneActuelle = index;
        Scene scene = scenes[index];

        if (scene.getTexte().equals("FIN")) {
            afficherFin();
            return;
        }

        // Mettre à jour le compteur de scène
        labelScene.setText("Scène " + (index + 1) + " / "
            + (scenes.length - 1));

        // Couleur et image
        panneauPrincipal.setBackground(scene.getCouleurFond());
        zoneDialogue.setBackground(scene.getCouleurFond().darker());
        chargerImage(scene.getImagePersonnage());

        // Texte animé
        animerTexte(scene.getTexte());

        // Réinitialiser les boutons
        bouton1.setVisible(true);
        bouton2.setVisible(true);
        bouton3.setVisible(true);

        for (ActionListener al : bouton1.getActionListeners())
            bouton1.removeActionListener(al);
        for (ActionListener al : bouton2.getActionListeners())
            bouton2.removeActionListener(al);
        for (ActionListener al : bouton3.getActionListeners())
            bouton3.removeActionListener(al);

        String[] choix = scene.getChoix();
        bouton1.setText(choix.length > 0 ? choix[0] : "");
        bouton2.setText(choix.length > 1 ? choix[1] : "");
        bouton3.setText(choix.length > 2 ? choix[2] : "");

        bouton1.addActionListener(e -> faireChoix(0));
        bouton2.addActionListener(e -> faireChoix(1));
        bouton3.addActionListener(e -> faireChoix(2));
    }

    private void faireChoix(int indexChoix) {
        // Arrêter l'animation si le joueur clique avant la fin
        if (timerTexte != null) timerTexte.cancel();
        zoneDialogue.setText(texteComplet);

        lea.modifierAffection(scenes[sceneActuelle].getEffet(indexChoix));
        mettreAJourAffection();
        chargerScene(sceneActuelle + 1);
    }

    private void mettreAJourAffection() {
        labelAffection.setText("❤️  Affection : "
            + lea.getAffection() + " / 100");
        labelRelation.setText(lea.getEtatRelation());
    }

    private void afficherFin() {
        if (clipMusique != null) clipMusique.stop();

        bouton1.setVisible(false);
        bouton2.setVisible(false);
        bouton3.setVisible(false);

        labelScene.setText("✨ Fin de l'histoire");

        int score = lea.getAffection();
        String texteFin;

        if (score >= 70) {
            texteFin = "🌸 FIN HEUREUSE 🌸\n\n"
                + "Quelques semaines plus tard...\n\n"
                + "Léa et toi êtes inséparables. Ce café est devenu\n"
                + "\"votre\" endroit. Elle pose sa tête sur ton épaule\n"
                + "et murmure doucement :\n\n"
                + "\"Je suis tellement contente qu'on se soit rencontrés.\"\n\n"
                + "────────────────────\n"
                + "Score final : " + score + " / 100\n"
                + lea.getEtatRelation();
            chargerImage("lea_heureuse.JPEG");

        } else if (score >= 40) {
            texteFin = "🌤️ FIN AMITIÉ 🌤️\n\n"
                + "Quelques semaines plus tard...\n\n"
                + "Léa et toi vous revoyez régulièrement. Une belle\n"
                + "amitié s'est installée, solide et sincère.\n"
                + "Qui sait ce que l'avenir réserve ?\n\n"
                + "────────────────────\n"
                + "Score final : " + score + " / 100\n"
                + lea.getEtatRelation();
            chargerImage("lea_neutre.JPEG");

        } else {
            texteFin = "🌧️ FIN MANQUÉE 🌧️\n\n"
                + "Quelques semaines plus tard...\n\n"
                + "Tu repenses à Léa de temps en temps.\n"
                + "Peut-être que si tu avais été plus ouvert(e)...\n"
                + "Mais la vie continue, et d'autres rencontres t'attendent.\n\n"
                + "────────────────────\n"
                + "Score final : " + score + " / 100\n"
                + lea.getEtatRelation();
            chargerImage("lea_triste.JPEG");
        }

        animerTexte(texteFin);

        // Bouton Rejouer
        bouton1.setText("🔄  Rejouer");
        bouton1.setVisible(true);
        bouton1.addActionListener(e -> {
            fenetre.dispose();
            EcranTitre ecran = new EcranTitre();
            ecran.afficher();
        });
    }

    public void afficher() {
        lancerMusique("theme.wav");
        chargerScene(0);
        fenetre.setLocationRelativeTo(null);
        fenetre.setVisible(true);
    }
}