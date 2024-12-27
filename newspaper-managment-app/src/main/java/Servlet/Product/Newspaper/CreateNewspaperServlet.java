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

@WebServlet("/create-newspaper")
public class CreateNewspaperServlet extends HttpServlet {
    private ProductService productService;

    public CreateNewspaperServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Создание новой газеты</h1>");
        out.println("<form method='post'>");
        out.println("Название: <input type='text' name='title'><br>");
        out.println("Номер: <input type='number' name='number'><br>");
        out.println("Дата выпуска: <input type='date' name='releaseDate'><br>");
        out.println("Количество: <input type='number' name='quantity'><br>");
        out.println("<input type='submit' value='Создать'>");
        out.println("</form>");
        out.println("<br><a href='/newspapers'>Назад к списку газет</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        int number = Integer.parseInt(req.getParameter("number"));
        LocalDate releaseDate = LocalDate.parse(req.getParameter("releaseDate"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        Newspaper newspaper = new Newspaper(title, number, releaseDate, quantity);
        productService.addNewspaper(newspaper);

        resp.sendRedirect("/newspapers");
    }
}
