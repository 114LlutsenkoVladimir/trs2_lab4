package com.example.trs2lab4.controller.product;

import com.example.trs2lab4.dbWorker.QueryRequest;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import com.example.trs2lab4.controller.MainControllerAware;
import com.example.trs2lab4.controller.ShowError;


import java.math.BigDecimal;


public class FindByPriceController implements MainControllerAware<ProductController>, ShowError {

    private ProductController mainController;

    @FXML
    private TextField priceFieldStart;

    @FXML
    private TextField priceFieldEnd;


    @Override
    public void setMainController(ProductController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void setRemoteService(QueryRequest service) {
    }

    public void findByPrice() {
        try {
            BigDecimal start = new BigDecimal(priceFieldStart.getText());
            BigDecimal end = new BigDecimal(priceFieldEnd.getText());
            mainController.findByPrice(start, end);
            priceFieldStart.setText("");
            priceFieldEnd.setText("");
        } catch (NumberFormatException e) {
           showError("Невірний формат для ціни");
        }
    }


}
