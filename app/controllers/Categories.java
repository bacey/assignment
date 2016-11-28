package controllers;

import models.Category;
import org.apache.commons.lang.StringUtils;
import play.Logger;
import play.data.validation.Error;
import play.data.validation.Required;
import play.i18n.Messages;
import play.modules.paginate.ModelPaginator;
import play.mvc.Controller;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO: I18N all the strings in this class
public class Categories extends Controller {

    public static void index() {
        final List<Category> categories = Category.find("order by id asc").fetch();
        // TODO: pagination
        //.from(1).fetch(10);
        final ModelPaginator paginator = new ModelPaginator(Category.class);
        paginator.setBoundaryControlsEnabled(false);
        render(paginator);
        //render(categories);
    }

    public static void newCategory() {
        final Category category = new Category();
        render(category);
    }

    // handles POST
    public static void create(final @Required(message = "name is required") String name) {
        if (validation.hasErrors()) {
            final String errors = collectErrors(validation.errorsMap());
            final String errorMessage = Messages.get("category.validationErrors", errors);
            flash.error(errorMessage);
            renderNewCategory(name);
        } else {
            try {
                Category.create(name);

                flash.success("Successfully created the %s category.", name);
                // TODO: redirect
                index();
            } catch (PersistenceException e) {
                Logger.error("Exception while creating the category with the name: \"%s\". %s", name, e);
                flash.error("Error while creating the category. " +
                        "Maybe the %s name is already taken?", name);
                renderNewCategory(name);
            }
        }
    }

    public static void edit(final Long id) {
        final Category category = Category.findById(id);
        render(category);
    }

    public static void delete() {
        render();
    }

    private static String collectErrors(final Map<String, List<Error>> errorsMap) {
        final List<Error> allErrors = new ArrayList<>();
        for (List<Error> errors : errorsMap.values()) {
            allErrors.addAll(errors);
        }

        return StringUtils.join(allErrors, ", ");
    }

    private static void renderNewCategory(final String name) {
        //TODO: somehow just calling newCategory() doesn't work.
        final Category category = new Category();
        category.name = name;

        render("Categories/newCategory.html", category);
    }

}
