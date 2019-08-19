package store.repository;

import com.google.inject.Inject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import store.config.MorphiaDriver;
import store.model.Item;

import java.util.List;

public class StoreRepository {

    private Datastore datastore;

    @Inject
    public StoreRepository(MorphiaDriver morphia) {
       //TODO implementar injeção de dependencia
    }

    public Item findOne(ObjectId id) {
        return null;
    }

    public List<Item> findAll() {
        return null;
    }

    //Merge
    public Item updateOne(ObjectId id, Item item) {
        return null;
    }

    public Item save(Item item) {
        return null;
    }

    //F/D
    public void deleteOne(ObjectId id) {
    }
}
