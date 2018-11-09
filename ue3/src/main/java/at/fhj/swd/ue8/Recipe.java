package at.fhj.swd.ue8;

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

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.PERSIST)
    private Collection<RecipeIngredient> recipeIngredients = new ArrayList<>();

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

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredients.add(recipeIngredient);
    }

    public void removeIngredient(RecipeIngredient recipeIngredient) {
        recipeIngredients.remove(recipeIngredient);
    }

    public Collection<RecipeIngredient> getRecipeIngredients() {
        return recipeIngredients;
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
