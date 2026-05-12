package com.example.trs2lab4.controller.product;

import com.example.trs2lab4.controller.ShowError;
import com.example.trs2lab4.dbWorker.QueryRequest;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import com.example.trs2lab4.controller.MainControllerAware;
import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.service.CategoryService;

import java.rmi.RemoteException;

public class FindByCategoryController implements MainControllerAware<ProductController>, ShowError {

    private ProductController mainController = new ProductController();

    private QueryRequest service;

    public FindByCategoryController() {
    }

    @FXML
    private ComboBox<Category> categorySelector;

    @FXML
    public void initialize() {

    }

    public void findByCategory() {
        mainController.findByCategory(categorySelector.getValue().getId());
    }

    @Override
    public void setMainController(ProductController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void setRemoteService(QueryRequest service) {
        this.service = service;
        try {
            categorySelector.getItems().addAll(service.findAllCategories());
            categorySelector.setValue(service.findCategoryById(2L));
        } catch (RemoteException | RuntimeException e) {
            showError(e.getMessage());
        }
    }
}
