package com.example.validator;

import com.grpc.service.ErrorMessage;
import com.grpc.service.ValidationCode;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;

import java.util.Optional;
import java.util.regex.Pattern;

public final class RequestValidator {

    private static final Pattern PUNCTUATION_PATTERN = Pattern.compile("\\p{P}");

    private static final Metadata.Key<ErrorMessage> ERROR_MESSAGE_KEY = ProtoUtils.keyForProto(ErrorMessage.getDefaultInstance());

    private RequestValidator() {

    }

    public static Optional<Status> validateProductId(int productId) {
        return productId < 1 ? Optional.of(Status.INVALID_ARGUMENT.withDescription("Invalid product id: %s ".formatted(productId))) : Optional.empty();
    }

    public static Optional<Status> validateProductName(String productName) {
        if (productName == null || productName.isBlank()) {
            return Optional.of(Status.INVALID_ARGUMENT.withDescription("Product name cannot be null or blank."));
        }
        if (PUNCTUATION_PATTERN.matcher(productName).find()) {
            return Optional.of(Status.INVALID_ARGUMENT.withDescription("Product name contains invalid characters."));
        }
        return Optional.empty();
    }

    public static Optional<StatusRuntimeException> validateProductIdMetadata(int productId) {
        return productId < 1 ?
                Optional.of(Status.INVALID_ARGUMENT.asRuntimeException(toMetadata(ValidationCode.INVALID_PRODUCT_ID))) :
                Optional.empty();
    }

    public static Optional<StatusRuntimeException> validateProductNameMetadata(String productName) {
        var metadata = toMetadata(ValidationCode.INVALID_PRODUCT_NAME);
        if (productName == null || productName.isBlank()) {
            return Optional.of(Status.INVALID_ARGUMENT.asRuntimeException(metadata));
        }
        if (PUNCTUATION_PATTERN.matcher(productName).find()) {
            return Optional.of(Status.INVALID_ARGUMENT.asRuntimeException(metadata));
        }
        return Optional.empty();
    }

    private static Metadata toMetadata(ValidationCode code) {
        var metadata = new Metadata();
        var value = ErrorMessage.newBuilder().setValidationCode(code).build();
//        var key = Metadata.Key.of("description", Metadata.ASCII_STRING_MARSHALLER);
//        metadata.put(key, code.name());
        metadata.put(ERROR_MESSAGE_KEY, value);
        return metadata;
    }

}
