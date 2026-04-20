import java.awt.Color;

public class Scene {

    private String texte;
    private String[] choix;
    private int[] effetsAffection;
    private Color couleurFond;       // NOUVEAU
    private String imagePersonnage;  // NOUVEAU : nom du fichier image

    public Scene(String texte, String[] choix, int[] effetsAffection,
                 Color couleurFond, String imagePersonnage) {
        this.texte = texte;
        this.choix = choix;
        this.effetsAffection = effetsAffection;
        this.couleurFond = couleurFond;
        this.imagePersonnage = imagePersonnage;
    }

    public String getTexte() { return texte; }
    public String[] getChoix() { return choix; }
    public int[] getEffetsAffection() { return effetsAffection; }
    public Color getCouleurFond() { return couleurFond; }
    public String getImagePersonnage() { return imagePersonnage; }
    public int getNombreChoix() { return choix.length; }

    public int getEffet(int indexChoix) {
        if (indexChoix < effetsAffection.length) {
            return effetsAffection[indexChoix];
        }
        return 0;
    }
}