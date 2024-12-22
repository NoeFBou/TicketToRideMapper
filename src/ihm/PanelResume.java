package src.ihm;

import src.Controleur;
import src.ihm.grilles.GrillesAreteModel;
import src.ihm.grilles.GrillesCartesObjectifsModel;
import src.ihm.grilles.GrillesNoeudModel;
import src.ihm.renderer.ColorCellRendererArete;
import src.metier.Arete;
import src.metier.CarteObjectif;
import src.metier.Noeud;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class PanelResume extends JPanel implements ActionListener{

    private GrillesNoeudModel modelN;
    private GrillesAreteModel modelA;
    private GrillesCartesObjectifsModel modelO;

    private JPanel panelAll;
    private JPanel panelBtn;
    private JPanel panelJoueur;
    private JPanel panelNoeud;
    private JPanel panelArrete;
    private JPanel panelObjectif;

    private JLabel lblJMin;
    private JLabel lblJMax;
    private JLabel lblDoubleMin;
    private JLabel lblVehicule;
    private JLabel lblFin;
    private JLabel lblCheminLong;

    private JTable jTabNoeud;
    private JTable jTabArrete;
    private JTable jTabCarte;

    private JButton btnRetour;
    private JButton btnEnregistrer;
    private JButton btnCouleurJoker;

    private int nbJoueurMin;
    private int nbJoueurMax;
    private int doubleVoie;
    private int nbVehiculeJoueur;
    private int nbVehiculeFin;
    private int nbPointCheminLong;

    private Controleur ctrl;

    //private JScrollBar sbTout;

    public PanelResume(Controleur ctrl){

        this.ctrl = ctrl;
        this.modelN = new GrillesNoeudModel(this.ctrl);
        this.modelA = new GrillesAreteModel(this.ctrl);
        this.modelO = new GrillesCartesObjectifsModel(this.ctrl);

        this.ctrl = ctrl;

        this.jTabNoeud = new JTable(this.modelN);
        this.jTabArrete = new JTable(this.modelA);
        this.jTabCarte = new JTable(this.modelO);

        this.jTabNoeud.setFillsViewportHeight(true);
        this.jTabArrete.setFillsViewportHeight(true);
        this.jTabCarte.setFillsViewportHeight(true);

        this.jTabNoeud.setEnabled(true);
        this.jTabArrete.setEnabled(true);
        this.jTabCarte.setEnabled(true);

        this.jTabArrete.setDefaultRenderer(Color.class, new ColorCellRendererArete());
        this.jTabArrete.getColumnModel().getColumn(3).setCellRenderer(new ColorCellRendererArete());
        
        JScrollPane spTabNoeud = new JScrollPane(this.jTabNoeud);
        JScrollPane spTabArrete = new JScrollPane(this.jTabArrete);
        JScrollPane spTabCarte = new JScrollPane(this.jTabCarte);

        this.panelAll = new JPanel();
        this.panelJoueur = new JPanel();
        this.panelNoeud = new JPanel();
        this.panelArrete = new JPanel();
        this.panelObjectif = new JPanel();
        this.panelBtn = new JPanel();

        this.nbJoueurMax   = ctrl.getMetier().getNombreJoueurMaximum();
        this.nbJoueurMin   = ctrl.getMetier().getNombreJoueurMinimum();
        this.doubleVoie    = ctrl.getMetier().getNombreJoueurMiniDoubleRoute();
        this.nbVehiculeJoueur = ctrl.getMetier().getNbVehiculeJoueur();
        this.nbVehiculeFin    = ctrl.getMetier().getNbVehiculeFinPartie();
        this.nbPointCheminLong = ctrl.getMetier().getNbPointCheminLong();
        this.btnCouleurJoker = new JButton("Couleur joker");


        this.lblJMin = new JLabel("Joueur Min : " + this.nbJoueurMin);
        this.lblJMax = new JLabel("Joueur Max : " + this.nbJoueurMax);
        this.lblVehicule = new JLabel("Vehicule par joueur : " + this.nbVehiculeJoueur);
        this.lblDoubleMin = new JLabel("Joueur Min pour double voies : " + this.doubleVoie );
        this.lblFin = new JLabel("Nombres de vehicules pour finir la partie : " +  this.nbVehiculeFin); 
        if(this.nbPointCheminLong < 1){
            this.lblCheminLong = new JLabel("Règle du chemin le plus long : Désactivée");
        }else{
            this.lblCheminLong = new JLabel("Points pour le chemin le plus long : " + this.nbPointCheminLong );
        }

        this.btnRetour = new JButton("Retour");
        this.btnEnregistrer = new JButton("Enregistrer");
        
        this.setLayout(new BorderLayout());
        this.panelAll.setLayout(new GridLayout(4,1));
        this.panelJoueur.setLayout(new GridLayout(7,1));
        this.panelNoeud.setLayout(new BorderLayout());
        this.panelArrete.setLayout(new BorderLayout());
        this.panelObjectif.setLayout(new BorderLayout());
        this.panelBtn.setLayout(new GridLayout(1,3));
        
        this.panelJoueur.add(this.btnCouleurJoker);
        this.panelJoueur.add(this.lblJMin);
        this.panelJoueur.add(this.lblJMax);
        this.panelJoueur.add(this.lblVehicule);
        this.panelJoueur.add(this.lblDoubleMin);
        this.panelJoueur.add(this.lblFin);
        this.panelJoueur.add(this.lblCheminLong);

        this.panelNoeud.add(spTabNoeud);
        this.panelArrete.add(spTabArrete);
        this.panelObjectif.add(spTabCarte);

        this.panelBtn.add(this.btnRetour);
        this.panelBtn.add(new JLabel(""));
        this.panelBtn.add(this.btnEnregistrer);
        
        this.panelAll.add(this.panelJoueur);
        this.panelAll.add(this.panelNoeud);
        this.panelAll.add(this.panelArrete);
        this.panelAll.add(this.panelObjectif);
        this.add(new JLabel("Resumé de l'ensemble des parametres",SwingConstants.CENTER),BorderLayout.NORTH);
        this.add(this.panelAll,BorderLayout.CENTER);
        this.add(this.panelBtn,BorderLayout.SOUTH);
        
        this.btnRetour.addActionListener(this);
        this.btnEnregistrer.addActionListener(this);
        this.btnCouleurJoker.addActionListener(this);
    }
    
    public void majNoeud(ArrayList<Noeud> noeuds) {
        this.modelN.majTable(noeuds);
    }

    public void majArete(ArrayList<Arete> lstAretes) {
        this.modelA.majTable(lstAretes);
    }

    public void majCarteObjectif(ArrayList<CarteObjectif> lstCarteObjectif){
        this.modelO.majTable(lstCarteObjectif);
    }

    public void majLabelResume(int nbJoueurMin, int nbJoueurMax, int doubeVoie, int nbVehicule, int nbVehiculeFin, int nbPointCheminLong){
        this.nbJoueurMin = nbJoueurMin;
        this.nbJoueurMax = nbJoueurMax;
        this.doubleVoie = doubeVoie;
        this.nbVehiculeJoueur = nbVehicule;
        this.nbVehiculeFin    = nbVehiculeFin;
        this.nbPointCheminLong = nbPointCheminLong;
        
        this.lblJMin.setText("Joueur Min : " + this.nbJoueurMin);
        this.lblJMax.setText("Joueur Max : " + this.nbJoueurMax);
        this.lblVehicule.setText("Vehicule par joueur : " + this.nbVehiculeJoueur);
        this.lblDoubleMin.setText("Joueur Min pour double voies : " + this.doubleVoie );
        this.lblFin.setText("Nombres de vehicules pour finir la partie : " +  this.nbVehiculeFin); 
        if(this.nbPointCheminLong < 1){
            this.lblCheminLong.setText("Règle du chemin le plus long : Désactivée");
        }else{
            this.lblCheminLong.setText("Points pour le chemin le plus long : " + this.nbPointCheminLong );
        }
    }

    public void majCouleurJoker(Color couleurJoker){
        this.btnCouleurJoker.setBackground(couleurJoker);
    }

    public void supprimerResume(){
        this.nbJoueurMin = 2;
        this.nbJoueurMax = 5;
        this.doubleVoie = 3;
        this.nbVehiculeJoueur = 45;
        this.nbVehiculeFin    = 2;
        this.nbPointCheminLong = -1;

        this.lblJMin.setText("Joueur Min : " + this.nbJoueurMin);
        this.lblJMax.setText("Joueur Max : " + this.nbJoueurMax);
        this.lblVehicule.setText("Vehicule par joueur : " + this.nbVehiculeJoueur);
        this.lblDoubleMin.setText("Joueur Min pour double voies : " + this.doubleVoie );
        this.lblFin.setText("Nombres de vehicules pour finir la partie : " +  this.nbVehiculeFin);

        if(this.nbPointCheminLong < 1){
            this.lblCheminLong.setText("Règle du chemin le plus long : Désactivée");
        }else{
            this.lblCheminLong.setText("Points pour le chemin le plus long : " + this.nbPointCheminLong );
        }
    }

    public void actionPerformed( ActionEvent e){
        if(e.getSource() == this.btnRetour){
            this.ctrl.getIHM().changePanel("panelListeObjectif");
        }
        if(e.getSource() == this.btnCouleurJoker) {
            Color c = JColorChooser.showDialog(this, "Choisissez la couleur Joker", this.btnCouleurJoker.getBackground());
            this.btnCouleurJoker.setBackground(c);
            this.ctrl.majCouleurJoker(c);
        }
        if(e.getSource() == this.btnEnregistrer){

            JFileChooser chooser = new JFileChooser(){
                public void approveSelection() {
                    File f = getSelectedFile();

                    if (f.exists() && getDialogType() == SAVE_DIALOG) {
                        int verif = JOptionPane.showConfirmDialog(this, f.getName() + " existe déjà.\nVoulez-vous le remplacer ?", "Confirmer l'enregistrement", JOptionPane.YES_NO_OPTION);

                        if(verif == JOptionPane.YES_OPTION){
                            super.approveSelection();
                            return;
                        }else{
                            return;
                        }
                    }
                    super.approveSelection();
                }
            };

            FileFilter filtre = new FileNameExtensionFilter("xml files", "xml");
            chooser.setFileFilter(filtre);
            chooser.setAcceptAllFileFilterUsed(false);

            chooser.setSelectedFile(new File("Nouveau.xml"));
            int res = chooser.showSaveDialog(null);

            if(res == JFileChooser.APPROVE_OPTION){
                File file = chooser.getSelectedFile();
                String path = file.getAbsolutePath();

                if (!path .endsWith(".xml")) path += ".xml";

                this.ctrl.ecrireXML(path);
            }
        }
    }
}
