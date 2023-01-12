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

import br.com.playtime.bean.CategoriaPT;
import br.com.playtime.dao.CategoriaDAO;

@WebServlet("/ServletCategoria")
public class CategoriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
		
		String cmd = request.getParameter("cmd");
		CategoriaDAO dao;
		CategoriaPT categoria = new CategoriaPT();
				
		try {
			dao = new CategoriaDAO();
			RequestDispatcher rd = null;
			 
			if (cmd.equalsIgnoreCase("mostrar")) {
				List<CategoriaPT> categoriaList = dao.todasCategorias();
				request.setAttribute("categoriaList", categoriaList);
				rd = request.getRequestDispatcher("/listCategoria.jsp");
			}
						
			else if (cmd.equalsIgnoreCase("incluir")) {
				categoria.setNomeCategoria(request.getParameter("nomeCategoria"));
				dao.salvar(categoria);
				rd = request.getRequestDispatcher("ServletCategoria?cmd=mostrar");
			} 
				
			else if (cmd.equalsIgnoreCase("exc")) {
				categoria = dao.procurarCategoria(Integer.parseInt(request.getParameter("id")));
				HttpSession session = request.getSession(true);
				session.setAttribute("categoria", categoria);
				rd = request.getRequestDispatcher("/excCategoria.jsp");
			} 
			
			else if (cmd.equalsIgnoreCase("excluir")) {	
				dao.excluir(categoria);
				rd = request.getRequestDispatcher("listCategoria.jsp");
				
			} 
			
			else if (cmd.equalsIgnoreCase("atu")) {
				categoria = dao.procurarCategoria(Integer.parseInt(request.getParameter("id")));
				HttpSession session = request.getSession(true);
				session.setAttribute("categoria", categoria);
				rd = request.getRequestDispatcher("/altCategoria.jsp");
				
			} 
			
			else if (cmd.equalsIgnoreCase("atualizar")) {
				categoria.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
				categoria.setNomeCategoria(request.getParameter("nomeCategoria"));
				dao.atualizar(categoria);
				rd = request.getRequestDispatcher("ServletCategoria?cmd=mostrar");
				
			} 
						
			rd.forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

}
