package at.fhj.swd.ue5;

import at.fhj.swd.dao.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class EntityTest {
    static Persistence P = Persistence.INST;
    static final String persistenceUnitName = "ue5";

    static int beerId;

    @BeforeClass
    public static void setup() {
        P.create(persistenceUnitName);
    }

    @AfterClass
    public static void teardown() {
        P.close();
    }

    @Test
    public void createBeer() {
        Beer beer = Beer.factory().setName("Puntigamer").setBeerType(BeerType.ALE).create();
        assertNotNull(beer);
        beerId = beer.getId();
    }

    @Test
    public void createBundles() {
        Beer beer = P.find(Beer.class, beerId);
        Bundle bundle = Bundle.factory().setName("B1").setBeer(beer).create();
        assertNotNull(bundle);
        assertEquals(1, beer.getBundles().size());
        assertEquals(bundle, beer.getBundles().get(0));
        Bundle.factory().setName("B2").setBeer(beer).create();
        assertEquals(2, beer.getBundles().size());
        beer = P.find(Beer.class, beerId);
        assertEquals(2, beer.getBundles().size());
    }

    @Test
    public void removeBundle() {
        Beer beer = P.find(Beer.class, beerId);
        assertNotNull(beer);
        assertEquals(2, beer.getBundles().size());
        Bundle bundle = beer.getBundles().get(0);
        int bundleId = bundle.getBundleId();
        P.remove(bundle);
        bundle = P.find(Bundle.class, bundleId);
        assertNull(bundle);
        beer = P.find(Beer.class, beerId);
        assertEquals(1, beer.getBundles().size());
    }
}