public class Personnage {

    private String nom;
    private String description;
    private int affection;
    private int affectionMax = 100;

    // Constructeur
    public Personnage(String nom, String description) {
        this.nom = nom;
        this.description = description;
        this.affection = 0;
    }

    // Ajouter ou retirer de l'affection
    public void modifierAffection(int valeur) {
        affection += valeur;

        // On s'assure que ça reste entre 0 et 100
        if (affection > affectionMax) affection = affectionMax;
        if (affection < 0) affection = 0;
    }

    // Retourne l'état de la relation selon le score
    public String getEtatRelation() {
        if (affection >= 80) return "💖 Amour profond";
        if (affection >= 60) return "💕 Très proche";
        if (affection >= 40) return "😊 Bonne entente";
        if (affection >= 20) return "🙂 Sympathie";
        return "😐 Inconnus";
    }

    // Les "getters" : méthodes pour lire les valeurs privées
    public String getNom() { return nom; }
    public String getDescription() { return description; }
    public int getAffection() { return affection; }
}