package com.example.trs2lab4.dbWorker;


import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.entity.ProductManufacturerCategory;
import com.example.trs2lab4.service.ProductService;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class QueryPreparer extends UnicastRemoteObject implements QueryRequest {

    private DBWorker dbWorker = new DBWorker();
    protected QueryPreparer() throws RemoteException { super(); }

    @Override
    public List<ProductManufacturerCategory> findAll() throws RemoteException {
        return dbWorker.fetchAllProducts();
    }

    @Override
    public List<ProductManufacturerCategory> findByCategoryId(Long id) {
        return dbWorker.fetchProductsByCategoryId(id);
    }

    @Override
    public List<ProductManufacturerCategory> findByManufacturerId(Long id) {
        return dbWorker.fetchProductsByManufacturerId(id);
    }

    @Override
    public List<ProductManufacturerCategory> findByPriceBetween(BigDecimal from, BigDecimal to) {
        return dbWorker.fetchProductsByPriceBetween(from, to);
    }

    @Override
    public List<Category> findAllCategories() {
        return dbWorker.fetchAllCategories();
    }

    @Override
    public Category findCategoryById(Long id) {
        return dbWorker.findCategoryById(id);
    }

}
