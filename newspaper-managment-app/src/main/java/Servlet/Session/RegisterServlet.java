package Servlet.Session;

import Service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    public RegisterServlet(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        out.println("<html><body>");
        out.println("<h1>Регистрация</h1>");
        out.println("<form method='post'>");
        out.println("Имя пользователя: <input type='text' name='username'><br>");
        out.println("Пароль: <input type='password' name='password'><br>");
        out.println("Подтвердите пароль: <input type='password' name='confirmPassword'><br>");
        out.println("<input type='submit' value='Зарегистрироваться'>");
        out.println("</form>");
        out.println("<br><a href='/login'>Войти</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Проверяем, совпадают ли пароли
        if (!password.equals(confirmPassword)) {
            out.println("<html><body>");
            out.println("<h1>Ошибка регистрации</h1>");
            out.println("<p>Пароли не совпадают.</p>");
            out.println("<a href='/register'>Попробовать снова</a>");
            out.println("</body></html>");
            return;
        }

        // Пытаемся зарегистрировать пользователя
        try {
            if (userService.registerUser(username, password, confirmPassword)) {
                // Регистрация успешна, перенаправляем на страницу приветствия
                resp.sendRedirect("/welcome");
            } else {
                // Регистрация не удалась, выводим сообщение об ошибке
                out.println("<html><body>");
                out.println("<h1>Ошибка регистрации</h1>");
                out.println("<p>Пользователь с таким именем уже существует.</p>");
                out.println("<a href='/register'>Попробовать снова</a>");
                out.println("</body></html>");
            }
        } catch (Exception e) {
            // Обработка других ошибок
            out.println("<html><body>");
            out.println("<h1>Ошибка регистрации</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<a href='/register'>Попробовать снова</a>");
            out.println("</body></html>");
        }
    }
}