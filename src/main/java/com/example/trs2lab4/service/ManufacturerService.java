package com.example.trs2lab4.service;

import com.example.trs2lab4.dbWorker.DBWorker;
import com.example.trs2lab4.entity.Manufacturer;
import com.example.trs2lab4.entity.ProductManufacturerCategory;

import java.util.List;

public class ManufacturerService {
    private DBWorker dbWorker = new DBWorker();

    public List<Manufacturer> findAll() {
        return dbWorker.fetchAllManufacturers();
    }
}
