package com.example.trs2lab4.controller.product;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.example.trs2_lab2_desktop.controller.MainControllerAware;
import org.example.trs2_lab2_desktop.entity.Manufacturer;
import org.example.trs2_lab2_desktop.service.ManufacturerService;
import org.springframework.stereotype.Component;

@Component
public class FindByManufacturerController implements MainControllerAware<ProductController> {

    public FindByManufacturerController(ManufacturerService service) {
        this.service = service;
    }

    @FXML
    private ComboBox<Manufacturer> manufacturerSelector;

    private ManufacturerService service;

    private ProductController mainController;


    @FXML
    public void initialize() {
        manufacturerSelector.getItems().addAll(service.findAll());
        manufacturerSelector.setValue(service.findById(1L).orElseThrow(
                () -> new RuntimeException("Manufacturer did not found")
        ));
    }

    public void findByManufacturer() {
        mainController.findByManufacturer(
                manufacturerSelector.getValue().getId()
        );
    }

    @Override
    public void setMainController(ProductController mainController) {
        this.mainController = mainController;
    }
}
