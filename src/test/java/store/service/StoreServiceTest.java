package store.service;

import org.bson.types.ObjectId;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import store.exception.CustomException;
import store.model.Item;
import store.repository.StoreRepository;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static store.composer.ItemComposer.getInvalidItem;
import static store.composer.ItemComposer.getItem;

@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository repo;

    @InjectMocks
    private StoreService service;

    @Rule
    public ExpectedException exception= ExpectedException.none();

    @Test
    public void shouldReturnASingleItem() {
        String id = "5d2a8bffc51377c6d72c0d2e";
        Item item = getItem();
        when(repo.findOne(any(ObjectId.class))).thenReturn(item);
        Item res = service.findOne(id);
        assertThat(res.getId(), is(item.getId()));
    }

    @Test
    public void shouldThrowAnErrorWhenCannotFindASingleItem() {
        String id = "5d2a8bffc51377c6d72c0d2e";
        Item item = getItem();
        when(repo.findOne(any(ObjectId.class))).thenReturn(null);
        exception.expectMessage("Item não encontrado");
        exception.expect(CustomException.class);
        service.findOne(id);

    }

    @Test
    public void shouldReturnAListOfItems() {
        when(repo.findAll()).thenReturn(Arrays.asList(getItem(), getItem()));
        List<Item> res = service.findAll();
        assertThat(res, hasSize(2));
    }

    @Test
    public void shouldFindAndUpdateAnItem() {
        String id = "5d2a8bffc51377c6d72c0d2e";
        Item newItem = getItem();
        newItem.setName("televisão");
        newItem.setDescription("TV LG");
        when(repo.updateOne(new ObjectId(id), newItem)).thenReturn(newItem);
        Item res = service.updateOne(id, newItem);
        assertThat(res.getName(), is(newItem.getName()));
        assertThat(res.getDescription(), is(newItem.getDescription()));
        assertThat(res.getId(), is(newItem.getId()));
    }

    @Test
    public void shouldInsertANewItem() {
        Item newItem = getItem();
        when(repo.save(any(Item.class))).thenReturn(newItem);
        Item res = service.saveOne(newItem);
        assertThat(res.getName(), is(newItem.getName()));
        assertThat(res.getDescription(), is(newItem.getDescription()));
        assertThat(res.getPrice(), is(newItem.getPrice()));
        assertThat(res.getQuantity(), is(newItem.getQuantity()));
    }

    @Test
    public void shouldThrowAnErrorWhenInsertAnInvalidItem() {
        Item newItem = getInvalidItem();
        when(repo.save(any(Item.class))).thenReturn(newItem);
        exception.expect(CustomException.class);
        exception.expectMessage("A quantidade deste item deve ser maior que 10");
        service.saveOne(newItem);
    }

    @Test
    public void shouldFindAndDeleteAnItem() {
        String id = "5d2a8bffc51377c6d72c0d2e";
        service.deleteOne(id);
        doNothing().when(repo).deleteOne(any(ObjectId.class));
        verify(repo).deleteOne(any(ObjectId.class));
    }
}
