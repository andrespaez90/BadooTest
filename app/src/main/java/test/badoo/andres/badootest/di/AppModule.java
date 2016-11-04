package test.badoo.andres.badootest.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import test.badoo.andres.badootest.api.controllers.RateController;
import test.badoo.andres.badootest.api.services.DataApi;

@Module
public class AppModule {

    @Provides
    @Singleton
    RateController rateController(DataApi dataApi){
        return new RateController(dataApi);
    }

}
