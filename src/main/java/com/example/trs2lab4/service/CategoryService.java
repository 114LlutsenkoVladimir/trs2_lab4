package com.example.trs2lab4.service;

import com.example.trs2lab4.dbWorker.DBWorker;
import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.entity.Manufacturer;

import java.util.List;

public class CategoryService {
    private DBWorker dbWorker = new DBWorker();

    public List<Category> findAll() {
        return dbWorker.fetchAllCategories();
    }
}
