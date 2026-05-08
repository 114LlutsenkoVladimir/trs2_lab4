package com.example.trs2lab4.controller.product;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.trs2_lab2_desktop.controller.MainControllerAware;
import org.example.trs2_lab2_desktop.controller.ShowError;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
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
