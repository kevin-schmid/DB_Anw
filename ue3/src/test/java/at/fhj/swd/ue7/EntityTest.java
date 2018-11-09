package at.fhj.swd.ue7;

import at.fhj.swd.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class EntityTest {
    static Persistence P = Persistence.INST;
    static final String persistenceUnitName = "ue7";

    @BeforeClass
    public static void setup() {
        P.create(persistenceUnitName);
    }

    @AfterClass
    public static void teardown() {
        P.close();
    }

    @Test
    public void verifyInheritance() {
        Drink wine = Wine.factory().setName("wine").setColour(WineColour.RED).create();
        Drink beer = Beer.factory().setName("beer").setBeerType(BeerType.ALE).create();
        assertTrue(wine instanceof Wine);
        assertTrue(beer instanceof Beer);
    }
}
