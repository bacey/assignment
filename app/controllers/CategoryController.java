package controllers;

import models.Category;
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

    public static void create() {
        render();
    }

    public static void edit(final Long id) {
        final Category category = Category.findById(id);
        render(category);
    }

    public static void delete() {
        render();
    }

}
