package test.badoo.andres.badootest.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.badoo.andres.badootest.api.services.DataApi;
import test.badoo.andres.badootest.interceptors.RateInterceptor;
import test.badoo.andres.badootest.interceptors.TransactionInterceptor;

@Module
public class ApiModule {

    @Provides
    @Singleton
    DataApi dataApi(Retrofit retrofit) {
        return retrofit.create(DataApi.class);
    }

    @Provides
    @Singleton
    public Retrofit retrofit(Gson gson) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);

        httpClient.addInterceptor(new RateInterceptor());

        httpClient.addInterceptor(new TransactionInterceptor());

        return new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl("http://www.andrespaez90badoo.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    public Gson gson() {
        return new GsonBuilder()
                .create();
    }
}
