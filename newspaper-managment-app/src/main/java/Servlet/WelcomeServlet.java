package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Получаем текущую сессию
        HttpSession session = req.getSession(false);

        out.println("<html><body>");
        out.println("<h1>Добро пожаловать в газетный киоск!</h1>");

        // Проверяем, авторизован ли пользователь
        if (session != null && session.getAttribute("username") != null) {
            out.println("<p>Добро пожаловать, " + session.getAttribute("username") + "!</p>");
            out.println("<a href='/main'>На главную</a> | <form method='post' action='/logout' style='display:inline;'><input type='submit' value='Выйти'></form>");
        } else {
            out.println("<p>Выберите действие:</p>");
            out.println("<ul>");
            out.println("<li><a href='/register'>Зарегистрироваться</a></li>");
            out.println("<li><a href='/login'>Войти</a></li>");
            out.println("</ul>");
        }

        out.println("</body></html>");
    }
}