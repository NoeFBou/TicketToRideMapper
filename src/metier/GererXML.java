package src.metier;

import org.jdom2.*;
import org.jdom2.input.*;
import src.Controleur;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;

public class GererXML {

	private Controleur       ctrl;
	private int              diametre;
	private int  nbrJoueurMinimum;
	private int  nbrJoueurMaximum;
	private int  nbrJoueurMiniDoubleRoute;
	private int  nbVehiculeJoueur;
	private int  nbVehiculeFinPartie;
	private int  nbPointCheminLong; //si < 0 (=-1) alors il n y a pas la regle du chemin le plus long
    private int  longueurVehicule;
    private int  hauteurVehicule;
	private int nombreJoker;
	private Color couleurJoker;
    private double espacementVehicule;
	private ArrayList<Noeud> lstNoeuds;
	private ArrayList<Arete> lstAretes;
	private ArrayList<CarteObjectif> lstCarteObjectifs;
	private ArrayList<CarteVehicule> lstCarteVehicules;
	private HashMap<Type, ArrayList<CarteVehicule>> hashMapCarteVehicules;
	private HashMap<Integer, Color> hashVehicules;
     

	public GererXML(Controleur ctrl){

		this.nbrJoueurMaximum = 5;
		this.nbrJoueurMinimum = 2;
		this.nbrJoueurMiniDoubleRoute = 3;
		this.nbVehiculeJoueur = 45;
		this.nbVehiculeFinPartie = 2;
		this.nbPointCheminLong = -1;
        this.longueurVehicule = 25;
        this.hauteurVehicule = 10;
        this.espacementVehicule = 1.5;
		this.nombreJoker = 5;
		this.couleurJoker = Color.LIGHT_GRAY;

		this.ctrl = ctrl;

		this.diametre = 20;

		this.lstNoeuds = new ArrayList<Noeud>();
		this.hashMapCarteVehicules = new HashMap<Type, ArrayList<CarteVehicule>>();
		this.hashVehicules = new HashMap<Integer, Color>();

		Type marron = Type.creerType(Color.ORANGE);
		Type rouge = Type.creerType(Color.RED);

		ArrayList<CarteVehicule> lstCarteMarron = new ArrayList<CarteVehicule>();
		lstCarteMarron.add(new CarteVehicule(Type.creerType(Color.ORANGE)));
		lstCarteMarron.add(new CarteVehicule(Type.creerType(Color.ORANGE)));
		lstCarteMarron.add(new CarteVehicule(Type.creerType(Color.ORANGE)));
		//(12 de chaque type : violet, blanc, bleu, jaune, orange, noir, rouge et vert et 14 locomotives)
		this.hashVehicules.put(12, Color.PINK);
		this.hashVehicules.put(12, Color.WHITE);
		this.hashVehicules.put(12, Color.BLUE);
		this.hashVehicules.put(12, Color.YELLOW);
		this.hashVehicules.put(12, Color.ORANGE);
		this.hashVehicules.put(12, Color.BLACK);
		this.hashVehicules.put(12, Color.GREEN);
		this.hashVehicules.put(12, Color.RED);

		this.lstCarteVehicules = new ArrayList<CarteVehicule>();

		this.lstAretes = new ArrayList<Arete>();
		this.lstCarteObjectifs = new ArrayList<CarteObjectif>();
	}

	public int getDiametre(){
		return this.diametre;
	}

	public int setDiametre(int diametre){
		return this.diametre = diametre;
	}

	public int getNombreJoueurMinimum(){
		return this.nbrJoueurMinimum;
	}

	public int setNombreJoueurMinimum(int nbrJoueurMinimum){
		return this.nbrJoueurMinimum = nbrJoueurMinimum;
	}

	public int getNombreJoueurMaximum(){
		return this.nbrJoueurMaximum;
	}

	public int setNombreJoueurMaximum(int nbrJoueurMaximum){
		return this.nbrJoueurMaximum = nbrJoueurMaximum;
	}

	public int getNombreJoueurMiniDoubleRoute(){
		return this.nbrJoueurMiniDoubleRoute;
	}

	public int setNombreJoueurMiniDoubleRoute(int nbrJoueurMiniDoubleRoute){
		return this.nbrJoueurMiniDoubleRoute = nbrJoueurMiniDoubleRoute;
	}

	public int getNbVehiculeJoueur(){
		return this.nbVehiculeJoueur;
	}

	public int setNbVehiculeJoueur(int nbVehiculeJoueur){
		return this.nbVehiculeJoueur = nbVehiculeJoueur;
	}

	public int getNbVehiculeFinPartie(){
		return this.nbVehiculeFinPartie;
	}

	public int setNbVehiculeFinPartie(int nbVehiculeFinPartie){
		return this.nbVehiculeFinPartie = nbVehiculeFinPartie;
	}

	public int getNbPointCheminLong(){
		return this.nbPointCheminLong;
	}

	public int setNbPointCheminLong(int nbPointCheminLong){
		return this.nbPointCheminLong = nbPointCheminLong;
	}
    public int getLongueurVehicule(){
        return this.longueurVehicule;
    }

    public int setLongueurVehicule(int longueurVehicule){
        return this.longueurVehicule = longueurVehicule;
    }

    public int getHauteurVehicule(){
        return this.hauteurVehicule;
    }

    public int setHauteurVehicule(int hauteurVehicule){
        return this.hauteurVehicule = hauteurVehicule;
    }

    public double getEspacementVehicule(){
        return this.espacementVehicule;
    }

    public double setEspacementVehicule(double espacementVehicule){
        return this.espacementVehicule = espacementVehicule;
    }

	public int getNombreJoker(){
		return this.nombreJoker;
	}

	public int setNombreJoker(int nombreJoker){
		return this.nombreJoker = nombreJoker;
	}
    

	/*****************/
    /*     NOEUD     */
    /*****************/

	public void ajouterNoeud(String nom, int x, int y, int nomX, int nomY){
		this.lstNoeuds.add(new Noeud(nom, x, y, nomX, nomY));
	}

	public void supprimerNoeud (Noeud n) {
		this.lstNoeuds.remove(n);
	}

	public void setPositionNoeud(Noeud n, int x, int y, int nomX, int nomY)
	{	
		for (Noeud noeud : this.lstNoeuds)
		{
			if(noeud.equals(n))
			{	
				noeud.setX(x);
				noeud.setY(y);
				noeud.setNomX(nomX);
				noeud.setNomY(nomY);
				return;
			}
			this.ctrl.majNoeud();
		}
	}
	    
	/*****************/
    /* CarteVehicule */
    /*****************/

	public HashMap<Integer, Color> getHashVehicules() {
		return this.hashVehicules;
	}

	public ArrayList<Type> getLstType() {
		//parcourir la listArrte et recuperer les types des aretes
		ArrayList<Type> listeType = new ArrayList();
		boolean trouve;
		for (Arete arete : this.lstAretes)
		{
			trouve = false;

			for (int i=0; i<listeType.size(); i++) {
				if (arete.getType().getColor().equals(listeType.get(i).getColor()) || arete.getType().getColor().equals(this.couleurJoker)) {
					trouve = true;
				}
			}
			if (!trouve) {
				listeType.add(arete.getType());
			}
		}

		listeType.add(Type.creerType(this.couleurJoker));

		return listeType;
	}

	public void setCouleurJoker (Color couleurJoker) {
		this.couleurJoker = couleurJoker;
	}

	public Color getCouleurJoker () {
		return this.couleurJoker;
	}

	/*****************/
    /*     Arete     */
    /*****************/

	public void ajouterArete(Noeud n1, Noeud n2, int longueur, Color coulType){
		this.lstAretes.add(new Arete(n1, n2, longueur, Type.creerType(coulType)));
	}

	public void supprimerArete (Arete a) {
		this.lstAretes.remove(a);
	}

	public void setLongueurArete(Arete a, int longueur)
	{
		for (Arete arete : this.lstAretes)
		{
			if(arete.equals(a))
			{
				arete.setLongueur(longueur);
				return;
			}
			this.ctrl.majArete();
		}
	}

	/*****************/
    /* CarteObjectif */
    /*****************/

	public void ajouterCarteObjectif(Noeud n1, Noeud n2, int points){
		this.lstCarteObjectifs.add(new CarteObjectif(n1, n2, points));
	}

	public void supprimerCarteObjectif(CarteObjectif co){
		this.lstCarteObjectifs.remove(co);
	}

	public void setPointCarteObjectif(CarteObjectif cObjectif, int points){

		for(CarteObjectif co : this.lstCarteObjectifs){
			if(co.equals(cObjectif)){
				co.setPoints(points);
				return;
			}
			this.ctrl.majCarteObjectif();
		}
	}

	/*****************/
	/*     Ecrire    */
	/*****************/

	public void ecrireXML(BufferedImage image, String filePath ){
		try{
			File file = new File(filePath);
			ByteArrayOutputStream convert = new ByteArrayOutputStream();
			ImageIO.write(image,"png",convert);


			byte[] bytes = convert.toByteArray();


			if(!file.exists()){
				file.createNewFile();
			}
			OutputStreamWriter ow = new FileWriter(file.getAbsoluteFile(), StandardCharsets.UTF_8);
			BufferedWriter bw = new BufferedWriter(ow);
			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n");

			bw.write("<mappe>\n");

			bw.write("\t<regles>\n" + 
					 "\t\t<nombreJoker>" + this.getNombreJoker() + "</nombreJoker>\n" +
					 "\t\t<nombreJoueurMinimum>" + this.getNombreJoueurMinimum() + "</nombreJoueurMinimum>\n" +
					 "\t\t<nombreJoueurMaximum >" + this.getNombreJoueurMaximum() + "</nombreJoueurMaximum>\n" +
					 "\t\t<nombreVehiculeJoueur>" + this.getNbVehiculeJoueur() + "</nombreVehiculeJoueur>\n" +
					 "\t\t<nombreJoueurMiniDoubleRoute >" + this.getNombreJoueurMiniDoubleRoute() + "</nombreJoueurMiniDoubleRoute>\n" +
					 "\t\t<nombreVehiculeFin>" + this.getNbVehiculeFinPartie() + "</nombreVehiculeFin>\n" +
					 "\t\t<nombrePointCheminLong>" + this.getNbPointCheminLong() + "</nombrePointCheminLong>\n" +
					 "\t\t<longueurVehicule>" + this.getLongueurVehicule() + "</longueurVehicule>\n" +
					 "\t\t<hauteurVehicule>" + this.getHauteurVehicule() + "</hauteurVehicule>\n" +
					 "\t\t<espacementVehicule>" + this.getEspacementVehicule() + "</espacementVehicule>\n" +
					 "\t\t<couleurJoker>" + this.getCouleurJoker().getRGB() + "</couleurJoker>\n" +
					 "\t</regles>\n");

			bw.write("\t<lstNoeuds>"                         		 + "\n");

			

			for(Noeud n : this.lstNoeuds)
			{
				bw.write("	\t<noeud>"                +"\n" +
						   "\t\t\t<nom>" + n.getNom() + "</nom>" + "\n" +
						   "\t\t\t<x>"   + n.getX()   + "</x>"   + "\n" +
						   "\t\t\t<y>"   + n.getY()   + "</y>"   + "\n" +
						   "\t\t\t<NomX>" + n.getNomX() + "</NomX>" + "\n" +
						   "\t\t\t<NomY>" + n.getNomY() + "</NomY>" + "\n" +
						"    \t</noeud>\n");
			}
			bw.write("\t</lstNoeuds>\n");

			bw.write("\t<lstAretes>\n");
			for(Arete a : this.lstAretes){
				bw.write("	\t<arete>"                +"\n" +
						   "\t\t\t<noeud1>" + a.getNoeud1().getNom() + "</noeud1>" + "\n" +
						   "\t\t\t<noeud2>" + a.getNoeud2().getNom() + "</noeud2>" + "\n" +
						   "\t\t\t<longueur>" + a.getLongueur() + "</longueur>" + "\n" +
						   "\t\t\t<type>" + a.getType().toString() + "</type>" + "\n" + 
						   "\t\t\t<orientation>" + a.getOrientation() + "</orientation>" + "\n" +
						"    \t</arete>\n");
			}
			bw.write("\t</lstAretes>\n");

			bw.write("\t<lstCarteObjectifs>\n");
			for(CarteObjectif co : this.lstCarteObjectifs){
				bw.write("	\t<carteObjectif>"                +"\n" +
						   "\t\t\t<noeud1>" + co.getNoeud1().getNom() + "</noeud1>" + "\n" +
						   "\t\t\t<noeud2>" + co.getNoeud2().getNom() + "</noeud2>" + "\n" +
						   "\t\t\t<point>" + co.getPoints() + "</point>" + "\n" +
						"    \t</carteObjectif>\n");
			}
			bw.write("\t</lstCarteObjectifs>\n");

			bw.write("\t<hashMapCarteVehicules>\n");

			for(Type t : this.hashMapCarteVehicules.keySet()){
				bw.write("	\t<lstCarteVehicule>"                +"\n" +
						   "\t\t\t<nbCarte>" + this.hashMapCarteVehicules.get(t).size() + "</nbCarte>" + "\n" +
						   "\t\t\t<Type>" + t + "</Type>" + "\n" +
						"    \t</lstCarteVehicule>\n");
			}

			bw.write("\t</hashMapCarteVehicules>\n");

			bw.write("\t<imagePlateau>" + Base64.getEncoder().encodeToString(bytes) +
					 "</imagePlateau>\n");
					 
			bw.write("</mappe>");
			bw.close();
		}catch(Exception e){e.printStackTrace();}
	}

	public void lireXML(File file){

		try{
			SAXBuilder sxb = new SAXBuilder();
			Document document = sxb.build(file);
			Element racine = null;
			racine = document.getRootElement();

			Element regles = racine.getChild("regles");
			this.setNombreJoker(Integer.parseInt(regles.getChildText("nombreJoker")));
			this.setNombreJoueurMinimum(Integer.parseInt(regles.getChildText("nombreJoueurMinimum")));
			this.setNombreJoueurMaximum(Integer.parseInt(regles.getChildText("nombreJoueurMaximum")));
			this.setNbVehiculeJoueur(Integer.parseInt(regles.getChildText("nombreVehiculeJoueur")));
			this.setNombreJoueurMiniDoubleRoute(Integer.parseInt(regles.getChildText("nombreJoueurMiniDoubleRoute")));
			this.setNbVehiculeFinPartie(Integer.parseInt(regles.getChildText("nombreVehiculeFin")));
			this.setNbPointCheminLong(Integer.parseInt(regles.getChildText("nombrePointCheminLong")));
			this.setLongueurVehicule(Integer.parseInt(regles.getChildText("longueurVehicule")));
			this.setHauteurVehicule(Integer.parseInt(regles.getChildText("hauteurVehicule")));
			this.setEspacementVehicule(Double.parseDouble(regles.getChildText("espacementVehicule")));
			this.setCouleurJoker(new Color(Integer.parseInt(regles.getChildText("couleurJoker"))));

			List listNoeud = racine.getChild("lstNoeuds").getChildren("noeud");
			Iterator i = listNoeud.iterator();
			while(i.hasNext()){
				Element courant = (Element)i.next();
				this.lstNoeuds.add(new Noeud(courant.getChildText("nom"), (int)Double.parseDouble(courant.getChildText("x").trim()), (int)Double.parseDouble(courant.getChildText("y").trim()),
				(int)Double.parseDouble(courant.getChildText("x").trim()) + 20 , (int)Double.parseDouble(courant.getChildText("y").trim())+20 ));
			}

			List listArete = racine.getChild("lstAretes").getChildren("arete");
			Iterator j = listArete.iterator();
			while(j.hasNext()){
				Element courant = (Element)j.next();
				String nomNoeud1 = courant.getChildText("noeud1");
				String nomNoeud2 = courant.getChildText("noeud2");
				int    longueur  = Integer.parseInt(courant.getChildText("longueur"));
				Color  type      = new Color(Integer.parseInt(courant.getChildText("type")));				
				boolean orientation = Boolean.parseBoolean(courant.getChildText("orientation"));

				for(Noeud n : this.lstNoeuds)
				{
					if(n.getNom().equals(nomNoeud1))
					{
						for(Noeud m : this.lstNoeuds)
						{
							if(m.getNom().equals(nomNoeud2))
							{
								this.lstAretes.add(new Arete(n, m, longueur, Type.creerType(type), orientation));
							}
						}
					}
				}
			}

			List listCarteObjectif = racine.getChild("lstCarteObjectifs").getChildren("carteObjectif");
			Iterator k = listCarteObjectif.iterator();
			while(k.hasNext()){
				Element courant = (Element)k.next();
				String nomNoeud1 = courant.getChildText("noeud1");
				String nomNoeud2 = courant.getChildText("noeud2");
				int    points    = Integer.parseInt(courant.getChildText("point"));

				for(Noeud n : this.lstNoeuds)
				{
					if(n.getNom().equals(nomNoeud1))
					{
						for(Noeud m : this.lstNoeuds)
						{
							if(m.getNom().equals(nomNoeud2))
							{
								this.lstCarteObjectifs.add(new CarteObjectif(n, m, points));
							}
						}
					}
				}
			
			}

			List listCarteVehicule = racine.getChildren("lstCarteVehicules");
			Iterator l = listCarteVehicule.iterator();
			while(l.hasNext()){
				Element courant = (Element)l.next();
				int 	nbCarte = Integer.parseInt(courant.getChildText("type"));
				Color   type = new Color(Integer.parseInt(courant.getChildText("nombre")));

				for(int m=0; m < nbCarte; m++){
					this.lstCarteVehicules.add(new CarteVehicule(Type.creerType(type)));
				}

				this.hashMapCarteVehicules.put(Type.creerType(type), this.lstCarteVehicules);
			}


			Element imagePlateau = racine.getChild("imagePlateau");

			String str = imagePlateau.getText();
			byte[] bytes = Base64.getDecoder().decode(str);		
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			BufferedImage bImage2 = ImageIO.read(bis);
			this.ctrl.setImagePlateau(bImage2);
			this.ctrl.majLabelResume(this.nbrJoueurMinimum, this.nbrJoueurMaximum, this.nbVehiculeJoueur, this.nbrJoueurMiniDoubleRoute,this.nbVehiculeFinPartie, this.nbPointCheminLong);
			this.ctrl.majTxtRegleJeu(this.nbrJoueurMinimum, this.nbrJoueurMaximum, this.nbVehiculeJoueur, this.nbrJoueurMiniDoubleRoute,this.nbVehiculeFinPartie, this.nbPointCheminLong);
			this.ctrl.majCouleurJoker(this.couleurJoker);
			this.ctrl.majNoeud();
			this.ctrl.majArete();
			this.ctrl.majCarteObjectif();
		}catch(Exception e){e.printStackTrace();} 
	}

	public ArrayList<Noeud> getLstNoeuds(){
		return this.lstNoeuds;
	}

	public ArrayList<Arete> getLstAretes(){
		return this.lstAretes;
	}

	public ArrayList<CarteObjectif> getLstCarteObjectifs(){
		return this.lstCarteObjectifs;
	}

	public void supprimerLstAretes(){
		this.lstAretes.clear();
	}

	public void supprimerLstCarteObjectifs(){
		this.lstCarteObjectifs.clear();
	}

	public void supprimerLstNoeuds(){
		this.lstNoeuds.clear();
	}
}