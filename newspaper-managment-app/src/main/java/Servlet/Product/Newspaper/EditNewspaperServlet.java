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
import java.time.LocalDate;

@WebServlet("/edit-newspaper")
public class EditNewspaperServlet extends HttpServlet {
    private ProductService productService;

    public EditNewspaperServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Newspaper newspaper = productService.getNewspaperById(id);

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Редактирование газеты</h1>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='id' value='" + newspaper.getId() + "'>");
        out.println("Название: <input type='text' name='title' value='" + newspaper.getTitle() + "'><br>");
        out.println("Номер: <input type='number' name='number' value='" + newspaper.getNumber() + "'><br>");
        out.println("Дата выпуска: <input type='date' name='releaseDate' value='" + newspaper.getReleaseDate() + "'><br>");
        out.println("Количество: <input type='number' name='quantity' value='" + newspaper.getQuantity() + "'><br>");
        out.println("<input type='submit' value='Сохранить'>");
        out.println("</form>");
        out.println("<br><a href='/newspapers'>Назад к списку газет</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        int number = Integer.parseInt(req.getParameter("number"));
        LocalDate releaseDate = LocalDate.parse(req.getParameter("releaseDate"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        Newspaper newspaper = new Newspaper(id, title, number, releaseDate, quantity);
        productService.updateNewspaper(newspaper);

        resp.sendRedirect("/newspapers");
    }
}
