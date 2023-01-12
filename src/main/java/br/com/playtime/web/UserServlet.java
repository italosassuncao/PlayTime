package br.com.playtime.web;

import java.io.IOException;
import java.util.List;

import br.com.playtime.bean.User;
import br.com.playtime.dao.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	        response.setContentType("text/html;charset=UTF-8");
	        // a variável cmd indica o tipo de ação - incluir, alterar, consulta.....
	        String cmd = request.getParameter("cmd");
	        // cria um objeto dao - CRUD
	        UserDAO dao;
	        // cria um objeto do tipo aluno
	        User user = new User();

	        if (cmd != null) {
	            try {
	                // inicializa os atributos da classe Alunos
	            	user.setNome(request.getParameter("nome"));  
	            	user.setUsername(request.getParameter("username"));
	            	user.setSenha(request.getParameter("senha"));
	            	
                } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        }
	        try {
	        	// cria a instancia do objeto dao
	            dao = new UserDAO();
	            RequestDispatcher rd = null;
	            // lista todos os alunos
	            if (cmd.equalsIgnoreCase("listar")) {
	                List UsersList = dao.todosUsers();
	                // cria uma sessão para encaminhar a lista para uma JSP
	                request.setAttribute("UsersList", UsersList);
	                // redireciona para a JSP mostraAlunosCads
	                rd = request.getRequestDispatcher("/mostrarUserCads.jsp");
	            }
	            
	            // incluir aluno
	            else if (cmd.equalsIgnoreCase("incluir")) {
	                dao.salvar(user);
	                rd = request.getRequestDispatcher("UserServlet?cmd=listar");
	             
	            // consulta aluno para exclusão    
	            } else if (cmd.equalsIgnoreCase("exc")) {
	                user = dao.procurarUser(user.getIdUser());
	                HttpSession session = request.getSession(true);
	                session.setAttribute("user", user);
	                rd = request.getRequestDispatcher("/formExcUser.jsp");
	             
	            // exclui aluno
	            } else if (cmd.equalsIgnoreCase("excluir")) {
	                dao.excluir(user);
	                rd = request.getRequestDispatcher("UserServlet?cmd=listar");
	            
	            // consulta aluno para alteração
	            }  else if (cmd.equalsIgnoreCase("atu")) {
	                user = dao.procurarUser(user.getIdUser());
	                HttpSession session = request.getSession(true);
	                session.setAttribute("user", user);
	                rd = request.getRequestDispatcher("/formAtuUser.jsp");
	             
	            // consulta aluno
	            } else if (cmd.equalsIgnoreCase("con")) {
	                user = dao.procurarUser(user.getIdUser());
	                HttpSession session = request.getSession(true);
	                session.setAttribute("user", user);
	                rd = request.getRequestDispatcher("/formConUser.jsp");
	            
	             // altera aluno    
	            } else if (cmd.equalsIgnoreCase("atualizar")) {
	                dao.atualizar(user);
	                rd = request.getRequestDispatcher("UserServlet?cmd=listar");
	            
	            // direciona para a página principal
	            } else if (cmd.equalsIgnoreCase("principal")) {
	                rd = request.getRequestDispatcher("/index.jsp");
	            }            
	            // executa a ação de direcionar para a página JSP
	            rd.forward(request, response);
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
