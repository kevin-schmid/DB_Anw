package at.fhj.swd.ue8;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class RecipeIngredientPrimaryKey implements Serializable {

    @Column(name = "recipe_id")
    private int recipeId;

    @Column(name = "ingredient_id")
    private int ingredientId;

    protected RecipeIngredientPrimaryKey() {}

    public RecipeIngredientPrimaryKey(int recipeId, int ingredientId) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientPrimaryKey that = (RecipeIngredientPrimaryKey) o;
        return recipeId == that.recipeId &&
                ingredientId == that.ingredientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, ingredientId);
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }
}
