package at.fhj.swd.ue6;

import at.fhj.swd.persistence.EntityCreator;
import at.fhj.swd.persistence.EntityFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "recipe")
public class Recipe implements at.fhj.swd.persistence.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private int recipeId;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = { @JoinColumn(name = "recipe_id") },
            inverseJoinColumns = { @JoinColumn(name = "ingredient_id") }
    )
    private Collection<Ingredient> ingredients = new ArrayList<>();

    protected Recipe() {}

    public Recipe(String name) {
        this.name = name;
    }

    public int getId() {
        return recipeId;
    }

    public void setName(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        ingredient.getRecipes().add(this);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
        ingredient.getRecipes().remove(this);
    }

    public Collection<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Collection<Ingredient> ingredients) {
        this.ingredients.clear();
        this.ingredients.addAll(ingredients);
    }

    public static SetName factory() {
        return new RecipeFactory();
    }

    protected static class RecipeFactory extends EntityFactory<Recipe> implements SetName {
        private String name;
        @Override
        public EntityCreator<Recipe> setName(String name) {
            this.name = name;
            return this;
        }
        @Override
        protected Recipe createEntity () {
            Recipe recipe = new Recipe();
            recipe.setName(name);
            return recipe;
        }
    }

    public interface SetName {
        EntityCreator<Recipe> setName(String name);
    }
}
