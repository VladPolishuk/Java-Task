package Servlet.Session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Получаем текущую сессию
        HttpSession session = req.getSession(false);

        out.println("<html><body>");
        out.println("<h1>Главная страница</h1>");

        // Проверяем, авторизован ли пользователь
        if (session != null && session.getAttribute("username") != null) {
            out.println("<p>Добро пожаловать, " + session.getAttribute("username") + "!</p>");
            out.println("<form method='post' action='/logout'>");
            out.println("<input type='submit' value='Выйти'>");
            out.println("</form>");
        } else {
            resp.sendRedirect("/welcome");
        }

        out.println("<p>Выберите действие:</p>");
        out.println("<ul>");
        out.println("<li><a href='/books'>Просмотреть книги</a></li>");
        out.println("<li><a href='/journals'>Просмотреть журналы</a></li>");
        out.println("<li><a href='/newspapers'>Просмотреть газеты</a></li>");
        out.println("</ul>");
        out.println("</body></html>");
    }
}