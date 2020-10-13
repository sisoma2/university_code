package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * The Class TestSuite.
 * Groups the different unit tests created
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CheckTest.class,RulesTest.class})
public class TestSuite {}
