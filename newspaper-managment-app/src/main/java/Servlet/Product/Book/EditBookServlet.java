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

@WebServlet("/edit-book")
public class EditBookServlet extends HttpServlet {
    private ProductService productService;

    public EditBookServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = productService.getBookById(id);

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Редактирование книги</h1>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='id' value='" + book.getId() + "'>");
        out.println("Название: <input type='text' name='title' value='" + book.getTitle() + "'><br>");
        out.println("Автор: <input type='text' name='author' value='" + book.getAuthor() + "'><br>");
        out.println("Издатель: <input type='text' name='publisher' value='" + book.getPublisher() + "'><br>");
        out.println("Количество страниц: <input type='number' name='pageCounts' value='" + book.getPageCounts() + "'><br>");
        out.println("Количество: <input type='number' name='quantity' value='" + book.getQuantity() + "'><br>");
        out.println("<input type='submit' value='Сохранить'>");
        out.println("</form>");
        out.println("<br><a href='/books'>Назад к списку книг</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String publisher = req.getParameter("publisher");
        int pageCounts = Integer.parseInt(req.getParameter("pageCounts"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        Book book = new Book(id, title, author, publisher, pageCounts, quantity);
        productService.updateBook(book);

        resp.sendRedirect("/books");
    }
}