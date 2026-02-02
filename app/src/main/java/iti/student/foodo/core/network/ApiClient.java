package iti.student.foodo.core.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiClient {

    private static Retrofit retrofit;

    private ApiClient() {}

    public static synchronized Retrofit getInstance(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static <T extends ApiService> T create(
            String baseUrl,
            Class<T> serviceClass
    ) {
        return getInstance(baseUrl).create(serviceClass);
    }
}
