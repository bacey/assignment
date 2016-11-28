package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends Model {

    public String name;

    // TODO: is cascade needed? What are the requirements?
    @OneToMany(mappedBy="category") // , cascade=CascadeType.ALL)
    public List<Product> products = new ArrayList<>();

    public Category() {
        // for CategoryController#newCategory
    }

    public Category(final String name) {
        this.name = name;
    }

    // TODO: is this needed?
    public Category addProduct(final Product product) {
        this.save();

        product.category = this;
        product.save();

        this.products.add(product);
        this.save();

        return this;
    }

}
