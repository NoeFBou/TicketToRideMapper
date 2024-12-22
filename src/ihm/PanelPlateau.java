package src.ihm;

import src.Controleur;
import src.metier.Arete;
import src.metier.Noeud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PanelPlateau extends JPanel
{
	private Controleur  ctrl;
	private Noeud  NoeudCourant;
	private int diametre;

    //PROVISOIRE
    private final int TAILLEWAGON = 25;
    private final int LARGEURWAGON = 20;
    private final double ATTACHEWAGON = 1.5;
	private final int HAUTEURLABEL = 15;

	public PanelPlateau(Controleur ctrl)
	{
		this.ctrl     = ctrl;
		this.NoeudCourant = null;
		this.diametre = this.ctrl.getMetier().getDiametre();

		//this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(1571, 918));
		this.addMouseListener(new GererSouris());
		this.addMouseMotionListener(new GererMouvementSouris());
    }


	//supprimer un noeud
	public void supprimerNoeud(Noeud n)
	{
		this.ctrl.supprimerNoeud(n);
		this.ctrl.majIHM();
	}

	//modifier la position d'un noeud lors d'un deplacem
	public void setPositionNoeud(int x, int y, int nomX, int nomY)
	{
		this.ctrl.setPositionNoeud(NoeudCourant,x, y, nomX, nomY);
		this.ctrl.majIHM();
	}


	//retourne le noeud correspondant au clique de la souris
	public Noeud sourisSurNoeud(int x, int y){
		for (Noeud n : this.ctrl.getLstNoeuds())
		{
			int xNoeud = (int) n.getX();
			int yNoeud = (int) n.getY();
			if (xNoeud-(this.diametre/2) <= x && x <= xNoeud+(this.diametre/2) && yNoeud-(this.diametre/2) <= y && y <= yNoeud+(this.diametre/2)) {
				return n;
			}
		}
		return null;
	}

	//retourne le noeud dont la position du nom correspond au clique de la souris
	public Noeud sourisSurNomNoeud(Graphics g, int x, int y){
		for (Noeud n : this.ctrl.getLstNoeuds())
		{
			int nomX = n.getNomX();
			int nomY = n.getNomY();
			int largeurNom = SwingUtilities.computeStringWidth(g.getFontMetrics(g.getFont()), n.getNom())+6;
			if (nomX <= x && x <= nomX+largeurNom && nomY-this.HAUTEURLABEL <= y && y <= nomY) {
				return n;
			}
		}
		return null;
	}

	public void majIHM()
	{
		super.repaint();		
	}
	
	public void paint(Graphics g) {

        int longueurWagon = this.ctrl.getMetier().getLongueurVehicule();
        double espacementWagon = this.ctrl.getMetier().getEspacementVehicule();

		super.paint(g);

		Graphics2D g1 = (Graphics2D) g;
        g1.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g1.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g1.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g1.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g1.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g1.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g1.setColor(Color.BLACK);

		for (Arete arete : this.ctrl.getLstAretes()) {

            // calcul de la longueur entre le noeud 1 et le noeud 2
			double longueur = Math.sqrt(Math.pow(arete.getNoeud1().getX() - arete.getNoeud2().getX(), 2)	+ Math.pow(arete.getNoeud1().getY() - arete.getNoeud2().getY(), 2));
            double distanceWagon = (longueurWagon*espacementWagon) * arete.getLongueur();

            double nbArete=0;
            double indexeArete=0;
            double indiceDecalage =0 ;
            for (Arete areteD : this.ctrl.getLstAretes()) 
                if ( (arete.getNoeud1().equals(areteD.getNoeud1()) && arete.getNoeud2().equals(areteD.getNoeud2())) || (arete.getNoeud1().equals(areteD.getNoeud2()) && arete.getNoeud2().equals(areteD.getNoeud1())))
                    {
                        nbArete++;
                        if (arete.equals(areteD))
                            indexeArete=nbArete;
                    }
            indiceDecalage = ( -((nbArete-1)/2) + (indexeArete-1)) * longueurWagon/2 ;
            //l'angle entre le point 1 et 2 par rapport à la base du plan
            double teta = Math.atan((arete.getNoeud2().getY()-arete.getNoeud1().getY())/(arete.getNoeud2().getX()-arete.getNoeud1().getX()));

			if (longueur < distanceWagon) {

                //calcule du demi petit rayon de l ellipse
                double grandR = longueur/2;
                double demiR = Math.sqrt(Math.abs(2*Math.pow(distanceWagon/Math.PI, 2)-Math.pow(grandR,2)));
                
                //calcule des coordonné du centre du cercle
                double centreX = (arete.getNoeud2().getX() - arete.getNoeud1().getX()) / 2 + arete.getNoeud1().getX();
                double centreY = (arete.getNoeud2().getY() - arete.getNoeud1().getY()) / 2 + arete.getNoeud1().getY();
                
                for(int cpt = 0 ; cpt < arete.getLongueur();cpt++) {
                    //coordonnée polaire des deux points du wagon
                    double angleWagon1 = (Math.PI)/arete.getLongueur()*cpt;
                    double wagon1X =grandR*Math.cos(angleWagon1)+ indiceDecalage*Math.cos(angleWagon1);
                    double wagon1Y = demiR*Math.sin(angleWagon1)+ indiceDecalage*Math.cos(angleWagon1);
                    
                    double angleWagon2 = (Math.PI)/arete.getLongueur()*(cpt+1);
                    double wagon2X =  grandR*Math.cos(angleWagon2)+ indiceDecalage*Math.cos(angleWagon2);
                    double wagon2Y =  demiR*Math.sin(angleWagon2) +indiceDecalage*Math.sin(angleWagon2);
                    
                    if(arete.getOrientation())
                    {
                        wagon1Y *= -1;
                        wagon2Y *= -1;
                    }

                    //on applique la rotation et la translation
                    double w1XP = centreX +rotationX(wagon1X,wagon1Y,teta);
                    double y1YP = centreY +rotationY(wagon1X,wagon1Y,teta);
                    double x2XP = centreX +rotationX(wagon2X,wagon2Y,teta);
                    double y2YP = centreY +rotationY(wagon2X,wagon2Y,teta);

                    tracerWagon( w1XP, y1YP, x2XP, y2YP,1,g1,arete.getType().getColor());
                }
            }
            else 
                {
                    if (arete.getNoeud1().getX() < arete.getNoeud2().getX())
                        teta = teta + Math.PI;
                
                    double decalageY = -indiceDecalage*Math.cos(teta);
                    double decalageX = -indiceDecalage*Math.sin(teta);
                    tracerWagon(arete.getNoeud1().getX()+decalageX, arete.getNoeud1().getCenterY()+decalageY, arete.getNoeud2().getX()+decalageX, arete.getNoeud2().getY()+decalageY, arete.getLongueur(), g1,arete.getType().getColor());
                }
			} 
            
        for (Noeud noeud : this.ctrl.getLstNoeuds()) {
			g1.draw(noeud);
			g.setColor(Color.BLACK);
			g1.fillOval((int) noeud.getX() - this.diametre / 2, (int) noeud.getY() - this.diametre / 2, this.diametre, this.diametre);
			g.setColor(Color.WHITE);
			g1.fillRect(noeud.getNomX()-2, noeud.getNomY()-12, SwingUtilities.computeStringWidth(g.getFontMetrics(g.getFont()), noeud.getNom())+5, this.HAUTEURLABEL);
			g1.setColor(Color.BLACK);
			g1.drawString(noeud.getNom(), noeud.getNomX(), noeud.getNomY());
		}

		g.setColor(Color.RED);
    }

    private double rotationX(double nX,double nY, double teta) {
        double xM = nX;
        double yM = nY;

        double x = xM*Math.cos(teta)-yM*Math.sin(teta);
        return x;
    }
    private double rotationY(double nX,double nY, double teta) {
        double xM = nX;
        double yM = nY;

        double y = xM*Math.sin(teta)+ yM*Math.cos(teta);
        return y;
    }

    private void tracerWagon(double aX, double aY, double bX, double bY, int nbWagon, Graphics2D g1,Color color) {

        int hauteurWagon = this.ctrl.getMetier().getHauteurVehicule();
        int longueurWagon = this.ctrl.getMetier().getLongueurVehicule();

        double longueur = Math.sqrt(Math.pow(aX- bX, 2)	+ Math.pow(aY - bY, 2));

        //longueur des wagon cumulé
		double lWagon = nbWagon * longueurWagon;

        double ecart = (longueur - lWagon)/(nbWagon+1);
        
        double beta = Math.atan((bY-aY)/(bX-aX));

        if (bX < aX) {
            beta = beta + Math.PI;
        }

        double ecX = ecart * Math.cos(beta);
        double ecY = ecart * Math.sin(beta);
        double waX = longueurWagon * Math.cos(beta);
        double waY = longueurWagon * Math.sin(beta);

		g1.setStroke(new BasicStroke(2));
        g1.setColor(Color.BLACK);
        
        g1.setStroke(new BasicStroke(hauteurWagon));
		for (int cpt = 0; cpt < nbWagon; cpt++) 
			g1.drawLine(    (int) (aX + ecX + cpt * ( ecX + waX)),
					        (int) (aY + ecY +  cpt * ( ecY + waY)),
					        (int) (aX + (cpt+1) * ( ecX + waX)),
					        (int) (aY + (cpt+1) * ( ecY + waY)));
        
        g1.setColor(color);
        g1.setStroke(new BasicStroke(hauteurWagon-2));
		for (int cpt = 0; cpt < nbWagon; cpt++) 
			g1.drawLine(    (int) (aX + ecX + cpt * ( ecX + waX)),
					        (int) (aY + ecY +  cpt * ( ecY + waY)),
					        (int) (aX + (cpt+1) * ( ecX + waX)),
					        (int) (aY + (cpt+1) * ( ecY + waY)));

        g1.setColor(Color.BLACK);
        g1.setStroke(new BasicStroke(2));  
		
	}

	private class GererSouris extends MouseAdapter
	{
		public void mousePressed (MouseEvent e)
		{
			//if clic droit, supprimer le noeud
			if (e.getButton() == MouseEvent.BUTTON3)
			{
				//vérifier si on a cliqué sur un noeud et le supprimer
				if (sourisSurNoeud(e.getX(), e.getY())!= null) {
					PanelPlateau.this.NoeudCourant = sourisSurNoeud(e.getX(), e.getY());
					boolean estRelie = false;
					//vérifier si le noeud est relié à d'autres noeuds par une arête
					for (Arete arete : PanelPlateau.this.ctrl.getLstAretes()) {
						if (arete.getNoeud1() == PanelPlateau.this.NoeudCourant || arete.getNoeud2() == PanelPlateau.this.NoeudCourant) {
							estRelie = true;
							break;
						}
					}
					if ( estRelie == false) {
						PanelPlateau.this.supprimerNoeud(NoeudCourant);
					}
					else {
						JOptionPane.showMessageDialog(null, "Le noeud est relié à d'autres noeuds par au moins une arête. Veuillez supprimer ces arêtes avant de supprimer le noeud.", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					PanelPlateau.this.NoeudCourant = null;
				}
			}
			//if click gauche, ajouter un noeud
			else if (e.getButton() == MouseEvent.BUTTON1)
			{
				//vérifier si on a cliqué sur un noeud et le sélectionner en noeud courant
				if (sourisSurNoeud(e.getX(), e.getY())!= null && e.getX()< 1200 && e.getY() <800 ) {
					PanelPlateau.this.NoeudCourant = sourisSurNoeud(e.getX(), e.getY());
				}
				//vérifier si on a cliqué sur un nom de noeud et le sélectionner en noeud courant
				else if (sourisSurNomNoeud(PanelPlateau.this.getGraphics(), e.getX(), e.getY())!= null) {
					PanelPlateau.this.NoeudCourant = sourisSurNomNoeud(PanelPlateau.this.getGraphics(), e.getX(), e.getY());
				
					boolean erreurNom = false;
					String nomVille = JOptionPane.showInputDialog(null,"Nouveau nom du noeud " + NoeudCourant.getNom() + " :","Saisie",JOptionPane.QUESTION_MESSAGE);
					
					for(Noeud n : PanelPlateau.this.ctrl.getLstNoeuds()){
						String nom = n.getNom();
						if(nomVille.equals(nom))
							erreurNom = true;
					}

					if(nomVille == null || nomVille.equals("")) {
						JOptionPane.showMessageDialog(null, "Veuillez entrer un nom de noeud","Erreur", JOptionPane.ERROR_MESSAGE);
					}
					else if(erreurNom == true)
						JOptionPane.showMessageDialog(null, "Noeud déjà Existant","Erreur", JOptionPane.ERROR_MESSAGE);
					else {
						PanelPlateau.this.NoeudCourant.setNom(nomVille);
						PanelPlateau.this.NoeudCourant = null;
						PanelPlateau.this.ctrl.majIHM();
						PanelPlateau.this.ctrl.majNoeud();
					}
				}
				else {//ajouter un noeud
					boolean Nom = false;
                    String nomVille = JOptionPane.showInputDialog(null,"Nom du Noeud","Saisie",JOptionPane.QUESTION_MESSAGE);
					if(nomVille!= null){
						for(Noeud n : PanelPlateau.this.ctrl.getLstNoeuds()){
							String nom = n.getNom();
							if(nomVille.equals(nom))
								Nom = true;
						}
					}
					if(nomVille == null || nomVille.equals(""))
						JOptionPane.showMessageDialog(null, "Veuillez entrer un nom de noeud");
					else if(Nom == true)
						JOptionPane.showMessageDialog(null, "Noeud déjà Existant","Erreur", JOptionPane.ERROR_MESSAGE);
					else if(e.getX() < 1200 && e.getY() < 800)
                    	PanelPlateau.this.ctrl.ajouterNoeud(nomVille, e.getX(), e.getY(), e.getX()+20, e.getY()+20);
					else
						JOptionPane.showMessageDialog(null, "Vous êtes sorti du plateau","Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
		}

		//si le clic souris est relaché, on ne sélectionne plus de noeud
		public void mouseReleased(MouseEvent e) 
		{
			PanelPlateau.this.NoeudCourant = null;
		}

		public void mouseClicked(MouseEvent e)	
			{
			if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
				
				//vérifier si on a cliqué sur un noeud et le sélectionner en noeud courant
				if (sourisSurNoeud(e.getX(), e.getY())!= null)
					PanelPlateau.this.NoeudCourant = sourisSurNoeud(e.getX(), e.getY());
			}
		}
		
	}

	private class GererMouvementSouris extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent e) 
		{
			int largeur = e.getX();
			int largeurNom = (int)(e.getX()+(NoeudCourant.getNomX()-NoeudCourant.getX()));		
			int hauteur = e.getY();
			int hauteurNom = (int)(e.getY()+(NoeudCourant.getNomY()-NoeudCourant.getY()));
			//bouger la position si un noeud est sélectionné
			if (PanelPlateau.this.NoeudCourant != null) {
				//PanelPlateau.this.ctrl.setPositionNoeud(PanelPlateau.this.NoeudCourant, e.getX(), e.getY(), e.getX(), e.getY());
				PanelPlateau.this.NoeudCourant.setFrame( e.getX() / 30d , e.getY() / 30d, PanelPlateau.this.diametre /1d, PanelPlateau.this.diametre /1d);
				
				//Si on dépasse à droite du Panel
				if(e.getX()+(NoeudCourant.getNomX()-NoeudCourant.getX()) > 1185)
				{
					largeur = 1185;
					largeurNom = (int)(1200-(NoeudCourant.getNomX()-NoeudCourant.getX()));
					//Si on dépasse à droite et en bas du Panel
					if(e.getY()+(NoeudCourant.getNomY()-NoeudCourant.getY()) > 785)
					{
						hauteur = 785;
						hauteurNom = (int)(800-(NoeudCourant.getNomY()-NoeudCourant.getY()));
					}
				}
				//Si on dépasse en bas du Panel
				else if(e.getY()+(NoeudCourant.getNomY()-NoeudCourant.getY()) > 785)
				{
					hauteur = 785;
					hauteurNom = (int)(800-(NoeudCourant.getNomY()-NoeudCourant.getY()));
					//Si on dépasse à droite et en bas du Panel
					if(e.getX()+(NoeudCourant.getNomX()-NoeudCourant.getX()) > 1185)
					{
						largeur = 1185;
						largeurNom = (int)(1200-(NoeudCourant.getNomX()-NoeudCourant.getX()));
					}
				}
				PanelPlateau.this.ctrl.setPositionNoeud(
														PanelPlateau.this.NoeudCourant,
														largeur,
														hauteur,
														largeurNom,
														hauteurNom
													   );
				majIHM();
			}
		}
	}

	//Permet de dessiner un fond d'écran 
	public void paintComponent (Graphics g)
	{
		super.paintComponent(g);
		Image img = this.ctrl.getImagePlateau();
		
		g.drawImage(img, 0, 0, 1200 , 800 , this);

	}
}