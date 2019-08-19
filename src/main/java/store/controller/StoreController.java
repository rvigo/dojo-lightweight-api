package store.controller;

import com.google.gson.Gson;
import com.google.inject.Inject;
import store.dto.MessageDTO;
import store.service.StoreService;

import static spark.Spark.*;

public class StoreController {

    private StoreService service;
    private Gson gson;

    @Inject
    public StoreController(StoreService service) {
       //TODO implementar injeção de dependencia
    }

    public void routes() {
        path("/api", () -> {

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
