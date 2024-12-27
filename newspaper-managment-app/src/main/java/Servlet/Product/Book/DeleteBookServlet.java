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

@WebServlet("/delete-book")
public class DeleteBookServlet extends HttpServlet {
    private ProductService productService;

    public DeleteBookServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = productService.getBookById(id);

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Удаление книги</h1>");
        out.println("<p>Вы уверены, что хотите удалить книгу \"" + book.getTitle() + "\"?</p>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='id' value='" + book.getId() + "'>");
        out.println("<input type='submit' value='Удалить'>");
        out.println("</form>");
        out.println("<br><a href='/books'>Назад к списку книг</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = productService.getBookById(id);
        productService.deleteBook(book);

        resp.sendRedirect("/books");
    }
}