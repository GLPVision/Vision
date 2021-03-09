package test;

import data.Agriculture;
import data.Coordonnees;
import data.Otage;
import data.Scenario;
import moteur.Build;
import moteur.Traitement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import javax.swing.*;

public class TestBuildAgricole {
    private Build build;
    private Traitement traitement;

    @Before
    public void prepareBuild(){
        traitement = new Traitement(false, new Coordonnees(10, 10), new Coordonnees(0, 0), 5, null, null, null, null, new DefaultListModel(), null, null);
        build = new Build(traitement);
    }
    @Test
    public void testImageIcon(){
        assertNotNull(build.getPersonne());
        assertNotNull(build.getIntrusion());
        assertNotNull(build.getMaladie());
        assertNotNull(build.getFeu());
        assertNotNull(build.getInconnue());
        assertNotNull(build.getBle());

        assertTrue(build.getPersonne() instanceof ImageIcon);
        assertTrue(build.getIntrusion() instanceof ImageIcon);
        assertTrue(build.getMaladie() instanceof ImageIcon);
        assertTrue(build.getFeu() instanceof ImageIcon);
        assertTrue(build.getInconnue() instanceof ImageIcon);
        assertTrue(build.getBle() instanceof ImageIcon);
    }

    @Test
    public void testBuild(){
        assertNotNull(build);
        assertTrue(build instanceof Build);

        assertNotNull(build.getScenario());
        assertTrue(build.getScenario() instanceof Agriculture);
        assertNotNull(build.getTaille());
        assertTrue(build.getTaille() instanceof Coordonnees);
        assertEquals(10, build.getTaille().getX());
        assertEquals(10, build.getTaille().getY());
        assertNotNull(build.getList());
        assertNotNull(build.getList());
        assertNotNull(build.isOtage());
        assertEquals(build.isOtage(), false);
    }

    @Test
    public void testBuild_map(){
        build.build_map();
        assertEquals(build.getList().size(), 100);
    }
}