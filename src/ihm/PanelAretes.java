package src.ihm;

import src.Controleur;
import src.ihm.grilles.GrillesAreteModel;
import src.ihm.renderer.ColorCellRendererArete;
import src.metier.Arete;
import src.metier.FonctionAux;
import src.metier.Noeud;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.Vector;

public class PanelAretes extends JPanel implements ActionListener,AdjustmentListener {

    private GrillesAreteModel model;

    private JTable tabAretes;

    private JPanel panelValidation, panelArete, panelAreteBtn, panelRemplissage, panelTable, panelClear;

    private JButton btnRetour, btnSuivant, btnAjoutArete, btnSupprArete, btnClear, btnCouleurJoker;
    private JComboBox<String> listNoeud1, listNoeud2;
    private JScrollBar  sbLongueur, sbHauteur, sbEspacement;

    private Color couleurJoker;

    private JTextField txtLongueurArete;

    private Controleur ctrl;

    private JButton btnType;


    public PanelAretes(Controleur ctrl) {

        // création des composants
        this.ctrl = ctrl;
        
        this.panelTable = new JPanel();
        this.panelValidation = new JPanel();
        this.btnRetour = new JButton("Retour");
        this.btnSuivant = new JButton("Suivant");

        this.model = new GrillesAreteModel(this.ctrl);
        this.tabAretes = new JTable(this.model);
       
        this.tabAretes.setFillsViewportHeight(true);
        this.tabAretes.setEnabled(true);

        this.tabAretes.setDefaultRenderer(Color.class, new ColorCellRendererArete());
        this.tabAretes.getColumnModel().getColumn(3).setCellRenderer(new ColorCellRendererArete());


        JScrollPane spTabAretes = new JScrollPane(this.tabAretes);

        this.sbEspacement = new JScrollBar(JScrollBar.HORIZONTAL, 15, 1, 5, 25);
        this.sbLongueur = new JScrollBar(JScrollBar.HORIZONTAL, 20, 1, 10, 40);
        this.sbHauteur = new JScrollBar(JScrollBar.HORIZONTAL, 10, 1, 5, 20);

        this.btnClear = new JButton("Effacer");
        this.btnAjoutArete = new JButton("Ajouter +");
        this.btnSupprArete = new JButton("Supprimer -");
        this.btnCouleurJoker = new JButton("Couleur Joker");

        Vector<String> vNoeud = new Vector<String>(this.ctrl.getNomNoeuds());

        this.listNoeud1 = new JComboBox<>(vNoeud);
        this.listNoeud2 = new JComboBox<>(vNoeud);
        this.btnType = new JButton("Type");
        this.txtLongueurArete = new JTextField();

        this.listNoeud1.setSelectedItem(null);
        this.listNoeud2.setSelectedItem(null);

        // création des layouts
        this.setLayout(new BorderLayout());
        this.panelTable       = new JPanel(new BorderLayout());
        this.panelArete       = new JPanel(new BorderLayout());
        this.panelAreteBtn    = new JPanel(new GridLayout(2,2));
        this.panelValidation  = new JPanel(new GridLayout(4,3));
        this.panelRemplissage = new JPanel(new GridLayout(2,4));
        this.panelClear       = new JPanel(new GridLayout(2,3));
        JPanel panelCouleur   = new JPanel();

        // activation des composants
        this.btnAjoutArete.addActionListener(this);
        this.btnSupprArete.addActionListener(this);
        this.btnClear.addActionListener(this);
        this.btnSuivant.addActionListener(this);
        this.btnRetour.addActionListener(this);
        this.btnCouleurJoker.addActionListener(this);
        this.sbEspacement.addAdjustmentListener(this);
        this.sbLongueur.addAdjustmentListener(this);
        this.sbHauteur.addAdjustmentListener(this);


        // ajout des composants
        panelCouleur.add(new JLabel("Couleur Joker :", SwingConstants.CENTER));
        panelCouleur.add(this.btnCouleurJoker);
        
        this.panelRemplissage.add(new JLabel("Noeud 1 :", SwingConstants.CENTER));
        this.panelRemplissage.add(new JLabel("Noeud 2 :", SwingConstants.CENTER));
        this.panelRemplissage.add(new JLabel("Longueur :", SwingConstants.CENTER));
        this.panelRemplissage.add(new JLabel("Type :", SwingConstants.CENTER));
        this.panelRemplissage.add(this.listNoeud1);
        this.panelRemplissage.add(this.listNoeud2);
        this.panelRemplissage.add(this.txtLongueurArete);
        this.panelRemplissage.add(this.btnType);

        this.panelClear.add(new JLabel());
        this.panelClear.add(this.btnClear);
        this.panelClear.add(new JLabel());
        this.panelClear.add(new JLabel());
        this.panelClear.add(new JLabel());
        this.panelClear.add(new JLabel());
        
        this.panelArete.add(this.panelClear, BorderLayout.SOUTH);
        this.panelArete.add(this.panelRemplissage, BorderLayout.CENTER);

        this.panelAreteBtn.add(this.btnAjoutArete);
        this.panelAreteBtn.add(this.btnSupprArete);
        this.panelAreteBtn.add(new JLabel());
        
        this.panelTable.add(this.panelArete, BorderLayout.NORTH);
        this.panelTable.add(spTabAretes, BorderLayout.CENTER);
        this.panelTable.add(this.panelAreteBtn, BorderLayout.SOUTH);
        
        this.panelValidation.add(new JLabel("Hauteur Vehicule", SwingConstants.CENTER));
        this.panelValidation.add(new JLabel("Longueur Vehicule", SwingConstants.CENTER));
        this.panelValidation.add(new JLabel("Espacement", SwingConstants.CENTER));
        this.panelValidation.add(this.sbHauteur);
        this.panelValidation.add(this.sbLongueur);
        this.panelValidation.add(this.sbEspacement);
        this.panelValidation.add(new JLabel());
        this.panelValidation.add(new JLabel());
        this.panelValidation.add(new JLabel());
        this.panelValidation.add(this.btnRetour);
        this.panelValidation.add(new JLabel());
        this.panelValidation.add(this.btnSuivant);

        this.add(new JLabel("Création des arêtes", SwingConstants.CENTER), BorderLayout.NORTH);
        this.add(panelCouleur, BorderLayout.NORTH);
        this.add(this.panelTable, BorderLayout.CENTER);
        this.add(this.panelValidation, BorderLayout.SOUTH);

        this.btnType.addActionListener(this);

        this.btnRetour.addActionListener(this);
        this.btnSuivant.addActionListener(this);

        this.listNoeud1.addActionListener(this);
        this.listNoeud2.addActionListener(this);
    }

    // fonction qui permet d effacer le contenu des composant du panelRemplissage
    private void removePanelRemplissage(){
        this.listNoeud1.removeActionListener(this);
        this.listNoeud2.removeActionListener(this);

        this.listNoeud1.setSelectedItem(null);
        this.listNoeud2.setSelectedItem(null);
        this.txtLongueurArete.setText("");

        this.listNoeud1.addActionListener(this);
        this.listNoeud2.addActionListener(this);
    }

    //renvoie une liste de noms de noeud sauf celui passé en parametre
    public ArrayList<String> getNomNoeuds(String nomNoeud){
        ArrayList<String> lstNomNoeuds = this.ctrl.getNomNoeuds();
        lstNomNoeuds.remove(nomNoeud);
        return lstNomNoeuds;
    }

    public void majNoeud(ArrayList<Noeud> lstNoeuds) {
        this.listNoeud1.removeActionListener(this);
        this.listNoeud2.removeActionListener(this);

        this.listNoeud1.removeAllItems();
        this.listNoeud2.removeAllItems();

        for (Noeud noeud : lstNoeuds) {
            this.listNoeud1.addItem(noeud.getNom());
        }

        this.listNoeud1.addActionListener(this);
        this.listNoeud2.addActionListener(this);
    }

    public void majArete(ArrayList<Arete> lstAretes) {
        this.model.majTable(lstAretes);
    }

    public void majCouleurJoker(Color couleur) {
        this.btnCouleurJoker.setBackground(couleur);
    }
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.btnClear){
            this.removePanelRemplissage();
        }

        if(e.getSource() == this.btnCouleurJoker) {
            Color c = JColorChooser.showDialog(this, "Choisissez la couleur Joker", this.btnCouleurJoker.getBackground());
            this.btnCouleurJoker.setBackground(c);
            this.ctrl.majCouleurJoker(c);
        }

        if(e.getSource() == this.btnAjoutArete){
            if(this.listNoeud1.getSelectedItem() != null    &&
               this.listNoeud2.getSelectedItem() != null    &&
               FonctionAux.isInteger(this.txtLongueurArete.getText())  &&
               this.btnType.getBackground() != null
              )
            {
                int nbdoublons = 0;
                for(int i=0; i<this.tabAretes.getRowCount(); i++){
                    if((this.tabAretes.getValueAt(i, 0).equals((String)this.listNoeud1.getSelectedItem()) &&
                       this.tabAretes.getValueAt(i, 1).equals((String)this.listNoeud2.getSelectedItem())) || (
                       this.tabAretes.getValueAt(i, 0).equals((String)this.listNoeud2.getSelectedItem()) &&
                       this.tabAretes.getValueAt(i, 1).equals((String)this.listNoeud1.getSelectedItem()) )){
                        nbdoublons++;
                    }
                }
                if(nbdoublons<2){

                    this.ctrl.ajouterArete(
                        this.ctrl.getLstNoeuds().get(this.listNoeud1.getSelectedIndex()),
                        this.ctrl.getLstNoeuds().get(this.listNoeud2.getSelectedIndex()),
                        Math.abs(Integer.parseInt(this.txtLongueurArete.getText())),
                        this.btnType.getBackground()
                    );
                    this.removePanelRemplissage();
                    this.ctrl.majArete();
                }
            }

            if(!FonctionAux.isInteger(this.txtLongueurArete.getText())){
                this.txtLongueurArete.setText("");
            }
        }

        if(e.getSource() == this.btnSupprArete){
            if(this.tabAretes.getSelectedRow() != -1){
                this.ctrl.supprimerArete(this.ctrl.getLstAretes().get(this.tabAretes.getSelectedRow()));
            }
        }

        if ( e.getSource() == this.btnRetour) {
            this.ctrl.getIHM().changePanel("panelRegleJeu");
        }

        if ( e.getSource() == this.btnSuivant) {
            this.ctrl.getIHM().changePanel("panelVehicule");
        }

        if(e.getSource() == this.listNoeud1){
            
            if (this.listNoeud1.getSelectedItem() == this.listNoeud2.getSelectedItem()) {
                this.listNoeud2.setSelectedItem(null);
            }
        }

        if(e.getSource() == this.listNoeud2){
            
            if (this.listNoeud2.getSelectedItem() == this.listNoeud1.getSelectedItem()) {
                this.listNoeud1.setSelectedItem(null);
            }
        }

        if (e.getSource() == this.btnType) {
            //choose color and set it to the backgrounf of the button
            Color c = JColorChooser.showDialog(this, "Choose a color", this.btnType.getBackground());
            this.btnType.setBackground(c);
            
        }

    }

    public void adjustmentValueChanged ( AdjustmentEvent e )
	{
        this.ctrl.getMetier().setHauteurVehicule(this.sbHauteur.getValue());
        this.ctrl.getMetier().setLongueurVehicule(this.sbLongueur.getValue());
        this.ctrl.getMetier().setEspacementVehicule(((double)this.sbEspacement.getValue())/10);
		this.ctrl.majIHM();
	}
    
}