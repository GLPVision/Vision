package affichage;

import data.Element;
import moteur.Traitement;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Info extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel txt_anomalie, anomalie, txt_liste_anomalie, txt_nb_anomalie, nb_anomalie, total_anomalie;
	private JLabel nb_otage, txt_individu, individu, txt_liste_individu, total_individu, total_assaillant;
    private JTextArea liste;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private JButton next, prec;
    private int diffy;
    private Traitement traitement;
    
    public Info(Traitement traitement, int diffy) {
        this.traitement = traitement;
        if(traitement.isOtage()){
            /**
             * Mise en place de l'encadré de texte affichant le nombre d'otages rempli par l'utilisateur au préalable
             */
            nb_otage = new JLabel("    Nombre d'otages :");
            nb_otage.setHorizontalAlignment(SwingConstants.LEFT);
            nb_otage.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
            nb_otage.setBounds(0, 0, 255, 30);
            this.add(nb_otage);

            /**
             * Mise en place de l'encadré de texte affichant les coordonnées de l'individu que l'on a sélectionner sur la carte
             */
            txt_individu = new JLabel("    Coordonnées de l'individu actuel");
            txt_individu.setBackground(SystemColor.activeCaption);
            txt_individu.setHorizontalAlignment(SwingConstants.LEFT);
            txt_individu.setBounds(0, 30, 255, 30);
            txt_individu.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
            this.add(txt_individu);

            /**
             * Mise en place des informations affichées en fonction de la case sélectionnée
             */
            individu = new JLabel("");
            individu.setBounds(0, 60, 255, 50);
            this.add(individu);
            individu.setVerticalAlignment(SwingConstants.CENTER);
            individu.setHorizontalAlignment(SwingConstants.LEFT);
            individu.setBorder(null);

            /**
             * Mise en place du texte au dessus de la liste
             */
            txt_liste_individu = new JLabel("    Coordonnées des individus repérés");
            txt_liste_individu.setHorizontalAlignment(SwingConstants.LEFT);
            txt_liste_individu.setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(0, 0, 0)));
            txt_liste_individu.setBackground(SystemColor.activeCaption);
            txt_liste_individu.setBounds(0, 110, 255, 30);
            this.add(txt_liste_individu);

            /**
             * Mise en place d'un système pour parcourir la liste facilement si celle ci est trop longue
             */
            scrollPane = new JScrollPane();
            scrollPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
            scrollPane.setBounds(0, 140, 255, 280);
            this.add(scrollPane);

            /**
             * Mise en place de la liste contenant les informations sur les individus de la carte
             */
            
            liste = new JTextArea();
            liste.setEditable(false);
            scrollPane.setViewportView(liste);
            liste.setBorder(null);
            liste.setBackground(new Color(204, 190, 121));
            liste.setFont(liste.getFont().deriveFont(Font.BOLD, liste.getFont().getSize()));

            /**
             * Mise en place de l'encadré de texte affichant le nombre total d'individu présents sur la carte
             */
            total_individu = new JLabel("    Nombre total d'individus :");
            total_individu.setHorizontalAlignment(SwingConstants.LEFT);
            total_individu.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
            total_individu.setBounds(0, 420, 255, 30);
            this.add(total_individu);

            /**
             * Mise en place de l'encadré de texte affichant le nombre total d'assaillants présents sur la carte
             */
            total_assaillant = new JLabel("    Nombre total d'assaillants : ");
            total_assaillant.setHorizontalAlignment(SwingConstants.LEFT);
            total_assaillant.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
            total_assaillant.setBounds(0, 450, 255, 30);
            this.add(total_assaillant);
        }
        else{
            /**
             * Mise en place de l'encadré de texte affichant l'anomalie sélectionnée sur la carte
             */
            txt_anomalie = new JLabel("    Anomalie sélectionnée");
            txt_anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            txt_anomalie.setBounds(0, 0, 255, 30);
            txt_anomalie.setBorder(new MatteBorder(3, 0, 3, 0, (Color) new Color(0, 0, 0)));
            this.add(txt_anomalie);

            /**
             * Mise en place des informations de coordonnées affichées en fonction de la case sélectionnée
             */
            anomalie = new JLabel(""); //lister les coordonnées
            anomalie.setVerticalAlignment(SwingConstants.CENTER);
            anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            anomalie.setBounds(0, 30, 255, 50);
            anomalie.setBorder(null);
            this.add(anomalie);

            /**
             * Mise en place du texte au dessus de la liste
             */
            txt_liste_anomalie = new JLabel("    Liste des anomalies présentes");
            txt_liste_anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            txt_liste_anomalie.setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(0, 0, 0)));
            txt_liste_anomalie.setBounds(0, 80, 255, 30);
            this.add(txt_liste_anomalie);

            /**
             * Mise en place d'un système pour parcourir la liste facilement si celle ci est trop longue
             */
            scrollPane = new JScrollPane();
            scrollPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
            scrollPane.setBounds(0, 108, 255, 235);
            this.add(scrollPane);

            /**
             * Mise en place de la liste contenant les informations sur les anomalies de la carte
             */
            
            liste = new JTextArea();
            liste.setEditable(false);
            scrollPane.setViewportView(liste);
            liste.setBorder(null);
            liste.setBackground(new Color(204, 190, 121));
            liste.setFont(liste.getFont().deriveFont(Font.BOLD, liste.getFont().getSize()));

            /**
             * Mise en place de l'encadré de texte affichant le nombre d'anomalies de chaque catégorie présentes sur la carte
             */
            txt_nb_anomalie = new JLabel("    Nombre d'anomalies");
            txt_nb_anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            txt_nb_anomalie.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
            txt_nb_anomalie.setBounds(0, 340, 255, 30);
            this.add(txt_nb_anomalie);

            /**
             * Mise en place des informations de types d'anomalie affichées en fonction de la case sélectionnée
             */
            nb_anomalie = new JLabel(); //
            nb_anomalie.setVerticalAlignment(SwingConstants.CENTER);
            nb_anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            nb_anomalie.setBorder(null);
            nb_anomalie.setBounds(0, 370, 255, 80);
            this.add(nb_anomalie);

            /**
             * Mise en place de l'encadré de texte affichant le nombre total d'anomalies présentes sur la carte
             */
            total_anomalie = new JLabel("    Total : "); //
            total_anomalie.setHorizontalAlignment(SwingConstants.LEFT);
            total_anomalie.setBorder(new MatteBorder(3, 0, 3, 0, (Color) Color.BLACK));
            total_anomalie.setBounds(0, 450, 255, 30);
            this.add(total_anomalie);
        }
        this.setBounds(10, 50, 255, 600-diffy);
        this.setBorder(new MatteBorder(3, 3, 3, 3, (Color) Color.BLACK));
        this.setLayout(null);
        this.setBackground(new Color(204, 190, 121));
        buttonPanel = new JPanel();
        buttonPanel.setBorder(new MatteBorder(0, 3, 3, 3, (Color) new Color(0, 0, 0)));
        buttonPanel.setBounds(0, 480, 255, 120-diffy);
        buttonPanel.setBackground(new Color(204, 190, 121));
        SpringLayout sl_panel = new SpringLayout();
        buttonPanel.setLayout(sl_panel);
        prec = new JButton("Individu précédent");
        prec.setBorder(null);
        prec.setBackground(SystemColor.activeCaption);
        next = new JButton("Individu suivant");
        next.setBorder(null);
        next.setBackground(SystemColor.activeCaption);
        sl_panel.putConstraint(SpringLayout.SOUTH, next, -10, SpringLayout.SOUTH, buttonPanel);
        sl_panel.putConstraint(SpringLayout.NORTH, next, 5, SpringLayout.VERTICAL_CENTER, buttonPanel);
        sl_panel.putConstraint(SpringLayout.SOUTH, prec, -5, SpringLayout.VERTICAL_CENTER, buttonPanel);
        sl_panel.putConstraint(SpringLayout.NORTH, prec, 10, SpringLayout.NORTH, buttonPanel);
        sl_panel.putConstraint(SpringLayout.WEST, prec, 10, SpringLayout.WEST, buttonPanel);
        sl_panel.putConstraint(SpringLayout.EAST, prec, -10, SpringLayout.EAST, buttonPanel);
        sl_panel.putConstraint(SpringLayout.WEST, next, 10, SpringLayout.WEST, buttonPanel);
        sl_panel.putConstraint(SpringLayout.EAST, next, -10, SpringLayout.EAST, buttonPanel);
        buttonPanel.add(prec);
        buttonPanel.add(next);
        this.add(buttonPanel);
    }
    public void majGUI(){
        if (traitement.isOtage()){
            individu.setText("    Individu en : " + traitement.getSelected().getCoordonnees().getX() + ", " + traitement.getSelected().getCoordonnees().getY());
            total_individu.setText("    Nombre total d'individus : " + traitement.getNbTotal().size());
            nb_otage.setText("    Nombre d'otages : " + traitement.getNbOtage());
            total_assaillant.setText("    Nombre total d'assaillants : " + traitement.getNbAssaillant());
            for(int i=0 ; i<traitement.getEntoure().size() ; i++){
                Element e = traitement.getEntoure().get(i);
                liste.setText(liste.getText() + "   Individu en : " + (e.getCoordonnees().getX()+traitement.getDebut().getX()) + ", " + (e.getCoordonnees().getY()+traitement.getDebut().getY()) + "\n");
            }
        }
        else {
            anomalie.setText("    " + traitement.getSelected().getDesc() + " en : " + traitement.getSelected().getCoordonnees().getX() + ", " + traitement.getSelected().getCoordonnees().getY());
            total_anomalie.setText("   Total : " + (traitement.getInconnue().size()+traitement.getFeu().size()+traitement.getMaladie().size()+traitement.getIntrusion().size()));
            nb_anomalie.setText("<html> &nbsp &#160 Nombre de feux : " + traitement.getFeu().size() + "<br/>" +
                    " &nbsp &#160 Nombre d'intrusions : " + traitement.getIntrusion().size() + "<br/>" +
                    " &nbsp &#160 Nombre de maladies : " + traitement.getMaladie().size() + "<br/>" +
                    " &nbsp &#160 Nombre d'anomalies inconnues : " + traitement.getInconnue().size() + "</html>");
            for(int i=0 ; i<traitement.getEntoure().size() ; i++){
                Element e = traitement.getEntoure().get(i);
                liste.setText(liste.getText() + "   " + e.getDesc() + " en : " + (e.getCoordonnees().getX()+traitement.getDebut().getX()) + ", " + (e.getCoordonnees().getY()+traitement.getDebut().getX()) + "\n");
            }
        }
    }
}
