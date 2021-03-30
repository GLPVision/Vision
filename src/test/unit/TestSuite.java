package test.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Classe test
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 * @version 1
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({TestTraitementOtage.class, TestTraitementAgricole.class})
/**
 * Classe qui lance tous les tests
 */
public class TestSuite {
}