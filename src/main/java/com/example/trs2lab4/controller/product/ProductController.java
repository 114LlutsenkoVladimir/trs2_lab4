package com.example.trs2lab4.controller.product;

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
import org.example.trs2_lab2_desktop.controller.MainController;
import org.example.trs2_lab2_desktop.controller.MainControllerAware;
import org.example.trs2_lab2_desktop.controller.ShowError;
import org.example.trs2_lab2_desktop.dbType.DbSelectionContext;
import org.example.trs2_lab2_desktop.dbType.DbType;
import org.example.trs2_lab2_desktop.dto.ProductManufacturerCategory;
import org.example.trs2_lab2_desktop.entity.Category;
import org.example.trs2_lab2_desktop.entity.Manufacturer;
import org.example.trs2_lab2_desktop.entity.Product;
import org.example.trs2_lab2_desktop.service.CategoryService;
import org.example.trs2_lab2_desktop.service.ManufacturerService;
import org.example.trs2_lab2_desktop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Component
public class ProductController implements MainController, ShowError {

    private ProductService service;
    private CategoryService categoryService;
    private ManufacturerService manufacturerService;
    private DbSelectionContext context;

    @Autowired
    private ApplicationContext applicationContext; // Spring Context

    public ProductController(ProductService service,
                             CategoryService categoryService,
                             ManufacturerService manufacturerService,
                             DbSelectionContext context) {
        this.service = service;
        this.categoryService = categoryService;
        this.manufacturerService = manufacturerService;
        this.context = context;
    }

    //====================MySql Table===========================
    @FXML
    private TableView<ProductManufacturerCategory> mySqlProductsTable;
    @FXML
    private TableColumn<ProductManufacturerCategory, Long> idColumnMySql;
    @FXML
    private TableColumn<ProductManufacturerCategory, String> nameColumnMySql;
    @FXML
    private TableColumn<ProductManufacturerCategory, String> priceColumnMySql;
    @FXML
    private TableColumn<ProductManufacturerCategory, Category> categoryColumnMySql;
    @FXML
    private TableColumn<ProductManufacturerCategory, Manufacturer> manufacturerColumnMySql;
    @FXML
    private TableColumn<ProductManufacturerCategory, Void> actionColumnMySql;
    //====================MySql Table===========================


    //====================PostgreSql Table===========================
    @FXML
    private TableView<ProductManufacturerCategory> postgresqlProducts;
    @FXML
    private TableColumn<ProductManufacturerCategory, Long> idColumnPostgresql;
    @FXML
    private TableColumn<ProductManufacturerCategory, String> nameColumnPostgresql;
    @FXML
    private TableColumn<ProductManufacturerCategory, String> priceColumnPostgresql;
    @FXML
    private TableColumn<ProductManufacturerCategory, Category> categoryColumnPostgresql;
    @FXML
    private TableColumn<ProductManufacturerCategory, Manufacturer> manufacturerColumnPostgresql;
    @FXML
    private TableColumn<ProductManufacturerCategory, Void> actionColumnPostgresql;
    //====================PostgreSql Table===========================


    @FXML
    public void initialize() {
        initMysqlTable();
        initPostgresqlTable();
    }

    public void initMysqlTable() {
        // 1. Предварительная загрузка списков (чтобы не вызывать сервис в каждой ячейке)
        ObservableList<Category> allCategories = FXCollections.observableArrayList(categoryService.findAll());
        ObservableList<Manufacturer> allManufacturers = FXCollections.observableArrayList(manufacturerService.findAll());

        // =====================
        // ID
        // =====================
        idColumnMySql.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getId()));

        // =====================
        // NAME (TextField)
        // =====================
        nameColumnMySql.setCellFactory(col -> new TableCell<>() {
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
        priceColumnMySql.setCellFactory(col -> new TableCell<>() {
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
        categoryColumnMySql.setCellFactory(col -> new TableCell<>() {
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
        manufacturerColumnMySql.setCellFactory(col -> new TableCell<>() {
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
        actionColumnMySql.setCellFactory(col -> new TableCell<>() {
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

    public void initPostgresqlTable() {
        // 1. Предварительная загрузка списков
        ObservableList<Category> allCategories = FXCollections.observableArrayList(categoryService.findAll());
        ObservableList<Manufacturer> allManufacturers = FXCollections.observableArrayList(manufacturerService.findAll());

        // =====================
        // ID
        // =====================
        idColumnPostgresql.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getId()));

        // =====================
        // NAME (TextField)
        // =====================
        nameColumnPostgresql.setCellFactory(col -> new TableCell<>() {
            private final TextField textField = new TextField();
            {
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
        priceColumnPostgresql.setCellFactory(col -> new TableCell<>() {
            private final TextField textField = new TextField();
            {
                textField.textProperty().addListener((obs, oldVal, newVal) -> {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    if (product != null) {
                        try {
                            product.setPrice(new BigDecimal(newVal));
                        } catch (NumberFormatException ignored) {}
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
        categoryColumnPostgresql.setCellFactory(col -> new TableCell<>() {
            private final ComboBox<Category> comboBox = new ComboBox<>(allCategories);
            {
                comboBox.setCellFactory(cb -> new ListCell<>() {
                    @Override protected void updateItem(Category item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : item.getName());
                    }
                });
                comboBox.setButtonCell(comboBox.getCellFactory().call(null));

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
        manufacturerColumnPostgresql.setCellFactory(col -> new TableCell<>() {
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
        // ACTION (Кнопки Update/Delete)
        // =====================
        actionColumnPostgresql.setCellFactory(col -> new TableCell<>() {
            private final Button updateBtn = new Button("Update");
            private final Button deleteBtn = new Button("Delete");
            private final HBox container = new HBox(10, updateBtn, deleteBtn);

            {
                updateBtn.setOnAction(event -> {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    if (product != null) updateProduct(product);
                });

                deleteBtn.setOnAction(event -> {
                    ProductManufacturerCategory product = getTableRow().getItem();
                    if (product != null) {
                        deleteProduct(product.getId());
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

    public void updateRelevantTable(List<ProductManufacturerCategory> products) {
        if(context.getCurrentDbType() == DbType.MY_SQL) {
            mySqlProductsTable.getItems().setAll(products);
            postgresqlProducts.getItems().clear();
        }
        else {
            postgresqlProducts.getItems().setAll(products);
            mySqlProductsTable.getItems().clear();
        }
    }

    public void updateBothTables() {
        mySqlProductsTable.getItems().addAll(service.findAllDto(DbType.MY_SQL));
        postgresqlProducts.getItems().addAll(service.findAllDto(DbType.POSTGRES));
    }

    private<T> T openAddWindow(String fxmlPath, String title) throws IOException {
        var resource = getClass().getResource(fxmlPath);
        if (resource == null) throw new RuntimeException("FXML не найден: " + fxmlPath);

        FXMLLoader loader = new FXMLLoader(resource);

        // КЛЮЧЕВАЯ СТРОКА: говорим лоадеру просить контроллеры у Spring
        loader.setControllerFactory(applicationContext::getBean);

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
        } catch (IOException e) {
            showError(e.getMessage());
        }
    }

    public void findAll() {
        updateRelevantTable(service.findAllDto());
    }

    public void findByManufacturerWindow() {
      openDependentWindow("/org/example/trs2_lab2_desktop/find-by-manufacturer-window.fxml",
              "Find by manufacturer");
    }

    public void findByManufacturer(Long manufacturerId) {
        updateRelevantTable(service.findByManufacturerIdDto(manufacturerId));
    }

    public void findByPriceWindow() {
        openDependentWindow("/org/example/trs2_lab2_desktop/find-by-price-window.fxml",
                "Find by price");
    }

    public void findByPrice(BigDecimal from, BigDecimal to) {
        updateRelevantTable(service.findByPriceBetweenDto(from, to));
    }

    public void findByCategoryWindow() {
        openDependentWindow("/org/example/trs2_lab2_desktop/find-by-category-window.fxml",
                "Find by category");
    }

    public void findByCategory(Long categoryId) {
        updateRelevantTable(service.findByCategoryIdDto(categoryId));
    }

    public void createProductWindow() {
        openDependentWindow("/org/example/trs2_lab2_desktop/add-product-window.fxml",
                "Add product");
    }

    public void addProduct(Product product) {
        service.create(product);
        findAll();
    }

    public void updateProduct(ProductManufacturerCategory product) {
        service.updateByDto(product);
        findAll();
    }

    public void deleteProduct(Long id) {
        service.delete(id);
        findAll();
    }

    public void dbSelectionWindow() {
        try {
            openAddWindow("/org/example/trs2_lab2_desktop/select-db.fxml",
                    "Db selection");
        } catch (IOException e) {
            showError(e.getMessage());
        }
    }

    public void transactionWindow() {
        openDependentWindow("/org/example/trs2_lab2_desktop/transaction.fxml",
                "Transaction");
    }
}
