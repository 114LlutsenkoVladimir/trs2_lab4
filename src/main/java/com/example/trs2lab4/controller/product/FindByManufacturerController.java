package com.example.trs2lab4.controller.product;

import com.example.trs2lab4.dbWorker.QueryRequest;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import com.example.trs2lab4.controller.MainControllerAware;
import com.example.trs2lab4.entity.Manufacturer;
import com.example.trs2lab4.service.ManufacturerService;


public class FindByManufacturerController implements MainControllerAware<ProductController> {

    public FindByManufacturerController() {}

    @FXML
    private ComboBox<Manufacturer> manufacturerSelector;

    private QueryRequest service;

    private ProductController mainController;

    @FXML
    public void initialize() {
        manufacturerSelector.getItems().addAll(service.);
//        manufacturerSelector.setValue(service.findById(1L).orElseThrow(
//                () -> new RuntimeException("Manufacturer did not found")
//        ));
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

    @Override
    public void setRemoteService(QueryRequest service) {
        this.service = service;
    }
}
