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
        render();
    }

    public static void create() {
        render();
    }

    public static void edit() {
        render();
    }

    public static void delete() {
        render();
    }

}
