package store.composer;

import org.bson.types.ObjectId;
import store.model.Item;

public class ItemComposer {
    public static Item getItem() {
        Item item = new Item();
        item.setId(new ObjectId("5d2a8bffc51377c6d72c0d2e"));
        item.setName("Mouse");
        item.setDescription("Mouse Logitech");
        item.setQuantity(15);
        item.setPrice(15.9);

        return item;
    }

    public static Item getInvalidItem() {
        Item item = new Item();
        item.setId(new ObjectId("5d2a8bffc51377c6d72c0d2e"));
        item.setName("Mouse");
        item.setDescription("Mouse Logitech");
        item.setQuantity(1);
        item.setPrice(15.9);

        return item;
    }

    public static String getItemJson() {
        return "{" +
                "    \"name\": \"Teclado\"," +
                "    \"description\": \"Teclado Logitech\"," +
                "    \"quantity\": 20," +
                "    \"price\": 15.5" +
                "}";
    }

    public static String getInvalidItemJson() {
        return "{" +
                "    \"name\": \"Teclado\"," +
                "    \"description\": \"Teclado Logitech\"," +
                "    \"quantity\": 5," +
                "    \"price\": 15.5" +
                "}";
    }
}
