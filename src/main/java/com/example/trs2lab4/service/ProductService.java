package com.example.trs2lab4.service;

import com.example.trs2lab4.dbWorker.DBWorker;
import com.example.trs2lab4.entity.ProductManufacturerCategory;

import java.util.List;

public class ProductService {
    private DBWorker dbWorker = new DBWorker();

    public List<ProductManufacturerCategory> findAll() {
        return dbWorker.fetchAllProducts();
    }
}
