package com.example.trs2lab4.controller.product;

import com.example.trs2lab4.entity.ProductManufacturerCategory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.example.trs2lab4.controller.MainControllerAware;
import com.example.trs2lab4.controller.ShowError;
import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.entity.Manufacturer;
import com.example.trs2lab4.entity.ProductManufacturerCategory;
import com.example.trs2lab4.service.CategoryService;
import com.example.trs2lab4.service.ManufacturerService;

import java.math.BigDecimal;


public class AddProductController implements MainControllerAware<ProductController>, ShowError {

    private ProductController mainController;

    private CategoryService categoryService;

    private ManufacturerService manufacturerService;

    public AddProductController(CategoryService categoryService,
                                ManufacturerService manufacturerService) {
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
    }


    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<Category> categorySelector;

    @FXML
    private ComboBox<Manufacturer> manufacturerSelector;

    @Override
    public void setMainController(ProductController mainController) {
        this.mainController = mainController;
    }

    public void initialize() {
        categorySelector.getItems().addAll(categoryService.findAll());
//        categorySelector.setValue(categoryService.findById(1L).orElseThrow(
//                () -> new RuntimeException("Category did not found")
//        ));
//
//        manufacturerSelector.getItems().addAll(manufacturerService.findAll());
//        manufacturerSelector.setValue(manufacturerService.findById(1L).orElseThrow(
//                () -> new RuntimeException("Manufacturer did not found")
//        ));
    }

//
//    public void addProduct() {
//        if (mainController == null)
//            System.out.println("MAIN CONTROLLER IS NULL");
//        try {
//            validate();
//            ProductManufacturerCategory product = new ProductManufacturerCategory(
//                    nameField.getText(),
//                    new BigDecimal(priceField.getText()),
//                    categorySelector.getValue(),
//                    manufacturerSelector.getValue()
//            );
//            mainController.addProduct(product);
//            nameField.setText("");
//            priceField.setText("");
//        } catch (RuntimeException e) {
//            showError(e.getMessage());
//        }
//
//
//    }

    private void validate() {
        if(nameField.getText().isEmpty())
            throw new RuntimeException("Порожнє ім'я");
        if(priceField.getText().isEmpty())
            throw new RuntimeException("Порожнє поле для ціна");
        try {
            BigDecimal price = new BigDecimal(priceField.getText());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Невірний формат для ціни");
        }
    }
}
