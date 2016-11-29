package models;

import play.db.jpa.Model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends Model {

    @Column(unique = true)
    public String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    public List<Product> products = new ArrayList<>();

    public static Category create(final String name) {
        final Category category = new Category();
        category.name = name;
        category.save();
        return category;
    }

    public static Category findOrCreateByName(final String name) {
        Category category = Category.find("byName", name).first();

        if (category == null) {
            category = Category.create(name);
        }

        return category;
    }

    @Override
    public String toString() {
        return name;
    }

}
