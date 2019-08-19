package store.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;
import store.dto.MessageDTO;
import store.exception.CustomException;
import store.model.Item;
import store.service.StoreService;

import static spark.Spark.*;
import static store.gson.GsonUtils.getGson;

public class StoreController {

    private StoreService service;
    private Gson gson;

    @Inject
    public StoreController(StoreService service) {
        this.service = service;
        this.gson = getGson().create();
    }

    public void routes() {
        path("/api", () -> {
            get("/item/:id", (req, res) -> service.findOne(req.params("id")), gson::toJson);
            get("/item", (req, res) -> service.findAll(), gson::toJson);
            post("/item", (req, res) -> {
                res.status(201);
                return service.saveOne(gson.fromJson(req.body(), Item.class));
            }, gson::toJson);

            put("/item/:id", (req, res) -> service.updateOne(req.params("id"), gson.fromJson(req.body(), Item.class)), gson::toJson);
            delete("/item/:id", (req, res) -> {
                service.deleteOne(req.params("id"));
                res.status(204);
                return "";
            }, gson::toJson);
        });

        exception(CustomException.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(ex.getStatusCode());
            res.body(gson.toJson(new MessageDTO(ex.getMessage())));
        });

        exception(Exception.class, (ex, req, res) -> {
            res.type("application/json");
            res.status(500);
            res.body(gson.toJson(new MessageDTO("Ocorreu um erro")));
        });

        notFound(gson.toJson(new MessageDTO("Rota não encontrada")));

        after((req, res) -> res.type("application/json"));
    }
}
