package com.example.trs2lab4.controller;

import com.example.trs2lab4.dbWorker.QueryRequest;

public interface MainControllerAware<T extends MainController> {
    void setMainController(T mainController);
    void setRemoteService(QueryRequest service);
}
