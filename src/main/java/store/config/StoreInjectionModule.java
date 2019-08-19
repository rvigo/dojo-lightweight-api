package store.config;

import com.google.inject.AbstractModule;
import store.controller.StoreController;
import store.gson.GsonUtils;
import store.repository.StoreRepository;
import store.service.StoreService;

public class StoreInjectionModule extends AbstractModule {

    @Override
    public void configure() {
        bind(StoreController.class);
        bind(StoreService.class);
        bind(StoreRepository.class);
        bind(MorphiaDriver.class);
        bind(GsonUtils.class);
    }
}
