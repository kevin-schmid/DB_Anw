package at.fhj.swd;

import javax.persistence.*;

@Entity
@Table(name = "beer")
public class Beer {

    @Id
    @Column(name = "beer_id")
    @SequenceGenerator (name = "seq_beer", sequenceName = "seq_beer", allocationSize = 1)
    @GeneratedValue (generator = "seq_beer")
    private int beerId;

    private String name;

    @Enumerated
    @Column(name = "beer_type")
    private BeerType beerType;

    protected Beer() {}

    public Beer(String name, BeerType beerType) {
        this.name = name;
        this.beerType = beerType;
    }

    public int getId() {
        return beerId;
    }

    public String getName() {
        return name;
    }

    public BeerType getBeerType() {
        return beerType;
    }

    public void setBeerType(BeerType beerType) {
        this.beerType = beerType;
    }
}
