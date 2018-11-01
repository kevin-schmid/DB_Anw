package at.fhj.swd.ue5;

import at.fhj.swd.dao.EntityCreator;
import at.fhj.swd.dao.EntityFactory;

import javax.persistence.*;

@Entity
@Table(name = "bundle")
public class Bundle implements at.fhj.swd.dao.Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bundle_id")
    private int bundleId;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beer_id")
    private Beer beer;

    protected Bundle() {}

    public int getBundleId() {
        return bundleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
        beer.addBundle(this);
    }

    public static SetName factory() {
        return new BundleFactory();
    }

    protected static class BundleFactory extends EntityFactory<Bundle> implements SetName, SetBeer {
        private String name;
        private Beer beer;

        @Override
        public SetBeer setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public EntityCreator<Bundle> setBeer(Beer beer) {
            this.beer = beer;
            return this;
        }

        @Override
        protected Bundle createEntity() {
            Bundle bundle = new Bundle();
            bundle.setName(this.name);
            bundle.setBeer(this.beer);
            return bundle;
        }
    }

    public interface SetName {
        SetBeer setName(String name);
    }

    public interface SetBeer {
        EntityCreator<Bundle> setBeer(Beer beer);
    }
}
