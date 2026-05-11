package com.example.trs2lab4.dbWorker;


import com.example.trs2lab4.entity.ProductManufacturerCategory;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface QueryRequest extends Remote {
    List<ProductManufacturerCategory> findAll() throws RemoteException;
}
