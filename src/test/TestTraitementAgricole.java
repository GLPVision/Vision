package test;

import data.Coordonnees;
import moteur.Traitement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Classe test traitement agricole
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 1
 */
public class TestTraitementAgricole {
    /**
     * Traitement
     */
    private Traitement traitement;

    /**
     * prepartaion
     */
    @Before
    public void prepareTraitement(){
        traitement = new Traitement(false, new Coordonnees(10, 10), new Coordonnees(0, 0), 0);
    }

    /**
     * test des images
     */
    @Test
    public void testImageIcon(){
        //assertNotNull(traitement.getCercle());
        //assertTrue(traitement.getCercle() instanceof ImageIcon);
    }

    /**
     * test traitement
     */
    @Test
    public void testTraitement(){
        assertNotNull(traitement.isOtage());
        assertEquals(traitement.isOtage(), false);
        assertNotNull(traitement.getTaille());
        assertTrue(traitement.getTaille() instanceof Coordonnees);
        assertEquals(traitement.getTaille().getX(), 10);
        assertEquals(traitement.getTaille().getY(), 10);
        assertNotNull(traitement.getDebut());
        assertTrue(traitement.getDebut() instanceof Coordonnees);
        assertEquals(traitement.getDebut().getX(), 0);
        assertEquals(traitement.getDebut().getY(), 0);
    }

    /**
     * test supp
     */
    @Test
    public void testSupp(){
        //traitement.supp();
        assertNotNull(traitement.getInconnue());
        assertEquals(traitement.getInconnue(), 0);
        assertNotNull(traitement.getFeu());
        assertEquals(traitement.getFeu(), 0);
        assertNotNull(traitement.getMaladie());
        assertEquals(traitement.getMaladie(), 0);
        assertNotNull(traitement.getIntrusion());
        assertEquals(traitement.getIntrusion(), 0);
        assertNull(traitement.getCarte());
        assertNull(traitement.getScenario());
    }

}
