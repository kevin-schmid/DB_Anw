package at.fhj.swd.ue9;

import at.fhj.swd.persistence.Persistence;
import at.fhj.swd.ue8.Ingredient;
import at.fhj.swd.ue8.Recipe;
import at.fhj.swd.ue8.RecipeIngredient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.Query;

import static org.junit.Assert.assertEquals;

@org.junit.FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class QueryTest {
    static Persistence P = Persistence.INST;
    static final String persistenceUnitName = "ue8";
    static int ingredientId;

    @BeforeClass
    public static void setup() {
        P.create(persistenceUnitName);
        Ingredient i1 = Ingredient.factory().setName("i1").create();
        Ingredient i2 = Ingredient.factory().setName("i2").create();
        ingredientId = i2.getId();
        Recipe recipe = Recipe.factory().setName("r1").create();
        RecipeIngredient.factory().setRecipe(recipe).setIngredient(i1).setQuantity(5).create();
        RecipeIngredient.factory().setRecipe(recipe).setIngredient(i2).setQuantity(10).create();
        P.refresh(recipe);
        assertEquals(2, recipe.getRecipeIngredients().size());
    }

    @AfterClass
    public static void teardown() {
        P.close();
    }

    @Test
    public void simpleQueryTest() {
        Query query = P.getEntityManager().createQuery(
                "select i.id from RecipeIngredient ri join ri.ingredient i where i.name like '%2'"
        );
        assertEquals(ingredientId, query.getSingleResult());
    }
}
