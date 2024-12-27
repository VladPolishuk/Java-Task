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

@WebServlet("/delete-journal")
public class DeleteJournalServlet extends HttpServlet {
    private ProductService productService;

    public DeleteJournalServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Journal journal = productService.getJournalById(id);

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Удаление журнала</h1>");
        out.println("<p>Вы уверены, что хотите удалить журнал \"" + journal.getTitle() + "\"?</p>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='id' value='" + journal.getId() + "'>");
        out.println("<input type='submit' value='Удалить'>");
        out.println("</form>");
        out.println("<br><a href='/journals'>Назад к списку журналов</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Journal journal = productService.getJournalById(id);
        productService.deleteJournal(journal);

        resp.sendRedirect("/journals");
    }
}
