package src.metier;

import java.util.ArrayList;

public class CarteObjectif {
    private static ArrayList<CarteObjectif> pioche = new ArrayList<>();

    private Noeud noeud1;
    private Noeud noeud2;
    private int   points;

    public CarteObjectif(Noeud noeud1, Noeud noeud2, int points) {
        this.noeud1 = noeud1;
        this.noeud2 = noeud2;
        this.points = points;
    }

    public Noeud getNoeud1() {
        return this.noeud1;
    }

    public Noeud getNoeud2() {
        return this.noeud2;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean setPoints(int points) {
        if (points < 0)
            return false;
        this.points = points;
        return true;
    }
}
