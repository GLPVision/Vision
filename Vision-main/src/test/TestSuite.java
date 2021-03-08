package test;

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestBuildOtage.class, TestBuildAgricole.class, TestTraitementOtage.class, TestTraitementAgricole.class})
public class TestSuite {
}
