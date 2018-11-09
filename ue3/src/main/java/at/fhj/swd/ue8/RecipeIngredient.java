package at.fhj.swd.ue8;

import at.fhj.swd.persistence.EntityCreator;
import at.fhj.swd.persistence.EntityFactory;

import javax.persistence.*;

@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient implements at.fhj.swd.persistence.Entity {
    @EmbeddedId
    private RecipeIngredientPrimaryKey pk;

    @ManyToOne(optional = false)
    @JoinColumn(name = "recipe_id", insertable = false, updatable = false)
    private Recipe recipe;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ingredient_id", insertable = false, updatable = false)
    private Ingredient ingredient;

    private int quantity;

    protected RecipeIngredient() {}

    private void setPk(RecipeIngredientPrimaryKey pk) {
        this.pk = pk;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void remove() {
        this.recipe.removeIngredient(this);
        this.ingredient.removeIngredient(this);
    }

    public static SetRecipe factory() {
        return new RecipeIngredientFactory();
    }

    protected static class RecipeIngredientFactory extends EntityFactory<RecipeIngredient> implements SetRecipe, SetIngredient, SetQuantity {
        private Recipe recipe;
        private Ingredient ingredient;
        private int quantity;

        @Override
        protected RecipeIngredient createEntity () {
            RecipeIngredient ri = new RecipeIngredient();
            ri.setRecipe(recipe);
            ri.setIngredient(ingredient);
            ri.setQuantity(quantity);
            ri.getRecipe().addRecipeIngredient(ri);
            ri.getIngredient().addRecipeIngredient(ri);
            ri.setPk(new RecipeIngredientPrimaryKey(ri.getRecipe().getId(), ri.getIngredient().getId()));
            return ri;
        }

        @Override
        public EntityCreator<RecipeIngredient> setQuantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        @Override
        public SetQuantity setIngredient(Ingredient ingredient) {
            this.ingredient = ingredient;
            return this;
        }

        @Override
        public SetIngredient setRecipe(Recipe recipe) {
            this.recipe = recipe;
            return this;
        }
    }

    public interface SetIngredient {
        SetQuantity setIngredient(Ingredient ingredient);
    }

    public interface SetRecipe {
        SetIngredient setRecipe(Recipe recipe);
    }

    public interface SetQuantity {
        EntityCreator<RecipeIngredient> setQuantity(int quantity);
    }
}
