package controllers;

import models.Category;
import play.data.validation.Required;
import play.mvc.Controller;

import java.util.List;

public class CategoryController extends Controller {

    public static void index() {
        final List<Category> categories = Category.find("order by name asc").fetch();
        // TODO: pagination
                //.from(1).fetch(10);
        render(categories);
    }

    public static void newCategory() {
        final Category category = new Category();
        render(category);
    }

    // handles POST
    public static void create(final @Required String name) {
        if (validation.hasErrors()) {
            //TODO: this doesn't work: newCategory();
            final Category category = new Category();
            render("CategoryController/newCategory.html", category);
        } else {
            Category.create(name);
            // TODO: I18N
            flash.success("Successfully created the %s category.", name);
            // TODO: redirect
            index();
        }
    }

    public static void edit(final Long id) {
        final Category category = Category.findById(id);
        render(category);
    }

    public static void delete() {
        render();
    }

}
