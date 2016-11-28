package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends Model {

    // TODO: make this unique
    public String name;

    // TODO: is cascade needed? What are the requirements?
    @OneToMany(mappedBy="category") // , cascade=CascadeType.ALL)
    public List<Product> products = new ArrayList<>();

    // TODO: is this needed?
    public Category addProduct(final Product product) {
        this.save();

        product.category = this;
        product.save();

        this.products.add(product);
        this.save();

        return this;
    }

    public static Category create(final String name) {
        final Category category = new Category();
        category.name = name;
        category.save();
        return category;
    }
}
