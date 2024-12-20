package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.validator.RequestValidator;
import com.grpc.service.FindProductByNameRequest;
import com.grpc.service.ProductRequest;
import com.grpc.service.ProductResponse;
import com.grpc.service.ProductServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class ProductService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    //Unary
//    @Override
//    public void getProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
//        RequestValidator.validateProductId(request.getId())
//                .map(Status::asRuntimeException)
//                .ifPresentOrElse(responseObserver::onError, () -> {
//                    var dbProduct = productRepository.findProductById(request.getId());
//                    dbProduct.ifPresentOrElse(product -> {
//                                responseObserver.onNext(productModelToProtoModel(product));
//                                responseObserver.onCompleted();
//                            },
//                            () -> {
//                                responseObserver.onError(Status.NOT_FOUND.withDescription("Could not find the entity").asRuntimeException());
//                            });
//                });
//    }
//
//    //Server streaming
//    @Override
//    public void getProductByName(FindProductByNameRequest request, StreamObserver<ProductResponse> responseObserver) {
//        RequestValidator.validateProductName(request.getName())
//                .map(Status::asRuntimeException)
//                .ifPresentOrElse(responseObserver::onError, () -> {
//                    var products = productRepository.findAllProductsByName(request.getName());
//                    for (Product product : products) {
//                        responseObserver.onNext(productModelToProtoModel(product));
//                    }
//                    responseObserver.onCompleted();
//                });
//    }

    //Unary
    @Override
    public void getProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        RequestValidator.validateProductIdMetadata(request.getId())
                .ifPresentOrElse(responseObserver::onError, () -> {
                    var dbProduct = productRepository.findProductById(request.getId());
                    dbProduct.ifPresentOrElse(product -> {
                                responseObserver.onNext(productModelToProtoModel(product));
                                responseObserver.onCompleted();
                            },
                            () -> {
                                responseObserver.onError(Status.NOT_FOUND.withDescription("Could not find the entity").asRuntimeException());
                            });
                });
    }

    //Server streaming
    @Override
    public void getProductByName(FindProductByNameRequest request, StreamObserver<ProductResponse> responseObserver) {
        RequestValidator.validateProductNameMetadata(request.getName())
                .ifPresentOrElse(responseObserver::onError, () -> {
                    var products = productRepository.findAllProductsByName(request.getName());
                    for (Product product : products) {
                        responseObserver.onNext(productModelToProtoModel(product));
                    }
                    responseObserver.onCompleted();
                });
    }

    private ProductResponse productModelToProtoModel(Product product) {
        return ProductResponse.newBuilder().setId(product.getId()).setDescription(product.getDescription()).setName(product.getName()).build();
    }

}
