package com.example.trs2lab4.controller;

public interface MainControllerAware<T extends MainController> {
    void setMainController(T mainController);
}
