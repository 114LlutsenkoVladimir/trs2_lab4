package com.example.trs2lab4.dbWorker;


import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.entity.ProductManufacturerCategory;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface QueryRequest extends Remote {
    List<ProductManufacturerCategory> findAll() throws RemoteException;

    List<ProductManufacturerCategory> findByCategoryId(Long id);

    List<ProductManufacturerCategory> findByManufacturerId(Long id);

    List<ProductManufacturerCategory> findByPriceBetween(BigDecimal from, BigDecimal to);

    List<Category> findAllCategories();

    Category findCategoryById(Long id);
}
