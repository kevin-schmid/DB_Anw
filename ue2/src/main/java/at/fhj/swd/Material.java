package at.fhj.swd;

import javax.persistence.*;

@Entity
@Table(name = "ingredient")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private int materialId;

    private String name;

    protected Material() {}

    public Material(String name) {
        this.name = name;
    }

    public int getId() {
        return materialId;
    }

    public String getName() {
        return name;
    }
}
