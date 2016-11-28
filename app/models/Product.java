package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
public class Product extends Model {

    @Column(unique = true)
    public String name;

    public BigDecimal price;

    @ManyToOne
    public Category category;

    public Product(final String name, final BigDecimal price) {
        this(name, price, null);
    }

    public Product(final String name, final BigDecimal price, final Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public static Product connect(final String name) {
        return find("byName", name).first();
    }

    @Override
    public String toString() {
        return name;
    }

}
