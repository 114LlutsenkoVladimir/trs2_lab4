package com.example.trs2lab4.dbWorker;


import com.example.trs2lab4.entity.ProductManufacturerCategory;
import com.example.trs2lab4.service.ProductService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class QueryPreparer extends UnicastRemoteObject implements QueryRequest {
    private ProductService productService = new ProductService();

    protected QueryPreparer() throws RemoteException { super(); }

    @Override
    public List<ProductManufacturerCategory> findAll() throws RemoteException {
        return productService.findAll();
    }
}
