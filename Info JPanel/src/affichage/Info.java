package affichage;

import data.Element;
import moteur.Traitement;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;

public class Info extends JPanel {
    private JLabel repere, nombre, total, text, types, Anomalies, otage, Individus;
    private JList liste;
    private DefaultListModel model;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;
    private JButton next, prec;
    private int diffy;
    private Traitement traitement;
    public Info(Traitement traitement, int diffy) {
        this.traitement = traitement;
        if(traitement.isOtage()){
            this.setBorder(new LineBorder(Color.BLACK, 3));
            this.setBounds(10, 50, 255, 600);
            this.setLayout(null);
            this.setBackground(new Color(204, 190, 121));

            /**
             * Mise en place de l'encadré de texte affichant le nombre d'otages rempli par l'utilisateur au préalable
             */
            otage = new JLabel("    Nombre d'otages :");
            otage.setHorizontalAlignment(SwingConstants.LEFT);
            otage.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
            otage.setBounds(0, 0, 255, 30);
            this.add(otage);

            /**
             * Mise en place de l'encadré de texte affichant les coordonnées de l'individu que l'on a sélectionner sur la carte
             */
            repere = new JLabel("    Coordonnées de l'individu actuel");
            repere.setBackground(SystemColor.activeCaption);
            repere.setHorizontalAlignment(SwingConstants.LEFT);
            repere.setBounds(0, 30, 255, 30);
            repere.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
            this.add(repere);

            /**
             * Mise en place de l'encadré de texte affichant le nombre total d'individu présents sur la carte
             */
            nombre = new JLabel("    Nombre total d'individus :");
            nombre.setHorizontalAlignment(SwingConstants.LEFT);
            nombre.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
            nombre.setBounds(0, 420, 255, 30);
            this.add(nombre);

            /**
             * Mise en place de l'encadré de texte affichant le nombre total d'assaillants présents sur la carte
             */
            total = new JLabel("    Nombre total d'assaillants : ");
            total.setHorizontalAlignment(SwingConstants.LEFT);
            total.setBorder(new MatteBorder(0, 0, 3, 0, (Color) Color.BLACK));
            total.setBounds(0, 450, 255, 30);
            this.add(total);

            /**
             * Mise en place du bouton permettant de sélectionner l'individu précédent sur la carte
             */

            /**
             * Mise en place du bouton permettant de sélectionner l'individu suivant sur la carte
             */

            /**
             * Mise en place du texte au dessus de la liste
             */
            Individus = new JLabel("    Coordonnées des individus repérés");
            Individus.setHorizontalAlignment(SwingConstants.LEFT);
            Individus.setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(0, 0, 0)));
            Individus.setBackground(SystemColor.activeCaption);
            Individus.setBounds(0, 110, 255, 30);
            this.add(Individus);

            /**
             * Mise en place des informations affichées en fonction de la case sélectionnée
             */
            text = new JLabel("");
            text.setBounds(0, 60, 255, 50);
            this.add(text);
            text.setVerticalAlignment(SwingConstants.CENTER);
            text.setHorizontalAlignment(SwingConstants.LEFT);
            text.setBorder(null);

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
            model = new DefaultListModel();
            liste = new JList(model);
            scrollPane.setViewportView(liste);
            liste.setBorder(null);
            liste.setBackground(new Color(204, 190, 121));

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
            this.setBounds(10, 50, 255, 600-diffy);

        }
        else{
            this.setBorder(new MatteBorder(1, 3, 3, 3, (Color) Color.BLACK));
            //this.setBounds(10, 50, 255, 600);
            //contentPane.add(this);
            this.setLayout(null);
            this.setBackground(new Color(204, 190, 121));

            /**
             * Mise en place de l'encadré de texte affichant l'anomalie sélectionnée sur la carte
             */
            repere = new JLabel("    Anomalie sélectionnée");
            repere.setHorizontalAlignment(SwingConstants.LEFT);
            repere.setBounds(0, 0, 255, 30);
            repere.setBorder(new MatteBorder(3, 0, 3, 0, (Color) new Color(0, 0, 0)));
            this.add(repere);

            /**
             * Mise en place de l'encadré de texte affichant le nombre d'anomalies de chaque catégorie présentes sur la carte
             */
            nombre = new JLabel("    Nombre d'anomalies");
            nombre.setHorizontalAlignment(SwingConstants.LEFT);
            nombre.setBorder(new MatteBorder(0, 0, 3, 0, (Color) new Color(0, 0, 0)));
            nombre.setBounds(0, 340, 255, 30);
            this.add(nombre);

            /**
             * Mise en place de l'encadré de texte affichant le nombre total d'anomalies présentes sur la carte
             */
            total = new JLabel("    Total : "); //
            total.setHorizontalAlignment(SwingConstants.LEFT);
            total.setBorder(new MatteBorder(3, 0, 3, 0, (Color) Color.BLACK));
            total.setBounds(0, 450, 255, 30);
            this.add(total);

            /**
             * Mise en place du bouton permettant de sélectionner l'anomalie précédente sur la carte
             */

            /**
             * Mise en place du bouton permettant de sélectionner l'anomalie suivante sur la carte
             */

            /**
             * Mise en place des informations de coordonnées affichées en fonction de la case sélectionnée
             */
            text = new JLabel(""); //lister les coordonnées
            text.setVerticalAlignment(SwingConstants.CENTER);
            text.setHorizontalAlignment(SwingConstants.LEFT);
            text.setBounds(0, 30, 255, 50);
            text.setBorder(null);
            this.add(text);

            /**
             * Mise en place des informations de types d'anomalie affichées en fonction de la case sélectionnée
             */
            types = new JLabel(); //
            types.setVerticalAlignment(SwingConstants.CENTER);
            types.setHorizontalAlignment(SwingConstants.LEFT);
            types.setBorder(null);
            types.setBounds(0, 370, 255, 80);
            this.add(types);

            /**
             * Mise en place du texte au dessus de la liste
             */
            Anomalies = new JLabel("    Liste des anomalies présentes");
            Anomalies.setHorizontalAlignment(SwingConstants.LEFT);
            Anomalies.setBorder(new MatteBorder(3, 0, 0, 0, (Color) new Color(0, 0, 0)));
            Anomalies.setBounds(0, 80, 255, 30);
            this.add(Anomalies);

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
            model = new DefaultListModel();
            liste = new JList(model);
            scrollPane.setViewportView(liste);
            liste.setBorder(null);
            liste.setBackground(new Color(204, 190, 121));

            buttonPanel = new JPanel();
            buttonPanel.setBorder(new MatteBorder(0, 3, 3, 3, (Color) new Color(0, 0, 0)));
            buttonPanel.setBounds(0, 480, 255, 120-diffy);
            buttonPanel.setBackground(new Color (204, 190, 121));
            SpringLayout sl_panel = new SpringLayout();
            buttonPanel.setLayout(sl_panel);
            prec = new JButton("Anomalie précédente");
            prec.setBorder(null);
            prec.setBackground(SystemColor.activeCaption);
            next = new JButton("Anomalie suivante");
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
            this.setBounds(10, 50, 255, 600-diffy);
        }




    }
    public void majGUI(){
        if (traitement.isOtage()){
            nombre.setText("    Nombre total d'individus : " + traitement.getNbTotal().size());
            otage.setText("    Nombre d'otages : " + traitement.getNbOtage());
            total.setText("    Nombre total d'assaillants : " + traitement.getNbAssaillant());
            for(int i=0 ; i<traitement.getEntoure().size() ; i++){
                Element e = traitement.getEntoure().get(i);
                model.addElement("   " + e.getDesc() + " en : " + e.getCoordonnees().getX() + ", " + e.getCoordonnees().getY());
            }
        }
        else {
            total.setText("   Total : " + (traitement.getInconnue().size()+traitement.getFeu().size()+traitement.getMaladie().size()+traitement.getIntrusion().size()));
            types.setText("<html> &nbsp &#160 Nombre de feux : " + traitement.getFeu().size() + "<br/>" +
                    " &nbsp &#160 Nombre d'intrusions : " + traitement.getIntrusion().size() + "<br/>" +
                    " &nbsp &#160 Nombre de maladies : " + traitement.getMaladie().size() + "<br/>" +
                    " &nbsp &#160 Nombre d'anomalies inconnues : " + traitement.getInconnue().size() + "</html>");
            for(int i=0 ; i<traitement.getEntoure().size() ; i++){
                Element e = traitement.getEntoure().get(i);
                model.addElement("   " + e.getDesc() + " en : " + e.getCoordonnees().getX() + ", " + e.getCoordonnees().getY());
            }
        }
    }

    public JLabel getRepere() {
        return repere;
    }

    public JLabel getNombre() {
        return nombre;
    }

    public JLabel getTotal() {
        return total;
    }

    public JLabel getText() {
        return text;
    }

    public JLabel getTypes() {
        return types;
    }

    public JLabel getAnomalies() {
        return Anomalies;
    }

    public JList getListe() {
        return liste;
    }

    public DefaultListModel getModel() {
        return model;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public JButton getNext() {
        return next;
    }

    public JButton getPrec() {
        return prec;
    }

    public int getDiffy() {
        return diffy;
    }
}
