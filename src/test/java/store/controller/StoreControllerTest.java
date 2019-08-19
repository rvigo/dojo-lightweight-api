package store.controller;

import com.google.gson.Gson;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import store.model.Item;

import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static spark.Spark.stop;
import static store.composer.ItemComposer.getInvalidItemJson;
import static store.composer.ItemComposer.getItemJson;
import static store.gson.GsonUtils.getGson;
import static store.server.Main.startUp;

@RunWith(MockitoJUnitRunner.class)
public class StoreControllerTest {

    @BeforeClass
    public static void setUp() throws Exception {
        startUp();
        port = 4567;
    }

    @AfterClass
    public static void tearDown() {
        stop();
    }

    Gson gson = getGson().create();

    @Test
    public void shouldReturnASingleItem() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        get("/api/item/5d3409e56ad8700ab806428e")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("name", equalTo("Mouse"));
    }

    @Test
    public void shouldThrowAnErrorWhenIdNotFound(){
        enableLoggingOfRequestAndResponseIfValidationFails();
        get("/api/item/5d3409e56ad8700ab804428e")
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Item não encontrado"));
    }

    @Test
    public void shouldReturnAllItems() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        get("/api/item")
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("[0]", hasKey("id"));
    }

    @Test
    public void shouldInsertANewItem() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        String json = getItemJson();
        String newItemJson = given()
                .contentType("application/json")
                .body(json)
                .post("/api/item")
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("name", equalTo("Teclado"))
                .extract()
                .asString();

        Item newItem = gson.fromJson(newItemJson, Item.class);
        delete("/api/item/".concat(newItem.getId().toString()));
    }

    @Test
    public void shouldThrowAnErrorWhenQuantityIsLessThan10(){
        enableLoggingOfRequestAndResponseIfValidationFails();
        String json = getInvalidItemJson();
        given()
                .contentType("application/json")
                .body(json)
                .post("/api/item")
                .then()
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("A quantidade deste item deve ser maior que 10"));

    }

    @Test
    public void shouldUpdateAnExistingItem() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        String json = getItemJson();
        String newItemJson = given()
                .contentType("application/json")
                .body(json)
                .post("/api/item")
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .extract()
                .asString();

        Item newItem = gson.fromJson(newItemJson, Item.class);
        newItem.setQuantity(19);
        String updateRoute = "/api/item/".concat(newItem.getId().toString());

        given()
                .contentType("application/json")
                .body(gson.toJson(newItem))
                .put(updateRoute)
                .then()
                .assertThat()
                .statusCode(SC_OK);

        delete("/api/item/".concat(newItem.getId().toString()));
    }

    @Test
    public void shouldDeleteASingleItem() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        String json = getItemJson();
        String newItemJson = given()
                .contentType("application/json")
                .body(json)
                .post("/api/item")
                .then()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .extract()
                .asString();
        Item newItem = gson.fromJson(newItemJson, Item.class);

        get("/api/item/".concat(newItem.getId().toString()))
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", equalTo(newItem.getId().toString()));

        delete("/api/item/".concat(newItem.getId().toString()))
                .then()
                .assertThat()
                .statusCode(SC_NO_CONTENT);

        get("/api/item/".concat(newItem.getId().toString()))
                .then()
                .assertThat()
                .statusCode(SC_NOT_FOUND);
    }
}
