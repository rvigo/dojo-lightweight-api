package store.gson;

import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import org.bson.types.ObjectId;

public class GsonUtils {

    @Inject
    private static GsonBuilder builder;

    public static GsonBuilder getGson(){
        builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdSerializer());
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdDeserializer());

        return builder;

    }
}
