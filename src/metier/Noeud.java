package src.metier;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Noeud extends Ellipse2D {
    private String nom;
    private int x;
    private int y;
    private int NomX;
    private int NomY;

    public Noeud(String nom, int x, int y, int NomX, int NomY) {
        this.nom = nom;
        this.x = x;
        this.y = y;
        this.NomX = NomX;
        this.NomY = NomY;
    }

    public String getNom() {
        return this.nom;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public int getNomX() {
        return this.NomX;
    }

    public int getNomY() {
        return this.NomY;
    }

    public boolean setNom(String nom) {
        if ( nom != null && nom.length() > 0) {
            this.nom = nom;
            return true;
        }
        return false;
    }

    public boolean setX(int x) {
        if ( x >= 0 && x <= 1200) {
            this.x = x;
            return true;
        }
        return false;
    }

    public boolean setY(int y) {
        if ( y >= 0 && y <= 1200) {
            this.y = y;
            return true;
        }
        return false;
    }

    public boolean setNomX(int NomX) {
        if ( NomX >= 0 && NomX <= 1200) {
            this.NomX = NomX;
            return true;
        }
        return false;
    }

    public boolean setNomY(int NomY) {
        if ( NomY >= 0 && NomY <= 1200) {
            this.NomY = NomY;
            return true;
        }
        return false;
    }

    public String toString(){
        return this.nom + ";" + this.x + ";" + this.y;
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void setFrame(double arg0, double arg1, double arg2, double arg3) {
    }
}