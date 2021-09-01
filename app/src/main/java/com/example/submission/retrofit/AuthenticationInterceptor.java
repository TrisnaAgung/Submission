package com.example.submission.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Authorization", "token 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");

        Request request = builder.build();
        return chain.proceed(request);
    }
}