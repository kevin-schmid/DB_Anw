package at.fhj.swd.ue6;

import at.fhj.swd.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class EntityTest {
    static Persistence P = Persistence.INST;
    static final String persistenceUnitName = "ue6";

    static int recipeId;

    @BeforeClass
    public static void setup() {
        P.create(persistenceUnitName);
    }

    @AfterClass
    public static void teardown() {
        P.close();
    }

    @Test
    public void create() {
        Ingredient i1 = Ingredient.factory().setName("i1").create();
        Ingredient i2 = Ingredient.factory().setName("i2").create();
        Recipe r1 = Recipe.factory().setName("r1").create();
        Recipe r2 = Recipe.factory().setName("r2").create();
        recipeId = r1.getId();
        r1.addIngredient(i1);
        r1.addIngredient(i2);
        r2.addIngredient(i2);
        P.merge(r1);
        P.merge(r2);
        assertEquals(2, i2.getRecipes().size());
        Ingredient i3 = P.find(Ingredient.class, i2.getId());
        assertEquals(2, i3.getRecipes().size());

    }

    @Test
    public void remove() {
        Recipe recipe = P.find(Recipe.class, recipeId);
        P.remove(recipe);
        assertNull(P.find(Recipe.class, recipe.getId()));
        for(Ingredient i : P.findAll(Ingredient.class)) {
            for(Recipe r : i.getRecipes()) {
                assertNotEquals(r.getId(), recipeId);
            }
        }
    }
}
