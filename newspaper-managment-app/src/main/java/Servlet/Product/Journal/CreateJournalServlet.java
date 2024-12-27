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

@WebServlet("/create-journal")
public class CreateJournalServlet extends HttpServlet {
    private ProductService productService;

    public CreateJournalServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Создание нового журнала</h1>");
        out.println("<form method='post'>");
        out.println("Название: <input type='text' name='title'><br>");
        out.println("Номер: <input type='number' name='number'><br>");
        out.println("Дата выпуска: <input type='date' name='releaseDate'><br>");
        out.println("Количество страниц: <input type='number' name='pageCounts'><br>");
        out.println("Количество: <input type='number' name='quantity'><br>");
        out.println("<input type='submit' value='Создать'>");
        out.println("</form>");
        out.println("<br><a href='/journals'>Назад к списку журналов</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        int number = Integer.parseInt(req.getParameter("number"));
        LocalDate releaseDate = LocalDate.parse(req.getParameter("releaseDate"));
        int pageCounts = Integer.parseInt(req.getParameter("pageCounts"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        Journal journal = new Journal(title, number, releaseDate, pageCounts, quantity);
        productService.addJournal(journal);

        resp.sendRedirect("/journals");
    }
}
