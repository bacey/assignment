package utils;

import play.Play;
import play.db.jpa.GenericModel;
import play.modules.paginate.ModelPaginator;

public class ConfiguredPaginator<T extends GenericModel> extends ModelPaginator<T> {

    private static final String DEFAULT_PAGE_SIZE = "10";

    public ConfiguredPaginator(final Class<T> typeToken) {
        super(typeToken);

        final int pageSize = Integer.parseInt(Play.configuration.getProperty("paginator.pageSize", DEFAULT_PAGE_SIZE));
        setPageSize(pageSize);

        setBoundaryControlsEnabled(false);
    }
}
