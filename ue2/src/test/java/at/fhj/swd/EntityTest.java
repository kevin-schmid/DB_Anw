package at.fhj.swd;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.List;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class EntityTest {

    static EntityManagerFactory factory;
    static EntityManager manager;
    static EntityTransaction transaction;

    static final String persistenceUnitName = "beerfactory";

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
        assertTrue(punti.getId() > 999);
    }

    @Test
    public void createMaterial() {
        transaction.begin();
        Material mat = new Material("Hemp");
        assertNotNull(mat);
        manager.persist(mat);
        transaction.commit();
        matId = mat.getId();

        List<Material> mats = manager.createQuery("SELECT m FROM Material m", Material.class).getResultList();
        assertEquals(1, mats.size());
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
        Material mat = manager.find(Material.class, matId);
        assertNotNull(mat);

        transaction.begin();
        manager.remove(mat);
        transaction.commit();

        mat = manager.find(Material.class, matId);
        assertNull(mat);
    }
}