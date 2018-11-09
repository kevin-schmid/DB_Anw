package at.fhj.swd.ue4;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class EntityTest {

    static EntityManagerFactory factory;
    static EntityManager manager;
    static EntityTransaction transaction;

    static final String persistenceUnitName = "ue4";

    static int beerId;
    static int matId;

    @BeforeClass
    public static void setup() {
        factory = Persistence.createEntityManagerFactory(persistenceUnitName);
        assertNotNull(factory);
        manager = factory.createEntityManager();
        assertNotNull(manager);
        transaction = manager.getTransaction();
    }

    @AfterClass
    public static void teardown() {
        if (manager == null)
            return;

        manager.close();
        factory.close();
    }


    @Test
    public void createBeer() {
        transaction.begin();
        Beer punti = new Beer("Puntigamer", BeerType.ALE);
        assertNotNull(punti);
        manager.persist(punti);
        transaction.commit();
        beerId = punti.getId();
        assertEquals(1000, punti.getId());

    }

    @Test
    public void createMaterial() {
        transaction.begin();
        Recipe mat = new Recipe("Hemp");
        assertNotNull(mat);
        manager.persist(mat);
        transaction.commit();
        matId = mat.getId();
        assertEquals(1, mat.getId());
    }

    @Test
    public void modify() {
        Beer beer = manager.find(Beer.class, beerId);
        assertNotNull(beer);

        transaction.begin();
        beer.setBeerType(BeerType.DRAUGHT);
        transaction.commit();

        beer = manager.find(Beer.class, beerId);

        assertEquals(BeerType.DRAUGHT, beer.getBeerType());
    }

    @Test
    public void removeBeer() {
        Beer beer = manager.find(Beer.class, beerId);
        assertNotNull(beer);

        transaction.begin();
        manager.remove(beer);
        transaction.commit();

        beer = manager.find(Beer.class, beerId);
        assertNull(beer);
    }

    @Test
    public void removeMat() {
        Recipe mat = manager.find(Recipe.class, matId);
        assertNotNull(mat);

        transaction.begin();
        manager.remove(mat);
        transaction.commit();

        mat = manager.find(Recipe.class, matId);
        assertNull(mat);
    }
}