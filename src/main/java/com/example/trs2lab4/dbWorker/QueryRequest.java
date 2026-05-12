package com.example.trs2lab4.dbWorker;


import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.entity.Manufacturer;
import com.example.trs2lab4.entity.ProductManufacturerCategory;

import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public interface QueryRequest extends Remote {
    List<ProductManufacturerCategory> findAll() throws RemoteException;

    List<ProductManufacturerCategory> findByCategoryId(Long id) throws RemoteException;

    List<ProductManufacturerCategory> findByManufacturerId(Long id) throws RemoteException;

    List<ProductManufacturerCategory> findByPriceBetween(BigDecimal from, BigDecimal to) throws RemoteException;

    void addProduct(ProductManufacturerCategory product) throws RemoteException;

    void deleteProduct(Long id) throws RemoteException;

    void updateProduct(ProductManufacturerCategory product) throws RemoteException;

    List<Category> findAllCategories() throws RemoteException;

    Category findCategoryById(Long id) throws RemoteException, RuntimeException;

    List<Manufacturer> findAllManufacturers() throws RemoteException;

    Manufacturer findManufacturerById(Long id) throws RemoteException, RuntimeException;
}
