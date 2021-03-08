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

public class TestTraitementOtage {
    private Traitement traitement;

    @Before
    public void prepareTraitement(){
        traitement = new Traitement(true, new Coordonnees(10, 10), new Coordonnees(0, 0), 5, null, null, null, null, null, null, null);
    }

    @Test
    public void testImageIcon(){
        assertNotNull(traitement.getCercle());
        assertTrue(traitement.getCercle() instanceof ImageIcon);
    }
    @Test
    public void testBuild(){
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

    @Test
    public void testSupp() throws InterruptedException {
        traitement.supp();
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
