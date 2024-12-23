package com.example.service.interceptor;

import io.grpc.*;

public class GzipResponseInterceptor implements ServerInterceptor {

    private final String gzipCompressionName = new Codec.Gzip().getMessageEncoding();

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {
        call.setCompression(gzipCompressionName);
        return next.startCall(call, headers);
    }

}
