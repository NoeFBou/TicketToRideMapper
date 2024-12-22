package src;
import com.formdev.flatlaf.FlatLightLaf;

import src.ihm.*;
import src.metier.*;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;

public class Controleur{

    private BufferedImage imagePlateau;

    private GererXML gererXML;

    private FramePrincipale IHM;

    public Controleur() {
        this.gererXML       = new GererXML(this);
        this.IHM            = new FramePrincipale(this);
    }

    //geter et seter du fichier de la carte
    public BufferedImage getImagePlateau() {
        return this.imagePlateau;
    }

    public void setImagePlateau(BufferedImage image) {
        this.imagePlateau = image;
        this.IHM.majIHM();
    }

    //permet au Controleur de lire le fichier xml
    public void lireXML(File fichier)
    {
        this.gererXML.lireXML(fichier);
    }

    //supprime toute les arrêtes
    public void supprimerAretes()
    {
        this.gererXML.supprimerLstAretes();
    }

    //supprime tout les noeuds
    public void supprimerNoeuds()
    {
        this.gererXML.supprimerLstNoeuds();
    }

    //supprime tout les objectifs
    public void supprimerCartesObjectif()
    {
        this.gererXML.supprimerLstCarteObjectifs();
    }

    //retourne la liste des types de cartes
    public ArrayList<Color> getTypes() {
        return Type.getCouleurs();
    }


    public void majIHM() {
        this.IHM.majIHM();
    }

    public FramePrincipale getIHM() {
        return this.IHM;
    }

    public GererXML getMetier() {
        return this.gererXML;
    }

    /*****************/
    /*     Noeud     */
    /*****************/

    public void ajouterNoeud(String nom, int x, int y, int nomX, int nomY) {
        //ajoute le noeud a l'arraylist
        this.gererXML.ajouterNoeud(nom, x, y, nomX, nomY);
        //majNoeud
        this.majIHM();
        this.majNoeud();
    }

    public void supprimerNoeud(Noeud n) {
        this.gererXML.supprimerNoeud(n);
        this.majIHM();
        this.majNoeud();
    }

    public void setPositionNoeud(Noeud n, int x, int y, int nomX, int nomY)
    {
        this.gererXML.setPositionNoeud(n,x,y,nomX,nomY);
        this.majNoeud();
    }

    public void majNoeud() {
        this.IHM.majNoeud();
    }

    /*****************/
    /*     Arete     */
    /*****************/

    public void ajouterArete(Noeud n1, Noeud n2, int n, Color c) {
        this.gererXML.ajouterArete(n1,n2,n,c);
        this.majIHM();
        this.majArete();
    }

    public void supprimerArete(Arete a) {
        this.gererXML.supprimerArete(a);
        this.majIHM();
        this.majArete();
    }

    public void majArete()
    {
        this.IHM.majArete();
    }

    public ArrayList<Type> getLstType(){
        return this.gererXML.getLstType();
    }

    /*****************/
    /*    CarteObj   */
    /*****************/

    public void ajouterCarteObjectif(Noeud n1, Noeud n2, int points){
        this.gererXML.ajouterCarteObjectif(n1, n2, points);
        this.majIHM();
        this.majCarteObjectif();
    }

    public void supprimerCarteObjectif(CarteObjectif cObjectif){
        this.gererXML.supprimerCarteObjectif(cObjectif);
        this.majIHM();
        this.majCarteObjectif();
    }

    public void majCarteObjectif()
    {
        this.IHM.majCarteObjectif();
    }
    /*****************/
    /*    Resume     */
    /*****************/

    public void majLabelResume(int nbJoueurMin, int nbJoueurMax, int doubeVoie, int nbWagon, int nbWagonFin, int nbPointCheminLong){
        this.IHM.majLabelResume(nbJoueurMin, nbJoueurMax, doubeVoie, nbWagon, nbWagonFin, nbPointCheminLong);
    }

    public void majTxtRegleJeu(int nbJoueurMin, int nbJoueurMax, int doubeVoie, int nbWagon, int nbWagonFin, int nbPointCheminLong){
        this.IHM.majTxtRegleJeu(nbJoueurMin, nbJoueurMax, doubeVoie, nbWagon, nbWagonFin, nbPointCheminLong);
    }

    public void majCouleurJoker(Color couleurJoker){
        this.gererXML.setCouleurJoker(couleurJoker);
        this.IHM.majCouleurJoker(couleurJoker);
    }

    public void supprimerResume(){
        this.IHM.suppimerResume();
    }

    public void supprimerRegleJeu(){
        this.IHM.supprimerRegleJeu();
    }


    /*****************/
    /*    Ecrire     */
    /*****************/

    public void ecrireXML(String path){

        this.gererXML.ecrireXML(this.imagePlateau, path);
    }
    /*****************/
    /* CarteVehicule */
    /*****************/

    public ArrayList<Noeud> getLstNoeuds() {
        return this.gererXML.getLstNoeuds();
    }

    public ArrayList<Arete> getLstAretes() {
        return this.gererXML.getLstAretes();
    }

    public ArrayList<CarteObjectif> getLstObjectifs() {
        return this.gererXML.getLstCarteObjectifs();
    }

    public ArrayList<String> getNomNoeuds() {
        ArrayList<Noeud> noeuds = this.gererXML.getLstNoeuds();
        ArrayList<String> nomNoeuds = new ArrayList<String>();
        for (Noeud n : noeuds) {
            nomNoeuds.add(n.getNom());
        };

        return nomNoeuds;
    }

    public ArrayList<CarteVehicule> getCarteVehicule() {
        ArrayList<CarteVehicule> lstCarteVehicule = new ArrayList<CarteVehicule>();
        for ( Type type : this.gererXML.getLstType()) {
            lstCarteVehicule.add(new CarteVehicule(type));
        }
        return lstCarteVehicule;
    }


    /*****************/
    /*      Main     */
    /*****************/

    public static void main(String[] args) {
        FlatLightLaf.setup();
        Controleur controleur = new Controleur();
    }

}