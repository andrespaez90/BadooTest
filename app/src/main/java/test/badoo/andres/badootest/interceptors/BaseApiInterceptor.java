package test.badoo.andres.badootest.interceptors;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public abstract class BaseApiInterceptor implements Interceptor {

    static final String baseUrl = "www.andrespaez90badoo.com";

    Response getResponse(Chain chain, String responseString) {
        Response response;
        response = new Response.Builder()
                .code(200)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .build();
        return response;
    }

}
