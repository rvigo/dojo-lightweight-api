package store.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import store.config.StoreInjectionModule;
import store.controller.StoreController;

public class Main {

    public static void main(String[] args){
        startUp();
    }

    public static void startUp(){
        Injector injector = Guice.createInjector(new StoreInjectionModule());
        StoreController controller = injector.getInstance(StoreController.class);
        controller.routes();
    }
}
