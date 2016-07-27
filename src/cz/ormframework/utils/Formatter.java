package cz.ormframework.utils;

/**
 * siOnzee.cz
 * JDBC ORM Framework Release
 */
public class Formatter {

    /**
     * Formatte string by replacing {number} for [parameter]
     * Example: "There is string {0}", "This will be printed", "This not!"
     * {index} = parameters[index]
     *
     * @param string the string
     * @param params the params
     * @return the string
     */
    public static String format(String string, Object... params) {
        for (int i = 0; i < params.length; i++) {
            if (string.contains("{" + i + "}")) {
                string = string.replace("{" + i + "}", params[i].toString());
            }
        }
        return string;
    }

}
