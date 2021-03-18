package test;

//import data.Agriculture;
import data.Coordonnees;
//import data.Otage;
import moteur.Traitement;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Classe test traitement otage
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 1
 */
public class TestTraitementOtage {
    /**
     * Traitement
     */
    private Traitement traitement;

    /**
     * preparation
     */
    @Before
    public void prepareTraitement(){
        traitement = new Traitement(true, new Coordonnees(10, 10), new Coordonnees(0, 0), 5, null, null, null, null, null, null, null);
    }

    /**
     * test des images
     */
    @Test
    public void testImageIcon(){
        assertNotNull(traitement.getCercle());
        assertTrue(traitement.getCercle() instanceof ImageIcon);
    }

    /**
     * test traitement
     */
    @Test
    public void testTraitement(){
        assertNotNull(traitement.isOtage());
        assertEquals(traitement.isOtage(), true);
        assertNotNull(traitement.getTaille());
        assertTrue(traitement.getTaille() instanceof Coordonnees);
        assertEquals(traitement.getTaille().getX(), 10);
        assertEquals(traitement.getTaille().getY(), 10);
        assertNotNull(traitement.getDebut());
        assertTrue(traitement.getDebut() instanceof Coordonnees);
        assertEquals(traitement.getDebut().getX(), 0);
        assertEquals(traitement.getDebut().getY(), 0);
        assertEquals(traitement.getNbOtage(), 5);
    }

    /**
     * test supp
     */
    @Test
    public void testSupp(){
        //traitement.supp();
        assertNotNull(traitement.getNbOtage());
        assertEquals(traitement.getNbOtage(), 0);
        assertNotNull(traitement.getNbAssaillant());
        assertEquals(traitement.getNbAssaillant(), 0);
        assertNotNull(traitement.getNbTotal());
        assertEquals(traitement.getNbTotal(), 0);
        assertNull(traitement.getCarte());
        assertNull(traitement.getScenario());
    }

}
