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
import java.time.LocalDate;

@WebServlet("/edit-journal")
public class EditJournalServlet extends HttpServlet {
    private ProductService productService;

    public EditJournalServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Journal journal = productService.getJournalById(id);

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Редактирование журнала</h1>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='id' value='" + journal.getId() + "'>");
        out.println("Название: <input type='text' name='title' value='" + journal.getTitle() + "'><br>");
        out.println("Номер: <input type='number' name='number' value='" + journal.getNumber() + "'><br>");
        out.println("Дата выпуска: <input type='date' name='releaseDate' value='" + journal.getReleaseDate() + "'><br>");
        out.println("Количество страниц: <input type='number' name='pageCounts' value='" + journal.getPageCounts() + "'><br>");
        out.println("Количество: <input type='number' name='quantity' value='" + journal.getQuantity() + "'><br>");
        out.println("<input type='submit' value='Сохранить'>");
        out.println("</form>");
        out.println("<br><a href='/journals'>Назад к списку журналов</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        int number = Integer.parseInt(req.getParameter("number"));
        LocalDate releaseDate = LocalDate.parse(req.getParameter("releaseDate"));
        int pageCounts = Integer.parseInt(req.getParameter("pageCounts"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        Journal journal = new Journal(id, title, number, releaseDate, pageCounts, quantity);
        productService.updateJournal(journal);

        resp.sendRedirect("/journals");
    }
}
