package src.metier;

public class CarteVehicule {
    private Type type;
    private int nbCarte;
    
    public CarteVehicule(Type type) {
        this.nbCarte = 12;
        this.type = type;
    }

    public int getNbCarte() {
        return this.nbCarte;
    }

    public Type getType() {
        return this.type;
    }
}



