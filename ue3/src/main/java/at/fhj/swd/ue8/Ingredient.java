package at.fhj.swd.ue8;

import at.fhj.swd.persistence.EntityCreator;
import at.fhj.swd.persistence.EntityFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "ingredient")
public class Ingredient implements at.fhj.swd.persistence.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private int ingredientId;

    private String name;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.PERSIST)
    private List<RecipeIngredient> recipeIngredients = new ArrayList<>();

    protected Ingredient() {}

    public int getId() {
        return ingredientId;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return new IngredientFactory();
    }

    protected static class IngredientFactory extends EntityFactory<Ingredient> implements SetName {
        private String name;
        @Override
        public EntityCreator<Ingredient> setName(String name) {
            this.name = name;
            return this;
        }
        @Override
        protected Ingredient createEntity () {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(name);
            return ingredient;
        }
    }

    public interface SetName {
        EntityCreator<Ingredient> setName(String name);
    }
}
