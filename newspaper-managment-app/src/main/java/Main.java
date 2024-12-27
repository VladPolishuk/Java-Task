import Controller.ConsoleController;
import Repository.*;
import Repository.Postgres.*;
import Repository.InMemory.*;
import Service.*;
import Servlet.*;
import Servlet.Product.Book.BookServlet;
import Servlet.Product.Book.CreateBookServlet;
import Servlet.Product.Book.DeleteBookServlet;
import Servlet.Product.Book.EditBookServlet;
import Servlet.Product.Journal.CreateJournalServlet;
import Servlet.Product.Journal.DeleteJournalServlet;
import Servlet.Product.Journal.EditJournalServlet;
import Servlet.Product.Journal.JournalServlet;
import Servlet.Product.Newspaper.CreateNewspaperServlet;
import Servlet.Product.Newspaper.DeleteNewspaperServlet;
import Servlet.Product.Newspaper.EditNewspaperServlet;
import Servlet.Product.Newspaper.NewspaperServlet;
import Servlet.Session.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main {
    public static void main(String[] args) throws Exception {
        runJettyServer();
    }
    /**
     1 аттестация
     */
    public static void runConsoleWithInMemoryDb() {
        BookRepository bookRepository = new BookRepositoryInMemoryImpl();
        JournalRepository journalRepository = new JournalRepositoryInMemoryImpl();
        NewspaperRepository newspaperRepository = new NewspaperRepositoryInMemoryImpl();
        UserRepository userRepository = new UserRepositoryInMemoryImpl();

        ProductService productService = new ProductService(bookRepository, journalRepository, newspaperRepository);
        InventoryService inventoryService = new InventoryService(productService);
        SalesService salesService = new SalesService(inventoryService);
        UserService userService = new UserService(userRepository);

        ConsoleController consoleController = new ConsoleController(productService, inventoryService, salesService, userService);
        consoleController.start();
    }
    /**
     2 аттестация
     */
    public static void runConsoleWithPostgresDb() {
        BookRepository bookRepository = new BookRepositoryJdbcImpl();
        JournalRepository journalRepository = new JournalRepositoryJdbcImpl();
        NewspaperRepository newspaperRepository = new NewspaperRepositoryJdbcImpl();
        UserRepository userRepository = new UserRepositoryJdbcImpl();

        ProductService productService = new ProductService(bookRepository, journalRepository, newspaperRepository);
        InventoryService inventoryService = new InventoryService(productService);
        SalesService salesService = new SalesService(inventoryService);
        UserService userService = new UserService(userRepository);

        ConsoleController consoleController = new ConsoleController(productService, inventoryService, salesService, userService);
        consoleController.start();
    }
    /**
     3 аттестация
     */
    public static void runJettyServer() throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        BookRepository bookRepository = new BookRepositoryJdbcImpl();
        JournalRepository journalRepository = new JournalRepositoryJdbcImpl();
        NewspaperRepository newspaperRepository = new NewspaperRepositoryJdbcImpl();
        UserRepository userRepository = new UserRepositoryJdbcImpl();

        ProductService productService = new ProductService(bookRepository, journalRepository, newspaperRepository);
        InventoryService inventoryService = new InventoryService(productService);
        SalesService salesService = new SalesService(inventoryService);
        UserService userService = new UserService(userRepository);

        context.addServlet(new ServletHolder(new WelcomeServlet()), "/welcome");
        context.addServlet(new ServletHolder(new RegisterServlet(userService)), "/register");
        context.addServlet(new ServletHolder(new LoginServlet(userService)), "/login");
        context.addServlet(new ServletHolder(new LogoutServlet()), "/logout");
        context.addServlet(new ServletHolder(new MainServlet()), "/main");

        // Сервлеты для книг
        context.addServlet(new ServletHolder(new BookServlet(productService)), "/books");
        context.addServlet(new ServletHolder(new CreateBookServlet(productService)), "/create-book");
        context.addServlet(new ServletHolder(new EditBookServlet(productService)), "/edit-book");
        context.addServlet(new ServletHolder(new DeleteBookServlet(productService)), "/delete-book");

        // Сервлеты для журналов
        context.addServlet(new ServletHolder(new JournalServlet(productService)), "/journals");
        context.addServlet(new ServletHolder(new CreateJournalServlet(productService)), "/create-journal");
        context.addServlet(new ServletHolder(new EditJournalServlet(productService)), "/edit-journal");
        context.addServlet(new ServletHolder(new DeleteJournalServlet(productService)), "/delete-journal");

        // Сервлеты для газет
        context.addServlet(new ServletHolder(new NewspaperServlet(productService)), "/newspapers");
        context.addServlet(new ServletHolder(new CreateNewspaperServlet(productService)), "/create-newspaper");
        context.addServlet(new ServletHolder(new EditNewspaperServlet(productService)), "/edit-newspaper");
        context.addServlet(new ServletHolder(new DeleteNewspaperServlet(productService)), "/delete-newspaper");

        server.setHandler(context);
        server.start();
        System.out.println("http://localhost:8080/welcome");
        server.join();
    }
}