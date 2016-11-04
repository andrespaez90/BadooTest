package test.badoo.andres.badootest.di;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import test.badoo.andres.badootest.DetailTrasactionActivity;
import test.badoo.andres.badootest.MainActivity;
import test.badoo.andres.badootest.api.services.DataApi;

@Singleton
@Component(modules = {ApiModule.class, AppModule.class})
public interface AppComponent {

    Retrofit retrofit();

    DataApi dataApi();

    void inject(MainActivity mainActivity);

    void inject(DetailTrasactionActivity deatilTrasactionActivity);
}
