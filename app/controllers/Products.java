package controllers;

import models.Category;
import models.Product;
import play.Logger;
import play.data.validation.Required;
import play.i18n.Messages;
import play.mvc.Controller;
import utils.ConfiguredPaginator;
import utils.Helpers;

import javax.persistence.PersistenceException;
import java.math.BigDecimal;
import java.util.List;

public class Products extends Controller {

    public static void index() {
        final ConfiguredPaginator<Product> paginator = new ConfiguredPaginator<>(Product.class);
        render(paginator);
    }

    public static void newProduct() {
        final Product product = new Product();
        final List<Category> categories = getAllCategories();

        render(product, categories);
    }

    // TODO: I18N the @Required message
    // TODO: should differentiate b/w create and update
    public static void create(final @Required(message = "name is required") String name,
                              final @Required(message = "price is required") BigDecimal price,
                              final @Required(message = "category is required") Long categoryId) {
        if (validation.hasErrors()) {
            final String errors = Helpers.concatenateErrors(validation.errorsMap());
            final String errorMessage = Messages.get("validationErrors", "product", errors);
            flash.error(errorMessage);
            renderNewProduct(name, price, categoryId);
        } else {
            try {
                final Category category = Category.findById(categoryId);
                Product.create(name, price, category);

                flash.success(Messages.get("successfullyCreated", name, "product"));
                redirectToIndexWhileShowingFlash();
            } catch (PersistenceException e) {
                Logger.error("Exception while creating the product with the name: \"%s\". %s", name, e);
                flash.error(Messages.get("nameAlreadyTaken", "product", name));
                renderNewProduct(name, price, categoryId);
            }
        }
    }

    public static void edit(final Long id) {
        final Product product = Product.findById(id);
        final List<Category> categories = getAllCategories();

        render(product, categories);
    }

    public static void delete(final Long id) {
        final Product product = Product.findById(id);
        final String name = product.name;

        product.delete();

        flash.success(Messages.get("successfullyDeleted", name, "product"));
        redirectToIndex();
    }

    private static void renderNewProduct(final String name, final BigDecimal price, final Long categoryId) {
        Category category = null;
        if (categoryId != null) {
            category = Category.findById(categoryId);
        }
        final Product product = new Product(name, price, category);

        final List<Category> categories = getAllCategories();

        render("Products/newProduct.html", product, categories);
    }

    public static void redirectToIndex() {
        redirectToIndexWhileShowingFlash();
    }

    // If this is not private, then the flash gets lost during redirect.
    private static void redirectToIndexWhileShowingFlash() {
        redirect("Products.index");
    }


    private static List<Category> getAllCategories() {
        return Category.all().fetch();
    }

}
