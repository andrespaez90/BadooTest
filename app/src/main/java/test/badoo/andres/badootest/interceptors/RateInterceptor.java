package test.badoo.andres.badootest.interceptors;


import java.io.IOException;
import java.net.URI;

import okhttp3.Interceptor;
import okhttp3.Response;

public class RateInterceptor extends BaseApiInterceptor {

    private final static String GET_RATES = "[{\n" +
            "\t\"from\": \"AUD\",\n" +
            "\t\"rate\": \"0.5\",\n" +
            "\t\"to\": \"USD\"\n" +
            "}, {\n" +
            "\t\"from\": \"USD\",\n" +
            "\t\"rate\": \"0.5\",\n" +
            "\t\"to\": \"EUR\"\n" +
            "}, {\n" +
            "\t\"from\": \"EUR\",\n" +
            "\t\"rate\": \"0.5\",\n" +
            "\t\"to\": \"GBP\"\n" +
            "}, {\n" +
            "\t\"from\": \"GBP\",\n" +
            "\t\"rate\": \"2\",\n" +
            "\t\"to\": \"EUR\"\n" +
            "}, {\n" +
            "\t\"from\": \"EUR\",\n" +
            "\t\"rate\": \"2\",\n" +
            "\t\"to\": \"USD\"\n" +
            "}, {\n" +
            "\t\"from\": \"USD\",\n" +
            "\t\"rate\": \"2\",\n" +
            "\t\"to\": \"AUD\"\n" +
            "}]";

    public static final String getRates = "/rates";


    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;

        final URI uri = chain.request().url().uri();

        final String path = uri.getPath();

        final String authority = uri.getAuthority();

        if (authority.equals(baseUrl)) {
            switch (path) {
                case getRates:
                    response = getResponse(chain, GET_RATES);
                    break;
                default:
                    response = chain.proceed(chain.request());
                    break;
            }

        } else {
            response = chain.proceed(chain.request());
        }

        return response;
    }
}
