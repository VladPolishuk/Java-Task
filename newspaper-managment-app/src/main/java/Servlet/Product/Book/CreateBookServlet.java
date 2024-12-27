package Servlet.Product.Book;

import DataModel.Book;
import Service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/create-book")
public class CreateBookServlet extends HttpServlet {
    private ProductService productService;

    public CreateBookServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Создание новой книги</h1>");
        out.println("<form method='post'>");
        out.println("Название: <input type='text' name='title'><br>");
        out.println("Автор: <input type='text' name='author'><br>");
        out.println("Издатель: <input type='text' name='publisher'><br>");
        out.println("Количество страниц: <input type='number' name='pageCounts'><br>");
        out.println("Количество: <input type='number' name='quantity'><br>");
        out.println("<input type='submit' value='Создать'>");
        out.println("</form>");
        out.println("<br><a href='/books'>Назад к списку книг</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String publisher = req.getParameter("publisher");
        int pageCounts = Integer.parseInt(req.getParameter("pageCounts"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        Book book = new Book(title, author, publisher, pageCounts, quantity);
        productService.addBook(book);

        resp.sendRedirect("/books");
    }
}
