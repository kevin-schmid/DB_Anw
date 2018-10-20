package at.fhj.swd.ue4;

import javax.persistence.*;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private int recipeId;

    private String name;

    protected Recipe() {}

    public Recipe(String name) {
        this.name = name;
    }

    public int getId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }
}
