package br.com.playtime.web;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import br.com.playtime.bean.User;
import br.com.playtime.dao.UserDAO;

@WebServlet("/ServletLogin")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String cmd = request.getParameter("cmd");		
		UserDAO dao;
		User usuario = new User();
						
		try {
			dao = new UserDAO();
			if (cmd.equalsIgnoreCase("logar")) {
				String username = request.getParameter("username");
				String senha = request.getParameter("senha");				
				usuario = dao.verificaUsuario(username);
								
				if ((usuario == null) || (!usuario.getSenha().toString().equals(senha))){
					request.setAttribute("message", "Usuário ou senha incorretos, tente novamente!");
					request.getRequestDispatcher("Login.jsp").forward(request, response);
					
				} else if ((usuario.getUsername().toString().equals(username)) && (usuario.getSenha().toString().equals(senha))) {
					HttpSession session = request.getSession();
					session.setAttribute("username", username);		
					request.getRequestDispatcher("UserServlet?cmd=listar").forward(request, response);
				}
			} 
			
			else if (cmd.equalsIgnoreCase("logout")) {
				HttpSession session = request.getSession();
				session.invalidate();
				request.getRequestDispatcher("UserServlet?cmd=listar").forward(request, response);
				
			} 
						
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
        
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
}
