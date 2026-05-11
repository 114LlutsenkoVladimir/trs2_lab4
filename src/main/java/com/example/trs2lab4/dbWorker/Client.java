package com.example.trs2lab4.dbWorker;

import com.example.trs2lab4.entity.ProductManufacturerCategory;

import java.rmi.Naming;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try {
            QueryRequest qp = (QueryRequest) Naming.lookup("//localhost/Query");
            List<ProductManufacturerCategory> productManufacturerCategories = qp.findAll();

            for (ProductManufacturerCategory p : productManufacturerCategories) {
                System.out.println(p);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
