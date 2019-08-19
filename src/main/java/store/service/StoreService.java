package store.service;

import com.google.inject.Inject;
import org.bson.types.ObjectId;
import store.exception.CustomException;
import store.model.Item;
import store.repository.StoreRepository;

import java.util.List;

public class StoreService {

    @Inject
    private StoreRepository repo;

    public Item findOne(String id) {
        Item item = repo.findOne(new ObjectId(id));
        if (item == null)
            throw new CustomException("Item não encontrado", 404);

        return item;
    }

    public List<Item> findAll() {
        return repo.findAll();
    }

    public Item updateOne(String id, Item item) {
        return repo.updateOne(new ObjectId(id), item);
    }

    public void deleteOne(String id) {
        repo.deleteOne(new ObjectId(id));
    }

    public Item saveOne(Item item) {

        if(item.getQuantity() < 10)
            throw new CustomException("A quantidade deste item deve ser maior que 10", 400);

        return repo.save(item);
    }
}
