package com.example.trs2lab4.controller.product;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.example.trs2_lab2_desktop.controller.MainControllerAware;
import org.example.trs2_lab2_desktop.entity.Category;
import org.example.trs2_lab2_desktop.service.CategoryService;
import org.springframework.stereotype.Component;

@Component
public class FindByCategoryController implements MainControllerAware<ProductController> {

    private ProductController mainController;

    private CategoryService categoryService;

    public FindByCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @FXML
    private ComboBox<Category> categorySelector;

    @FXML
    public void initialize() {
        categorySelector.getItems().addAll(categoryService.findAll());
        categorySelector.setValue(categoryService.findById(1L).orElseThrow(
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
}
