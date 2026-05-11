package com.example.trs2lab4.controller.product;

import com.example.trs2lab4.dbWorker.QueryRequest;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import com.example.trs2lab4.controller.MainControllerAware;
import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.service.CategoryService;

public class FindByCategoryController implements MainControllerAware<ProductController> {

    private ProductController mainController = new ProductController();

    private QueryRequest service;

    public FindByCategoryController() {
    }

    @FXML
    private ComboBox<Category> categorySelector;

    @FXML
    public void initialize() {
        categorySelector.getItems().addAll(service.findAllCategories());
        categorySelector.setValue(service.findCategoryById(1L).orElseThrow(
                () -> new RuntimeException("category did not found")
        ));
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
    }
}
