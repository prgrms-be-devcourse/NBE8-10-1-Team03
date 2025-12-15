package com.nbe8101team03.global.response;

public interface Response<T> {
    String getStatus();

    T getData();

    String getMessage();
}
