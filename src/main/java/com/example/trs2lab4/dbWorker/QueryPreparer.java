package com.example.trs2lab4.dbWorker;


import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.entity.Manufacturer;
import com.example.trs2lab4.entity.ProductManufacturerCategory;
import com.example.trs2lab4.service.ProductService;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Optional;

public class QueryPreparer extends UnicastRemoteObject implements QueryRequest {

    private DBWorker dbWorker = new DBWorker();

    protected QueryPreparer() throws RemoteException { super(); }

    @Override
    public List<ProductManufacturerCategory> findAll() throws RemoteException {
        return dbWorker.fetchAllProducts();
    }

    @Override
    public List<ProductManufacturerCategory> findByCategoryId(Long id) throws RemoteException {
        return dbWorker.fetchProductsByCategoryId(id);
    }

    @Override
    public List<ProductManufacturerCategory> findByManufacturerId(Long id) throws RemoteException {
        return dbWorker.fetchProductsByManufacturerId(id);
    }

    @Override
    public List<ProductManufacturerCategory> findByPriceBetween(BigDecimal from, BigDecimal to) throws RemoteException {
        return dbWorker.fetchProductsByPriceBetween(from, to);
    }

    @Override
    public void addProduct(ProductManufacturerCategory product) {
        dbWorker.addProduct(product);
    }

    @Override
    public void deleteProduct(Long id) {
        dbWorker.deleteProduct(id);
    }

    @Override
    public void updateProduct(ProductManufacturerCategory product) throws RemoteException {
        dbWorker.updateProduct(product);
    }

    @Override
    public List<Category> findAllCategories() throws RemoteException {
        return dbWorker.fetchAllCategories();
    }

    @Override
    public Category findCategoryById(Long id) throws RemoteException, RuntimeException {
        return dbWorker.findCategoryById(id);
    }

    @Override
    public List<Manufacturer> findAllManufacturers() throws RemoteException {
        return dbWorker.fetchAllManufacturers();
    }

    @Override
    public Manufacturer findManufacturerById(Long id) throws RemoteException, RuntimeException {
        return dbWorker.findManufacturerById(id);
    }

}
