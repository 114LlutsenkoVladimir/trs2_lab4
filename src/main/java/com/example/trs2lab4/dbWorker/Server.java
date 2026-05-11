package com.example.trs2lab4.dbWorker;

import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            QueryPreparer qp = new QueryPreparer();
            Naming.rebind("Query", qp);
            System.out.println("Сервер готовий.");
        } catch (Exception e) { e.printStackTrace(); }
    }
}
