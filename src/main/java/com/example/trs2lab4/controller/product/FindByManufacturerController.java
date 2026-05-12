package com.example.trs2lab4.controller.product;

import com.example.trs2lab4.controller.ShowError;
import com.example.trs2lab4.dbWorker.QueryRequest;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import com.example.trs2lab4.controller.MainControllerAware;
import com.example.trs2lab4.entity.Manufacturer;
import com.example.trs2lab4.service.ManufacturerService;

import java.rmi.Remote;
import java.rmi.RemoteException;


public class FindByManufacturerController implements MainControllerAware<ProductController>, ShowError {

    public FindByManufacturerController() {}

    @FXML
    private ComboBox<Manufacturer> manufacturerSelector;

    private QueryRequest service;

    private ProductController mainController;

    @FXML
    public void initialize() {
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
        try {
            manufacturerSelector.getItems().addAll(service.findAllManufacturers());
            manufacturerSelector.setValue(service.findManufacturerById(1L));
        } catch (RemoteException | RuntimeException e) {
            showError(e.getMessage());
            e.printStackTrace();
        }
    }
}
