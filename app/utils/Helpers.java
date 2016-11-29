package utils;

import org.apache.commons.lang.StringUtils;
import play.data.validation.Error;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO: better name
public class Helpers {

    public static String concatenateErrors(final Map<String, List<Error>> errorsMap) {
        final List<Error> allErrors = new ArrayList<>();
        for (List<Error> errors : errorsMap.values()) {
            allErrors.addAll(errors);
        }

        return StringUtils.join(allErrors, ", ");
    }

}
