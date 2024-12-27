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
import java.util.List;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private ProductService productService;

    public BookServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        List<Book> books = productService.getAllBooks();

        out.println("<html><body>");
        out.println("<h1>Книги</h1>");
        out.println("<p><a href='/create-book'>Добавить новую книгу</a></p>");
        out.println("<p>Список книг:</p>");

        if (books.isEmpty()) {
            out.println("<p>Книги не найдены.</p>");
        } else {
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Название</th>");
            out.println("<th>Автор</th>");
            out.println("<th>Издатель</th>");
            out.println("<th>Количество страниц</th>");
            out.println("<th>Количество</th>");
            out.println("<th>Действия</th>");
            out.println("</tr>");

            for (Book book : books) {
                out.println("<tr>");
                out.println("<td>" + book.getId() + "</td>");
                out.println("<td>" + book.getTitle() + "</td>");
                out.println("<td>" + book.getAuthor() + "</td>");
                out.println("<td>" + book.getPublisher() + "</td>");
                out.println("<td>" + book.getPageCounts() + "</td>");
                out.println("<td>" + book.getQuantity() + "</td>");
                out.println("<td>");
                out.println("<a href='/edit-book?id=" + book.getId() + "'>Редактировать</a> | ");
                out.println("<a href='/delete-book?id=" + book.getId() + "'>Удалить</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        }

        out.println("<br><a href='/main'>На главную</a>");
        out.println("</body></html>");
    }
}