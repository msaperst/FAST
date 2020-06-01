package sample;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {
    static String getProperty(String property) {
        Properties prop = new Properties();
        try (InputStream input = Property.class.getClassLoader().getResourceAsStream("credentials.properties")) {
            prop.load(input);
        } catch (NullPointerException | IOException e) {
        }
        return prop.getProperty(property);
    }
}
