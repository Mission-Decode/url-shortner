package common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlValidator {

    private UrlValidator() {

    }

    private static Pattern urlPattern = Pattern.compile
            ("^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]" +
                    "{2,5}(:[0-9]{1,5})?(\\/.*)?$");

    private static Matcher matcher;

    public static boolean isValidUrl(String url) {
        matcher = urlPattern.matcher(url);
        return matcher.matches();
    }

}
