package Servlet.Product.Journal;

import DataModel.Journal;
import Service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/journals")
public class JournalServlet extends HttpServlet {
    private ProductService productService;

    public JournalServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        List<Journal> journals = productService.getAllJournals();

        out.println("<html><body>");
        out.println("<h1>Журналы</h1>");
        out.println("<p><a href='/create-journal'>Добавить новый журнал</a></p>");
        out.println("<p>Список журналов:</p>");

        if (journals.isEmpty()) {
            out.println("<p>Журналы не найдены.</p>");
        } else {
            out.println("<table border='1'>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Название</th>");
            out.println("<th>Номер</th>");
            out.println("<th>Дата выпуска</th>");
            out.println("<th>Количество страниц</th>");
            out.println("<th>Количество</th>");
            out.println("<th>Действия</th>");
            out.println("</tr>");

            for (Journal journal : journals) {
                out.println("<tr>");
                out.println("<td>" + journal.getId() + "</td>");
                out.println("<td>" + journal.getTitle() + "</td>");
                out.println("<td>" + journal.getNumber() + "</td>");
                out.println("<td>" + journal.getReleaseDate() + "</td>");
                out.println("<td>" + journal.getPageCounts() + "</td>");
                out.println("<td>" + journal.getQuantity() + "</td>");
                out.println("<td>");
                out.println("<a href='/edit-journal?id=" + journal.getId() + "'>Редактировать</a> | ");
                out.println("<a href='/delete-journal?id=" + journal.getId() + "'>Удалить</a>");
                out.println("</td>");
                out.println("</tr>");
            }

            out.println("</table>");
        }

        out.println("<br><a href='/main'>На главную</a>");
        out.println("</body></html>");
    }
}