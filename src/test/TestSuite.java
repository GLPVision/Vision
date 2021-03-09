package test;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

/**
 * Classe test
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 1
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TestBuildOtage.class, TestBuildAgricole.class, TestTraitementOtage.class, TestTraitementAgricole.class})
/**
 * Classe qui lance tous les tests
 */
public class TestSuite {
}
