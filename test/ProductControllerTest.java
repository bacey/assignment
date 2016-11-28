import models.Category;
import models.Product;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.math.BigDecimal;

public class ProductControllerTest extends UnitTest {

    private static final String EXPECTED_PRODUCT_NAME = "first product";

    @Before
    public void setUp() {
        Fixtures.deleteDatabase();

        final Category category = new Category("first category");
        category.save();

        // Create a new product and save it
        new Product(EXPECTED_PRODUCT_NAME, BigDecimal.TEN, category).save();
    }

    @Test
    public void createAndRetrieveProduct() {
        final Product actualProduct = Product.find("byName", EXPECTED_PRODUCT_NAME).first();

        Assertions.assertThat(Product.count()).isEqualTo(1);
        Assertions.assertThat(actualProduct).isNotNull();
        Assertions.assertThat(actualProduct.name).isEqualTo(EXPECTED_PRODUCT_NAME);
    }

    @Test
    public void tryConnectAsProduct() {
        // Test
        assertNotNull(Product.connect(EXPECTED_PRODUCT_NAME));
        assertNull(Product.connect("does not exist a product with such a name"));
    }

}
