package util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class CategoryUtil {
    @Autowired
    private static Environment env;
    public static String INVENTORY_SERVICE = env.getProperty("inventory-service");
    public static String PRICE_SERVICE = env.getProperty("price-service");
    public static String ORIGIN = "http://localhost:9191";
}
