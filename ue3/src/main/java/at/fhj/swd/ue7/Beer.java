package at.fhj.swd.ue7;

import at.fhj.swd.persistence.EntityCreator;
import at.fhj.swd.persistence.EntityFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "beer")
public class Beer extends Drink implements at.fhj.swd.persistence.Entity {
    @Enumerated
    @Column(name = "beer_type")
    private BeerType beerType;

    public BeerType getBeerType() {
        return beerType;
    }

    public void setBeerType(BeerType beerType) {
        this.beerType = beerType;
    }

    protected Beer(){}

    public static SetName factory() {
        return new BeerFactory();
    }

    public static class BeerFactory extends EntityFactory<Beer> implements SetName, SetBeerType {
        private String name;
        private BeerType beerType;
        @Override
        protected Beer createEntity() {
            Beer beer = new Beer();
            beer.setName(name);
            beer.setBeerType(beerType);
            return beer;
        }

        @Override
        public SetBeerType setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public EntityCreator<Beer> setBeerType(BeerType beerType) {
            this.beerType = beerType;
            return this;
        }
    }

    public interface SetName {
        SetBeerType setName(String name);
    }

    public interface SetBeerType {
        EntityCreator<Beer> setBeerType(BeerType beerType);
    }
}
