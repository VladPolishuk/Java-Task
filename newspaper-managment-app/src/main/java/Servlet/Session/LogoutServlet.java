package Servlet.Session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем текущую сессию
        HttpSession session = req.getSession(false);

        // Если сессия существует, очищаем её
        if (session != null) {
            session.invalidate(); // Уничтожаем сессию
        }

        // Перенаправляем на страницу приветствия
        resp.sendRedirect("/welcome");
    }
}