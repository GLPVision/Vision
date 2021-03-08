package test;

import data.Agriculture;
import data.Coordonnees;
import data.Otage;
import moteur.Traitement;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestTraitementAgricole {
    private Traitement traitement;

    @Before
    public void prepareTraitement(){
        traitement = new Traitement(false, new Coordonnees(10, 10), new Coordonnees(0, 0), 0, null, null, null, null, null, null, null);
    }

    @Test
    public void testImageIcon(){
        assertNotNull(traitement.getCercle());
        assertTrue(traitement.getCercle() instanceof ImageIcon);
    }
    @Test
    public void testBuild(){
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

    @Test
    public void testSupp() throws InterruptedException {
        traitement.supp();
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
