package controllers;

import models.Category;
import play.Logger;
import play.data.validation.Required;
import play.i18n.Messages;
import play.mvc.Controller;
import utils.Helpers;
import utils.ConfiguredPaginator;

import javax.persistence.PersistenceException;

public class Categories extends Controller {

    public static void index() {
        final ConfiguredPaginator<Category> paginator = new ConfiguredPaginator<>(Category.class);
        render(paginator);
    }

    public static void newCategory() {
        final Category category = new Category();
        render(category);
    }

    // handles POST
    // TODO: I18N the @Required message
    public static void create(final @Required(message = "name is required") String name) {
        if (validation.hasErrors()) {
            final String errors = Helpers.concatenateErrors(validation.errorsMap());
            final String errorMessage = Messages.get("validationErrors", "category", errors);
            flash.error(errorMessage);
            renderNewCategory(name);
        } else {
            try {
                Category.create(name);

                flash.success(Messages.get("successfullyCreated", name, "category"));
                redirectToIndex();
            } catch (PersistenceException e) {
                Logger.error("Exception while creating the category with the name: \"%s\". %s", name, e);
                flash.error(Messages.get("nameAlreadyTaken", "category", name));
                renderNewCategory(name);
            }
        }
    }

    public static void edit(final Long id) {
        final Category category = Category.findById(id);
        render(category);
    }

    public static void delete(final Long id) {
        final Category category = Category.findById(id);
        final String name = category.name;

        category.delete();

        flash.success(Messages.get("successfullyDeleted", name, "category"));
        redirectToIndex();
    }

    private static void renderNewCategory(final String name) {
        // TODO: somehow a plain call to newCategory() just didn't work.
        final Category category = new Category();
        category.name = name;

        render("Categories/newCategory.html", category);
    }

    // Post/Redirect/Get pattern: https://en.wikipedia.org/wiki/Post/Redirect/Get
    private static void redirectToIndex() {
        redirect("Categories.index");
    }

}
