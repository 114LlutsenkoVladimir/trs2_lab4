package com.example.trs2lab4.controller.product;

import com.example.trs2lab4.dbWorker.QueryRequest;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.example.trs2lab4.controller.MainController;
import com.example.trs2lab4.controller.MainControllerAware;
import com.example.trs2lab4.controller.ShowError;
import com.example.trs2lab4.entity.ProductManufacturerCategory;
import com.example.trs2lab4.entity.Category;
import com.example.trs2lab4.entity.Manufacturer;
import com.example.trs2lab4.service.CategoryService;
import com.example.trs2lab4.service.ManufacturerService;
import com.example.trs2lab4.service.ProductService;


import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public class ProductController implements MainController, ShowError {

    private CategoryService categoryService = new CategoryService();
    private ManufacturerService manufacturerService = new ManufacturerService();

    private QueryRequest remoteService;

 // Spring Context


    public ProductController() {
    }

    //====================MySql Table===========================
    @FXML
    private TableView<ProductManufacturerCategory> table;
    @FXML
    private TableColumn<ProductManufacturerCategory, Long> idColumn;
    @FXML
    private TableColumn<ProductManufacturerCategory, String> nameColumn;
    @FXML
    private TableColumn<ProductManufacturerCategory, String> priceColumn;
    @FXML
    private TableColumn<ProductManufacturerCategory, Category> categoryColumn;
    @FXML
    private TableColumn<ProductManufacturerCategory, Manufacturer> manufacturerColumn;
    @FXML
    private TableColumn<ProductManufacturerCategory, Void> actionColumn;
    //====================MySql Table===========================


    @FXML
    public void initialize() {
        initMysqlTable();
        try {
            remoteService = (QueryRequest) Naming.lookup("//localhost/Query");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initMysqlTable() {
        // 1. Предварительная загрузка списков (чтобы не вызывать сервис в каждой ячейке)
        ObservableList<Category> allCategories = FXCollections.observableArrayList(categoryService.findAll());
        ObservableList<Manufacturer> allManufacturers = FXCollections.observableArrayList(manufacturerService.findAll());

        // =====================
        // ID
        // =====================
        idColumn.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getId()));

        // =====================
        // NAME (TextField)
        // =====================
        nameColumn.setCellFactory(col -> new TableCell<>() {
            private final TextField textField = new TextField();
            {
                // Слушатель изменений текста: записываем в объект при каждом вводе
                textField.textProperty().addListener((obs, oldVal, newVal) -> {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    if (product != null) product.setName(newVal);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    textField.setText(getTableRow().getItem().getName());
                    setGraphic(textField);
                }
            }
        });

        // =====================
        // PRICE (TextField)
        // =====================
        priceColumn.setCellFactory(col -> new TableCell<>() {
            private final TextField textField = new TextField();
            {
                textField.textProperty().addListener((obs, oldVal, newVal) -> {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    if (product != null) {
                        try {
                            product.setPrice(new BigDecimal(newVal));
                        } catch (NumberFormatException ignored) {
                            // Можно добавить красную рамку полю, если введено не число
                        }
                    }
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    BigDecimal price = getTableRow().getItem().getPrice();
                    textField.setText(price != null ? price.toString() : "");
                    setGraphic(textField);
                }
            }
        });

        // =====================
        // CATEGORY (ComboBox)
        // =====================
        categoryColumn.setCellFactory(col -> new TableCell<>() {
            private final ComboBox<Category> comboBox = new ComboBox<>(allCategories);
            {
                // Красивое отображение имени категории в списке
                comboBox.setCellFactory(cb -> new ListCell<>() {
                    @Override protected void updateItem(Category item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : item.getName());
                    }
                });
                comboBox.setButtonCell(comboBox.getCellFactory().call(null));

                // Сохранение выбора в объект
                comboBox.setOnAction(e -> {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    if (product != null && comboBox.getValue() != null) {
                        product.setCategoryId(comboBox.getValue().getId());
                    }
                });
            }

            @Override
            protected void updateItem(Category item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    // Устанавливаем текущее значение из объекта
                    allCategories.stream()
                            .filter(c -> c.getId().equals(product.getCategoryId()))
                            .findFirst()
                            .ifPresent(comboBox::setValue);
                    setGraphic(comboBox);
                }
            }
        });

        // =====================
        // MANUFACTURER (ComboBox)
        // =====================
        manufacturerColumn.setCellFactory(col -> new TableCell<>() {
            private final ComboBox<Manufacturer> comboBox = new ComboBox<>(allManufacturers);
            {
                comboBox.setCellFactory(cb -> new ListCell<>() {
                    @Override protected void updateItem(Manufacturer item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : item.getName());
                    }
                });
                comboBox.setButtonCell(comboBox.getCellFactory().call(null));

                comboBox.setOnAction(e -> {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    if (product != null && comboBox.getValue() != null) {
                        product.setManufacturerId(comboBox.getValue().getId());
                    }
                });
            }

            @Override
            protected void updateItem(Manufacturer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    allManufacturers.stream()
                            .filter(m -> m.getId().equals(product.getManufacturerId()))
                            .findFirst()
                            .ifPresent(comboBox::setValue);
                    setGraphic(comboBox);
                }
            }
        });


        // =====================
        // ACTION (Кнопка Update)
        // =====================
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button updateBtn = new Button("Update");
            private final Button deleteBtn = new Button("Delete");
            private final HBox container = new HBox(10, updateBtn, deleteBtn); // 10 - отступ между кнопками

            {
                // Логика кнопки Update
                updateBtn.setOnAction(event -> {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    if (product != null) {
                        updateProduct(product);
                    }
                });

                // Логика кнопки Delete
                deleteBtn.setOnAction(event -> {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    if (product != null) {
                        // Вызываем вашу функцию удаления по ID
                        deleteProduct(product.getId());

                        // Удаляем строку из таблицы визуально
                        getTableView().getItems().remove(product);
                    }
                });

                container.setAlignment(javafx.geometry.Pos.CENTER);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });

    }


    public void updateTable(List<ProductManufacturerCategory> products) {
        table.getItems().setAll(products);
    }

    private<T> T openAddWindow(String fxmlPath, String title) throws IOException {
        var resource = getClass().getResource(fxmlPath);
        if (resource == null) throw new RuntimeException("FXML не найден: " + fxmlPath);

        FXMLLoader loader = new FXMLLoader(resource);

        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();

        return loader.getController();
    }

    private void openDependentWindow(String fxmlPath, String title) {
        try {
            // Установка главного контроллера
            MainControllerAware<ProductController> controller = openAddWindow(fxmlPath, title);
            controller.setMainController(this);
            controller.setRemoteService(remoteService);
        } catch (IOException e) {
            System.err.println("CRITICAL ERROR LOADING FXML:");
            e.printStackTrace();
        }
    }

    public void findAll() throws RemoteException {
        updateTable(remoteService.findAll());
    }

    public void findByManufacturerWindow() {
      openDependentWindow("/com/example/trs2lab4/find-by-manufacturer-window.fxml",
              "Find by manufacturer");
    }

    public void findByManufacturer(Long manufacturerId) {
         try {
             updateTable(remoteService.findByManufacturerId(manufacturerId));
         } catch (RemoteException e) {
             showError("remote exception");
         }
    }

    public void findByPriceWindow() {
        openDependentWindow("/com/example/trs2lab4/find-by-price-window.fxml",
                "Find by price");
    }

    public void findByPrice(BigDecimal from, BigDecimal to) {
        try {
            updateTable(remoteService.findByPriceBetween(from, to));
        } catch (RemoteException e) {
            showError("remote exception");
        }
    }

    public void findByCategoryWindow() {
        openDependentWindow("/com/example/trs2lab4/find-by-category-window.fxml",
                "Find by category");
    }

    public void findByCategory(Long categoryId) {
        try{
            updateTable(remoteService.findByCategoryId(categoryId));
        } catch (RemoteException e) {
            showError("remote exception");
        }
    }

    public void addProductWindow() {
        openDependentWindow("/com/example/trs2lab4/add-product-window.fxml",
                "Add product");
    }

    public void addProduct(ProductManufacturerCategory product) {
        try {
            remoteService.addProduct(product);
        } catch (RemoteException e) {
            showError(e.getMessage());
        }

    }

    public void updateProduct(ProductManufacturerCategory product) {
        try {
            remoteService.updateProduct(product);
        } catch (RemoteException e) {
            showError(e.getMessage());
        }
    }

    public void deleteProduct(Long id) {
        try {
            remoteService.deleteProduct(id);
            findAll();
        } catch (RemoteException e) {
            showError(e.getMessage());
        }
    }

}
