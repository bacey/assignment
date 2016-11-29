import models.Category;
import models.Product;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.math.BigDecimal;
import java.util.List;

public class CategoryTest extends UnitTest {

    private static final String EXPECTED_PRODUCT_NAME = "first product";

    private Category category;

    @Before
    public void setUp() {
        Fixtures.deleteDatabase();

        category = Category.create("first category");
        category.save();

        final Product product = new Product(EXPECTED_PRODUCT_NAME, BigDecimal.TEN, category);
        product.save();

        category.products.add(product);
        category.save();
    }

    @Test
    public void createAndRetrieveProduct() {
        final Product actualProduct = Product.find("byName", EXPECTED_PRODUCT_NAME).first();

        Assertions.assertThat(Product.count()).isEqualTo(1);
        Assertions.assertThat(actualProduct).isNotNull();
        Assertions.assertThat(actualProduct.name).isEqualTo(EXPECTED_PRODUCT_NAME);
    }

    @Test
    public void createAndRetrieveProduct2() {
        final List<Product> products = Product.find("byCategory", category).fetch();

        Assertions.assertThat(Product.count()).isEqualTo(1);
        Assertions.assertThat(products).hasSize(1);
        Assertions.assertThat(products).extracting("name").containsExactly(EXPECTED_PRODUCT_NAME);
    }

    @Test
    public void createAndRetrieveProduct3() {
        final List<Product> products = category.products;

        Assertions.assertThat(Product.count()).isEqualTo(1);
        Assertions.assertThat(products).hasSize(1);
        Assertions.assertThat(products).extracting("name").containsExactly(EXPECTED_PRODUCT_NAME);
    }

    @Test
    public void createAndRetrieveProduct4() {
        final Category category = Category.create("second category");
        //category.addProduct(new Product(EXPECTED_PRODUCT_NAME, BigDecimal.TEN));

        final List<Product> products = category.products;

        Assertions.assertThat(products).hasSize(1);
        Assertions.assertThat(products).extracting("name").containsExactly(EXPECTED_PRODUCT_NAME);
    }

    @Test
    public void tryConnectAsProduct() {
        // Test
//        assertNotNull(Product.connect(EXPECTED_PRODUCT_NAME));
//        assertNull(Product.connect("does not exist a product with such a name"));
    }

}
