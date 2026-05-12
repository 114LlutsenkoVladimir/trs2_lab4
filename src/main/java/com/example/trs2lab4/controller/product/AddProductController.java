package com.example.trs2lab4.controller.product;

import com.example.trs2lab4.dbWorker.QueryRequest;
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
import java.rmi.RemoteException;


public class AddProductController implements MainControllerAware<ProductController>, ShowError {

    private ProductController mainController;

    private QueryRequest remoteService;

    public AddProductController() {
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

    @Override
    public void setRemoteService(QueryRequest service) {
        this.remoteService = service;
        try {
            categorySelector.getItems().addAll(remoteService.findAllCategories());
            categorySelector.setValue(remoteService.findCategoryById(1L));

            manufacturerSelector.getItems().addAll(remoteService.findAllManufacturers());
            manufacturerSelector.setValue(remoteService.findManufacturerById(1L));
        } catch (RuntimeException | RemoteException e) {
            showError(e.getMessage());
        }
    }

    public void initialize() {

    }

    public void addProduct() {
        if (mainController == null)
            System.out.println("MAIN CONTROLLER IS NULL");
        try {
            validate();
            ProductManufacturerCategory product = new ProductManufacturerCategory(
                    nameField.getText(),
                    new BigDecimal(priceField.getText()),
                    categorySelector.getValue().getId(),
                    manufacturerSelector.getValue().getId()
            );
            mainController.addProduct(product);
            nameField.setText("");
            priceField.setText("");
        } catch (RuntimeException e) {
            showError(e.getMessage());
        }

    }

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
