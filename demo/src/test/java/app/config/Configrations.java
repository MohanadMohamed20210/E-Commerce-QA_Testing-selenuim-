package app.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class Configrations {
    public void loadProperities() {
        try {
            Properties property = new Properties();
            File resourcesDir = new File("src/test/resources/");
            if (resourcesDir.exists() && resourcesDir.isDirectory()) {
                for (File file : FileUtils.listFiles(resourcesDir, new String[]{"properties"}, true)) {
                    FileInputStream fis = new FileInputStream(file);
                    property.load(fis);
                    fis.close();
                }
            }
            System.getProperties().putAll(property);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
