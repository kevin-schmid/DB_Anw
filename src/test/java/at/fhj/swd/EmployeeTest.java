package at.fhj.swd;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class EmployeeTest {

    static EntityManagerFactory factory;
    static EntityManager manager;
    static EntityTransaction transaction;

    static final String persistenceUnitName = "company";

    static final int id = 158;
    static final String firstName = "John";
    static final String lastName = "Doe";
    static final int salary = 45000;
    static final int salaryRaise = 1000;

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
    public void create() {
        transaction.begin();
        Employee john = new Employee(id, firstName, lastName, salary);
        assertNotNull(john);
        manager.persist(john);
        transaction.commit();

        System.out.println("Created and Persisted " + john);

    }

    @Test
    public void modify() {
        Employee john = manager.find(Employee.class, id);
        assertNotNull(john);
        System.out.println("Found " + john);

        transaction.begin();
        john.raiseSalary(salaryRaise);
        transaction.commit();

        //#if STRICT
        //start from scratch - this ensures that john is fetched from the DB :
        //teardown ();
        //setup    ();
        //#endif

        john = manager.find(Employee.class, id);

        assertEquals(salary + salaryRaise, (int) john.getSalary());
        System.out.println("Updated " + john);
    }

    @Test
    public void remove() {
        Employee john = manager.find(Employee.class, id);
        assertNotNull(john);

        transaction.begin();
        manager.remove(john);
        transaction.commit();

        john = manager.find(Employee.class, id);
        assertNull(john);

        System.out.println("Removed " + id);
    }
}
