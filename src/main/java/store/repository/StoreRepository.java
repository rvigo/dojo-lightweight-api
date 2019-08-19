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
        this.datastore = morphia.getDataStore();
    }

    public Item findOne(ObjectId id) {
        return datastore.get(Item.class, id);
    }

    public List<Item> findAll() {
        return datastore.find(Item.class).asList();
    }

    public Item updateOne(ObjectId id, Item item) {
        item.setId(id);
        datastore.merge(item);
        return item;
    }

    public Item save(Item item) {
        datastore.save(item);
        return item;
    }

    public void deleteOne(ObjectId id) {
        datastore.delete(datastore.get(Item.class, id));
    }
}
