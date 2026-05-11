package com.example.trs2lab4.controller.product;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import com.example.trs2lab4.controller.MainControllerAware;
import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.service.CategoryService;

public class FindByCategoryController implements MainControllerAware<ProductController> {

    private ProductController mainController = new ProductController();

    private CategoryService categoryService;

    public FindByCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @FXML
    private ComboBox<Category> categorySelector;

    @FXML
    public void initialize() {
        categorySelector.getItems().addAll(categoryService.findAll());
//        categorySelector.setValue(categoryService.findById(1L).orElseThrow(
//                () -> new RuntimeException("category did not found")
//        ));
    }

    public void findByCategory() {
        mainController.findByCategory(categorySelector.getValue().getId());
    }

    @Override
    public void setMainController(ProductController mainController) {
        this.mainController = mainController;
    }
}
