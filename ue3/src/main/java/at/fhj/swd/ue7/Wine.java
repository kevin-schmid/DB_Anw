package at.fhj.swd.ue7;

import at.fhj.swd.persistence.EntityCreator;
import at.fhj.swd.persistence.EntityFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "wine")
public class Wine extends Drink implements at.fhj.swd.persistence.Entity {
    @Enumerated
    @Column(name = "colour")
    private WineColour colour;

    public WineColour getColour() {
        return colour;
    }

    public void setColour(WineColour colour) {
        this.colour = colour;
    }

    protected Wine() {}

    public static SetName factory() {
        return new WineFactory();
    }

    public static class WineFactory extends EntityFactory<Wine> implements SetName, SetWineColour {
        private String name;
        private WineColour colour;
        @Override
        protected Wine createEntity() {
            Wine wine = new Wine();
            wine.setName(name);
            wine.setColour(colour);
            return wine;
        }

        @Override
        public SetWineColour setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public EntityCreator<Wine> setColour(WineColour colour) {
            this.colour = colour;
            return this;
        }
    }

    public interface SetName {
        SetWineColour setName(String name);
    }

    public interface SetWineColour {
        EntityCreator<Wine> setColour(WineColour colour);
    }
}
