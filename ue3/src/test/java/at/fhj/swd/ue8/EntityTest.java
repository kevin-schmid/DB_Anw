package at.fhj.swd.ue8;

import at.fhj.swd.persistence.Persistence;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class EntityTest {
    static Persistence P = Persistence.INST;
    static final String persistenceUnitName = "ue8";

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
        Ingredient i2 = Ingredient.factory().setName("i1").create();
        Recipe recipe = Recipe.factory().setName("r1").create();
        RecipeIngredient.factory().setRecipe(recipe).setIngredient(i1).setQuantity(5).create();
        RecipeIngredient.factory().setRecipe(recipe).setIngredient(i2).setQuantity(10).create();
        P.refresh(recipe);
        assertEquals(2, recipe.getRecipeIngredients().size());
    }
}
