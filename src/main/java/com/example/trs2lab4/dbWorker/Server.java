package com.example.trs2lab4.dbWorker;

import java.rmi.Naming;

public class Server {
    public static void main(String[] args) {
        try {
            // 1. Пытаемся создать реестр в памяти этого процесса
            try {
                java.rmi.registry.LocateRegistry.createRegistry(1099);
                System.out.println("RMI Registry started on port 1099");
            } catch (java.rmi.RemoteException e) {
                System.out.println("RMI Registry already running (it's okay)");
            }

            // 2. Создаем экземпляр вашего удаленного объекта
            QueryPreparer qp = new QueryPreparer();

            // 3. Регистрируем его под именем "Query"
            java.rmi.Naming.rebind("//localhost/Query", qp);

            System.out.println(">>> SERVER IS READY <<<");
            System.out.println("Waiting for client requests...");

        } catch (Exception e) {
            System.err.println("Server exception:");
            e.printStackTrace();
        }
    }
}
