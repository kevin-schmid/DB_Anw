package at.fhj.swd.ue5;

import at.fhj.swd.dao.EntityCreator;
import at.fhj.swd.dao.EntityFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "beer")
public class Beer implements at.fhj.swd.dao.Entity {

    @Id
    @Column(name = "beer_id")
    @SequenceGenerator (name = "seq_beer", sequenceName = "seq_beer", allocationSize = 1)
    @GeneratedValue (generator = "seq_beer")
    private int beerId;

    private String name;

    @Enumerated
    @Column(name = "beer_type")
    private BeerType beerType;

    @OneToMany(mappedBy = "beer", orphanRemoval=true)
    private final List<Bundle> bundleList = new ArrayList<>();

    protected Beer() {}

    public int getId() {
        return beerId;
    }

    public void setName(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public BeerType getBeerType() {
        return beerType;
    }

    public void setBeerType(BeerType beerType) {
        this.beerType = beerType;
    }

    public void addBundle(Bundle bundle) { bundleList.add(bundle); }

    public List<Bundle> getBundles() { return new ArrayList<>(bundleList); }

    public static BeerFactory factory() {
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
