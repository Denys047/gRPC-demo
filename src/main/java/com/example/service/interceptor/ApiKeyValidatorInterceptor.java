package com.example.service.interceptor;

import io.grpc.*;

import java.util.Objects;

public class ApiKeyValidatorInterceptor implements ServerInterceptor {

    private static final Metadata.Key<String> API_KEY = Metadata.Key.of("api-key", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        var apiKey = headers.get(API_KEY);
        if (isValidApiKey(apiKey)) {
            return next.startCall(call, headers);
        }
        call.close(
                Status.UNAUTHENTICATED.withDescription("client must provide a valid api key"),
                headers
        );
        return new ServerCall.Listener<>() {
        };
    }

    private boolean isValidApiKey(String apiKey) {
        return Objects.nonNull(apiKey) && apiKey.equals("apiKey");
    }

}
