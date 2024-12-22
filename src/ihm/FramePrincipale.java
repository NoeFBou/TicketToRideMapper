package src.ihm;

import src.Controleur;
import src.metier.Noeud;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class FramePrincipale extends JFrame implements ActionListener
{
    private JPanel panelFormulaire;

    private JPanel panelVide,panelBase;

    private PanelPlateau panelPlateau;

    private PanelVehicule panelVehicule;

    private PanelAretes panelArete;

    private PanelListeObjectif panelListeObjectif;

    private PanelRegleJeu panelRegleJeu;

    private PanelResume panelResume;

	private JMenuBar menuBarre;

    private JMenu menuFichier;

    private JMenu menuAide;

    private JMenuItem menuNouveau;

    private JMenuItem menuOuvrir;

    private JMenuItem menuRegles;

    private JTextArea txtAide;

    private JScrollPane scrollPane;

    CardLayout card;

    private File reglePDF;

    private Image imgLogo;
    
    private Controleur ctrl;


	public FramePrincipale(Controleur ctrl){

        this.ctrl = ctrl;
        Toolkit kit = Toolkit.getDefaultToolkit();

        Dimension tailleEcran = kit.getScreenSize();
        Insets scnMax = kit.getScreenInsets(getGraphicsConfiguration());
        int tailleBarTache = scnMax.bottom;

        this.panelVide = new JPanel(new GridBagLayout());
        this.panelBase = new JPanel(new BorderLayout());
        this.panelFormulaire = new JPanel(new CardLayout());
        this.panelPlateau = new PanelPlateau(this.ctrl);
        this.panelVehicule = new PanelVehicule(this.ctrl);
        this.panelArete = new PanelAretes(this.ctrl);
        this.panelListeObjectif = new PanelListeObjectif(this.ctrl);
        this.panelRegleJeu = new PanelRegleJeu(this.ctrl);
        this.panelResume = new PanelResume(this.ctrl);

        this.scrollPane = new JScrollPane(this.panelPlateau);

        this.setTitle("Concepteur de Plateau");
        this.setResizable(true);
        this.setUndecorated(false);

        this.txtAide = new JTextArea("Fichier > nouveau    Ctrl + N : " + "\n" + "Pour ouvrir l'image du plateau de jeu" + "\n\n" + "Fichier > ouvrir         Ctrl + O : " + "\n" + "Pour ouvrir  et modifier un plateau de jeu existant" + "\n\n" + "Aide > Regles          Ctrl + A : " + "\n" + "Regles de base des Aventuriers du Rail");
        this.txtAide.setEditable(false);

        this.imgLogo = kit.getImage("./src/data/images/logo.png") ;
		this.setIconImage(imgLogo);

        this.menuBarre = new JMenuBar();

        this.menuFichier = new JMenu ("Fichier");
        this.menuNouveau = new JMenuItem("Nouveau");
        this.menuOuvrir = new JMenuItem("Ouvrir");

        this.menuAide = new JMenu("Aide");
        this.menuRegles = new JMenuItem("Regles");

        this.menuRegles.setIcon(new ImageIcon("./src/data/images/Regles.png"));
        
        this.menuOuvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,  InputEvent.CTRL_DOWN_MASK));
        this.menuNouveau.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,  InputEvent.CTRL_DOWN_MASK));
        this.menuRegles.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        this.panelVide.add(this.txtAide);
        this.panelVide.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        this.panelFormulaire.add(this.panelRegleJeu,"panelRegleJeu");
        this.panelFormulaire.add(this.panelVehicule,"panelVehicule");
        this.panelFormulaire.add(this.panelArete,"panelArete");
        this.panelFormulaire.add(this.panelListeObjectif,"panelListeObjectif");
        this.panelFormulaire.add(this.panelResume,"panelResume");


        this.menuFichier.add(this.menuNouveau);
        this.menuFichier.add(this.menuOuvrir);

        this.menuAide.add(this.menuRegles);

        this.menuBarre.add(this.menuFichier);
        this.menuBarre.add(this.menuAide);

        
        this.panelBase.add(this.scrollPane, BorderLayout.CENTER);
        this.panelFormulaire.setPreferredSize(new Dimension(400, 200));
        this.panelBase.add(this.panelFormulaire, BorderLayout.EAST);
        
        this.add(this.panelVide);
        

        this.setJMenuBar(this.menuBarre);

        this.reglePDF = new File("./src/data/PDF/Regles.pdf");

        //Ajout des listener
        this.menuRegles.addActionListener(this::actionPerformed);
        this.menuNouveau.addActionListener(this::actionPerformed);
        this.menuOuvrir.addActionListener(this::actionPerformed);


        this.pack();

        this.setSize((int) tailleEcran.getWidth(), (int) tailleEcran.getHeight() - tailleBarTache);

		this.setExtendedState(this.MAXIMIZED_BOTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setVisible(true);

        



    }

    public void changePanel(){
        this.setContentPane(panelBase);
        this.revalidate();
    }
    /*****************/
    /*     NOEUD     */
    /*****************/

    public void majNoeud()
    {
        this.panelRegleJeu.maJTable(this.ctrl.getLstNoeuds());
        this.panelArete.majNoeud(this.ctrl.getLstNoeuds());
        this.panelResume.majNoeud(this.ctrl.getLstNoeuds());
        this.panelListeObjectif.majTableNoeud(this.ctrl.getLstNoeuds());
    }


    public void ajouterNoeud(String nom, int x, int y, int nomX, int nomY) {
        this.panelRegleJeu.maJTable(this.ctrl.getLstNoeuds());
        this.panelArete.majNoeud(this.ctrl.getLstNoeuds());
    }

    public void setPositionNoeud(Noeud noeud, int x, int y, int nomX, int nomY) {
        this.panelPlateau.majIHM();
        this.panelArete.majNoeud(this.ctrl.getLstNoeuds());
    }
    
    /*****************/
    /*     Arete     */
    /*****************/
    
    public void majArete()
    {   
        this.panelArete.majArete(this.ctrl.getLstAretes());
        this.panelResume.majArete(this.ctrl.getLstAretes());
        this.panelVehicule.majTableVehicule(this.ctrl.getLstType());
    }

    /*****************/
    /*    CarteObj   */
    /*****************/

    public void majCarteObjectif()
    {   
        this.panelListeObjectif.majCarteObjectif(this.ctrl.getLstObjectifs());
    }

    //Permet de changer de panel 
    //nomPanel : nom du panel a afficher
    public void changePanel(String nomPanel){
        this.card = (CardLayout) this.panelFormulaire.getLayout();
        this.card.show(this.panelFormulaire,nomPanel);
    }

    /*****************/
    /*     Resume    */
    /*****************/

    public void majLabelResume(int nbJoueurMin, int nbJoueurMax, int doubeVoie, int nbWagon, int nbWagonFin, int nbPointCheminLong){
        this.panelResume.majLabelResume(nbJoueurMin, nbJoueurMax, doubeVoie, nbWagon, nbWagonFin, nbPointCheminLong);
    }

    public void majTxtRegleJeu(int nbJoueurMin, int nbJoueurMax, int doubeVoie, int nbWagon, int nbWagonFin, int nbPointCheminLong){
        this.panelRegleJeu.majTxtRegleJeu(nbJoueurMin, nbJoueurMax, doubeVoie, nbWagon, nbWagonFin, nbPointCheminLong);
    }

    public void majCouleurJoker(Color couleurJoker){
        this.panelArete.majCouleurJoker(couleurJoker);
        this.panelResume.majCouleurJoker(couleurJoker);
        this.panelVehicule.majTableVehicule(this.ctrl.getLstType());
    }

    public void supprimerRegleJeu(){
        this.panelRegleJeu.supprimerRegleJeu();
    }

    public void suppimerResume(){
        this.panelResume.supprimerResume();
    }


    /*****************/
    /*     utiles    */
    /*****************/

    public void majIHM() {
        this.panelPlateau.majIHM();
        this.panelPlateau.repaint(); 
        this.panelRegleJeu.maJTable(this.ctrl.getLstNoeuds());
        this.panelListeObjectif.majTableNoeud(this.ctrl.getLstNoeuds());
    }

    
    public void actionPerformed( ActionEvent e){

        if(e.getSource() == this.menuRegles){

            try{
                Desktop.getDesktop().open(reglePDF);
            }catch(Exception erreur){erreur.printStackTrace();}
        }
		if(e.getSource() == this.menuNouveau){
			try{
				JFileChooser chooser = new JFileChooser();

                FileFilter filtre = new FileNameExtensionFilter("Image files", new String[]{"png","jpg","gif"});
                chooser.setFileFilter(filtre);
                chooser.setAcceptAllFileFilterUsed(false);
				
				int res = chooser.showOpenDialog(this);
                if(res == JFileChooser.APPROVE_OPTION)
                {
                    File file = new File(chooser.getSelectedFile().getPath());
                    BufferedImage bi = ImageIO.read(file);

                    this.ctrl.setImagePlateau(bi);
                    this.ctrl.supprimerAretes();
                    this.ctrl.supprimerNoeuds();
                    this.ctrl.supprimerCartesObjectif();
                    this.ctrl.supprimerRegleJeu();
                    this.ctrl.supprimerResume();
                    this.changePanel();
                }
			}catch(Exception erreur){erreur.printStackTrace();}
		}
        if(e.getSource() == this.menuOuvrir){
            try{
				JFileChooser chooser = new JFileChooser("./src/data/mappe/");

                FileFilter filtre = new FileNameExtensionFilter("XML (*.xml)", "xml");
                chooser.setFileFilter(filtre);
                chooser.setAcceptAllFileFilterUsed(false);
				
				int res = chooser.showOpenDialog(this);
				if (res == JFileChooser.APPROVE_OPTION)
                {
                    this.ctrl.supprimerAretes();
                    this.ctrl.supprimerNoeuds();
                    this.ctrl.supprimerCartesObjectif();
					this.ctrl.lireXML(chooser.getSelectedFile());
                    this.changePanel();
                }

            }catch(Exception erreur){erreur.printStackTrace();}
        }
    }

}
