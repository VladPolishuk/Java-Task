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

@WebServlet("/delete-newspaper")
public class DeleteNewspaperServlet extends HttpServlet {
    private ProductService productService;

    public DeleteNewspaperServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Newspaper newspaper = productService.getNewspaperById(id);

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Удаление газеты</h1>");
        out.println("<p>Вы уверены, что хотите удалить газету \"" + newspaper.getTitle() + "\"?</p>");
        out.println("<form method='post'>");
        out.println("<input type='hidden' name='id' value='" + newspaper.getId() + "'>");
        out.println("<input type='submit' value='Удалить'>");
        out.println("</form>");
        out.println("<br><a href='/newspapers'>Назад к списку газет</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Newspaper newspaper = productService.getNewspaperById(id);
        productService.deleteNewspaper(newspaper);

        resp.sendRedirect("/newspapers");
    }
}
