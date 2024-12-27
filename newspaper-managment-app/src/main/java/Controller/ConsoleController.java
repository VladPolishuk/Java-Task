package Controller;

import DataModel.Book;
import DataModel.Journal;
import DataModel.Newspaper;
import DataModel.Publication;
import Service.InventoryService;
import Service.ProductService;
import Service.SalesService;
import Service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ConsoleController {
    private ProductService productService;
    private InventoryService inventoryService;
    private SalesService salesService;
    private UserService userService;
    private Scanner scanner;
    private boolean isAuthenticated = false;

    public ConsoleController(ProductService productService, InventoryService inventoryService, SalesService salesService, UserService userService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
        this.salesService = salesService;
        this.userService = userService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            if (!isAuthenticated) {
                showAuthMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showAuthMenu() {
        System.out.println("Добро пожаловать! Выберите действие:");
        System.out.println("1. Войти");
        System.out.println("2. Зарегистрироваться");
        System.out.println("3. Выйти");

        int choice = readIntInput("Введите номер действия: ");

        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Выход из программы.");
                System.exit(0);
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private void showMainMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1. Добавить товар");
        System.out.println("2. Редактировать товар");
        System.out.println("3. Удалить товар");
        System.out.println("4. Приемка товара");
        System.out.println("5. Продажа товара");
        System.out.println("6. Просмотреть все товары");
        System.out.println("7. Выйти из системы");

        int choice = readIntInput("Введите номер действия: ");

        switch (choice) {
            case 1:
                addProduct();
                break;
            case 2:
                editProduct();
                break;
            case 3:
                deleteProduct();
                break;
            case 4:
                receiveProduct();
                break;
            case 5:
                sellProduct();
                break;
            case 6:
                viewAllProducts();
                break;
            case 7:
                isAuthenticated = false;
                System.out.println("Вы вышли из системы.");
                break;
            default:
                System.out.println("Неверный выбор. Попробуйте снова.");
        }
    }

    private void login() {
        try {
            System.out.println("Введите имя пользователя:");
            String username = scanner.nextLine();
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();

            if (userService.authenticateUser(username, password)) {
                isAuthenticated = true;
                System.out.println("Вход выполнен успешно!");
            } else {
                System.out.println("Неверное имя пользователя или пароль.");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при входе: " + e.getMessage());
        }
    }

    private void register() {
        try {
            System.out.println("Введите имя пользователя:");
            String username = scanner.nextLine();
            System.out.println("Введите пароль:");
            String password = scanner.nextLine();
            System.out.println("Подтвердите пароль:");
            String confirmPassword = scanner.nextLine();

            if (userService.registerUser(username, password, confirmPassword)) {
                System.out.println("Регистрация прошла успешно! Теперь вы можете войти.");
            } else {
                System.out.println("Ошибка при регистрации. Возможно, пользователь уже существует.");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при регистрации: " + e.getMessage());
        }
    }

    private void addProduct() {
        try {
            System.out.println("Выберите тип товара:");
            System.out.println("1. Книга");
            System.out.println("2. Журнал");
            System.out.println("3. Газета");

            int type = readIntInput("Введите номер типа товара: ");

            System.out.println("Введите название:");
            String title = scanner.nextLine();

            int quantity = readIntInput("Введите количество: ");

            switch (type) {
                case 1:
                    System.out.println("Введите автора:");
                    String author = scanner.nextLine();
                    System.out.println("Введите издателя:");
                    String publisher = scanner.nextLine();
                    int pageCounts = readIntInput("Введите количество страниц: ");

                    Book book = new Book(title, author, publisher, pageCounts, quantity);
                    productService.addBook(book);
                    break;
                case 2:
                    int number = readIntInput("Введите номер: ");
                    LocalDate releaseDate = readDateInput("Введите дату выпуска (гггг-мм-дд): ");
                    int journalPageCounts = readIntInput("Введите количество страниц: ");

                    Journal journal = new Journal(title, number, releaseDate, journalPageCounts, quantity);
                    productService.addJournal(journal);
                    break;
                case 3:
                    int newspaperNumber = readIntInput("Введите номер: ");
                    LocalDate newspaperReleaseDate = readDateInput("Введите дату выпуска (гггг-мм-дд): ");

                    Newspaper newspaper = new Newspaper(title, newspaperNumber, newspaperReleaseDate, quantity);
                    productService.addNewspaper(newspaper);
                    break;
                default:
                    System.out.println("Неверный тип товара.");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при добавлении товара: " + e.getMessage());
        }
    }

    private void editProduct() {
        try {
            int id = readIntInput("Введите ID товара для редактирования: ");

            Publication publication = productService.getProductById(id);
            if (publication == null) {
                System.out.println("Товар не найден.");
                return;
            }

            System.out.println("Введите новое название:");
            String title = scanner.nextLine();
            int quantity = readIntInput("Введите новое количество: ");

            publication.setTitle(title);
            publication.setQuantity(quantity);

            if (publication instanceof Book) {
                System.out.println("Введите нового автора:");
                String author = scanner.nextLine();
                System.out.println("Введите нового издателя:");
                String publisher = scanner.nextLine();
                int pageCounts = readIntInput("Введите новое количество страниц: ");

                ((Book) publication).setAuthor(author);
                ((Book) publication).setPublisher(publisher);
                ((Book) publication).setPageCounts(pageCounts);
            } else if (publication instanceof Journal) {
                int number = readIntInput("Введите новый номер: ");
                LocalDate releaseDate = readDateInput("Введите новую дату выпуска (гггг-мм-дд): ");
                int journalPageCounts = readIntInput("Введите новое количество страниц: ");

                ((Journal) publication).setNumber(number);
                ((Journal) publication).setReleaseDate(releaseDate);
                ((Journal) publication).setPageCounts(journalPageCounts);
            } else if (publication instanceof Newspaper) {
                int number = readIntInput("Введите новый номер: ");
                LocalDate releaseDate = readDateInput("Введите новую дату выпуска (гггг-мм-дд): ");

                ((Newspaper) publication).setNumber(number);
                ((Newspaper) publication).setReleaseDate(releaseDate);
            }

            productService.updateProduct(publication);
            System.out.println("Товар успешно обновлен.");
        } catch (Exception e) {
            System.err.println("Ошибка при редактировании товара: " + e.getMessage());
        }
    }

    private void deleteProduct() {
        try {
            int id = readIntInput("Введите ID товара для удаления: ");

            Publication publication = productService.getProductById(id);
            if (publication == null) {
                System.out.println("Товар не найден.");
                return;
            }

            if (publication instanceof Book) {
                productService.deleteBook((Book) publication);
            } else if (publication instanceof Journal) {
                productService.deleteJournal((Journal) publication);
            } else if (publication instanceof Newspaper) {
                productService.deleteNewspaper((Newspaper) publication);
            }

            System.out.println("Товар успешно удален.");
        } catch (Exception e) {
            System.err.println("Ошибка при удалении товара: " + e.getMessage());
        }
    }

    private void receiveProduct() {
        try {
            int id = readIntInput("Введите ID товара для приемки: ");
            int quantity = readIntInput("Введите количество: ");

            inventoryService.receiveProduct(id, quantity);
            System.out.println("Товар успешно принят.");
        } catch (Exception e) {
            System.err.println("Ошибка при приемке товара: " + e.getMessage());
        }
    }

    private void sellProduct() {
        try {
            int id = readIntInput("Введите ID товара для продажи: ");
            int quantity = readIntInput("Введите количество: ");

            salesService.sellProduct(id, quantity);
            System.out.println("Товар успешно продан.");
        } catch (Exception e) {
            System.err.println("Ошибка при продаже товара: " + e.getMessage());
        }
    }

    private void viewAllProducts() {
        try {
            List<Publication> products = productService.getAllProducts();
            for (Publication product : products) {
                System.out.println(product);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при получении списка товаров: " + e.getMessage());
        }
    }

    // === Валидация ввода ===

    private int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: Введите целое число.");
            }
        }
    }

    private LocalDate readDateInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String dateStr = scanner.nextLine();
                return LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.err.println("Ошибка: Введите дату в формате гггг-мм-дд.");
            }
        }
    }
}