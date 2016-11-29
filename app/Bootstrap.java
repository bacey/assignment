import models.Category;
import models.Product;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import java.math.BigDecimal;

@OnApplicationStart
public class Bootstrap extends Job {

    private static final int NUMBER_OF_PRODUCTS = 20;
    private static final int NUMBER_OF_CATEGORIES = 3;

    @Override
    public void doJob() {
        final boolean dbIsEmpty = Category.count() == 0;

        if (dbIsEmpty) {
            Logger.debug("Loading initial data into the DB...");

            createDummyCategories();
            createDummyProducts();

            Logger.debug("Loading of initial data finished.");
        } else {
            Logger.debug("Not loading initial data into the DB " +
                    "in order to avoid overwriting the already existing data.");
        }
    }

    private void createDummyCategories() {
        for (int i = 1; i <= NUMBER_OF_CATEGORIES; i++) {
            final Category category = Category.create("Category " + i);
        }
    }

    private void createDummyProducts() {
        for (int i = 1; i <= NUMBER_OF_PRODUCTS; i++) {
            final int categoryNumber = Math.max(i % (NUMBER_OF_CATEGORIES + 1), 1); // [1-45]
            final Category category = Category.findOrCreateByName("Category " + categoryNumber);

            final Product product = new Product("Product " + i, BigDecimal.valueOf(i));
            product.category = category;
            product.save();
        }
    }

}
