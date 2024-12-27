package Servlet.Session;

import Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    public LoginServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Вход</h1>");
        out.println("<form method='post'>");
        out.println("Имя пользователя: <input type='text' name='username'><br>");
        out.println("Пароль: <input type='password' name='password'><br>");
        out.println("<input type='submit' value='Войти'>");
        out.println("</form>");
        out.println("<br><a href='/register'>Зарегистрироваться</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Проверяем авторизацию
        if (userService.authenticateUser(username, password)) {
            // Авторизация успешна, создаем сессию
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            // Перенаправляем на главную страницу
            resp.sendRedirect("/main");
        } else {
            // Авторизация не удалась, выводим сообщение об ошибке
            out.println("<html><body>");
            out.println("<h1>Ошибка входа</h1>");
            out.println("<p>Неверное имя пользователя или пароль.</p>");
            out.println("<a href='/login'>Попробовать снова</a>");
            out.println("</body></html>");
        }
    }
}