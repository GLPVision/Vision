package test;

import data.Coordonnees;
import data.Otage;
import moteur.Build;
import moteur.Traitement;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

/**
 * Classe test build otage
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 1
 */
public class TestBuildOtage {
    /**
     * Build
     */
    private Build build;
    /**
     * Traitement
     */
    private Traitement traitement;

    /**
     * preparation
     */
    @Before
    public void prepareBuild(){
        traitement = new Traitement(new Coordonnees(10, 10), new Coordonnees(0, 0), 5);
        build = new Build(traitement);
    }

    /**
     * test des images
     */
    @Test
    public void testImageIcon(){
        assertNotNull(build.getPersonne());
        assertNotNull(build.getBat());

        assertTrue(build.getPersonne() instanceof ImageIcon);
        assertTrue(build.getBat() instanceof ImageIcon);
    }

    /**
     * test build
     */
    @Test
    public void testBuild(){
        assertNotNull(build);
        assertTrue(build instanceof Build);

        assertNotNull(build.getScenario());
        assertTrue(build.getScenario() instanceof Otage);
        assertNotNull(build.getTaille());
        assertTrue(build.getTaille() instanceof Coordonnees);
        assertEquals(10, build.getTaille().getX());
        assertEquals(10, build.getTaille().getY());
        assertNotNull(build.getList());
        assertNotNull(build.getList());
        assertNotNull(build.isOtage());
        assertEquals(build.isOtage(), true);
    }

    /**
     * test build_map
     */
    @Test
    public void testBuild_map(){
        build.build_map();
        assertEquals(build.getList().size(), 100);
    }
}
