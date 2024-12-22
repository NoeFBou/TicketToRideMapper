package src.metier;

import java.awt.*;
import java.util.ArrayList;

public class Type {
    private static ArrayList<Color> listeCouleurs = new ArrayList<Color>();
	private Color couleurActuelle;

	private Type(Color couleur){
		this.couleurActuelle = ajouterCouleur(couleur);
	}

	private static Color ajouterCouleur(Color c) {
        for( Color coul : listeCouleurs) {
			if( coul == c ) {
				return coul;
			}
		}
		listeCouleurs.add(c);
        return c;
    }

	public static Type creerType (Color couleur) {
        return new Type(Type.ajouterCouleur(couleur));
	}

	public Color getColor(){
		return this.couleurActuelle;
	}

	public static ArrayList<Color> getCouleurs(){
		return Type.listeCouleurs;
	}

	public Color getCouleurActuelle(){
		return this.couleurActuelle;
	}

	public String toString(){
		return String.valueOf(this.couleurActuelle.getRGB());
	}
}