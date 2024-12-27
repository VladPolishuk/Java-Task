package Servlet.Product.Newspaper;

import DataModel.Newspaper;
import Service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/newspapers")
public class NewspaperServlet extends HttpServlet {
    private ProductService productService;

    public NewspaperServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        List<Newspaper> newspapers = productService.getAllNewspapers();

        out.println("<html><body>");
        out.println("<h1>Газеты</h1>");
        out.println("<p><a href='/create-newspaper'>Добавить новую газету</a></p>");
        out.println("<p>Список газет:</p>");

        if (newspapers.isEmpty()) {
            out.println("<p>Газеты не найдены.</p>");
        } else {
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Название</th>");
            out.println("<th>Номер</th>");
            out.println("<th>Дата выпуска</th>");
            out.println("<th>Количество</th>");
            out.println("<th>Действия</th>");
            out.println("</tr>");

            for (Newspaper newspaper : newspapers) {
                out.println("<tr>");
                out.println("<td>" + newspaper.getId() + "</td>");
                out.println("<td>" + newspaper.getTitle() + "</td>");
                out.println("<td>" + newspaper.getNumber() + "</td>");
                out.println("<td>" + newspaper.getReleaseDate() + "</td>");
                out.println("<td>" + newspaper.getQuantity() + "</td>");
                out.println("<td>");
                out.println("<a href='/edit-newspaper?id=" + newspaper.getId() + "'>Редактировать</a> | ");
                out.println("<a href='/delete-newspaper?id=" + newspaper.getId() + "'>Удалить</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        }

        out.println("<br><a href='/main'>На главную</a>");
        out.println("</body></html>");
    }
}