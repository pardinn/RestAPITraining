package resources;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileHandler {
    public static void loadPropertiesFile(Properties prop, String filePath) {
        InputStream input = null;
        try {
            input = new FileInputStream(filePath);

            // load a properties file
            prop.load(input);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
