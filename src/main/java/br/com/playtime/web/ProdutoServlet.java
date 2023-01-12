package br.com.playtime.web;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import br.com.playtime.bean.ProdutoPT;
import br.com.playtime.dao.ProdutoDAO;

@WebServlet("/ProdutoServlet")
public class ProdutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		
		String cmd = request.getParameter("cmd");
		ProdutoDAO dao;
		ProdutoPT produto = new ProdutoPT();
				
		try {
			dao = new ProdutoDAO();
			RequestDispatcher rd = null;
			 
			if (cmd.equalsIgnoreCase("listar")) {
				List<ProdutoPT> produtoList = dao.todosProdutos();
				request.setAttribute("produtoList", produtoList);		// cria uma sess�o para encaminhar a lista para uma JSP
				rd = request.getRequestDispatcher("/produtos.jsp");		// redireciona para a JSP produtos
			}
			
			else if (cmd.equalsIgnoreCase("mostrar")) {
				List<ProdutoPT> produtoList = dao.todosProdutos();
				request.setAttribute("produtoList", produtoList);
				rd = request.getRequestDispatcher("/listProduto.jsp");
			}
			
			else if (cmd.equalsIgnoreCase("incluir")) {
				produto.setNomeProduto(request.getParameter("nomeProduto"));
				produto.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
				produto.setPrecoUnitario(Double.parseDouble(request.getParameter("precoUnitario")));
				produto.setImgProduto(request.getParameter("imgProduto"));
				produto.setDescricao(request.getParameter("descricao"));
				dao.salvar(produto);
				rd = request.getRequestDispatcher("ServletProduto?cmd=mostrar");
			} 
						
			else if (cmd.equalsIgnoreCase("exc")) { 	// consulta produto para exclus�o
				produto = dao.procurarProduto(Integer.parseInt(request.getParameter("id")));
				HttpSession session = request.getSession(true);
				session.setAttribute("produto", produto);
				rd = request.getRequestDispatcher("/excProduto.jsp");
			} 
			
			else if (cmd.equalsIgnoreCase("excluir")) {	// exclui produto
				produto.setIdProduto(Integer.parseInt(request.getParameter("idProduto")));
				dao.excluir(produto);
				rd = request.getRequestDispatcher("ServletProduto?cmd=mostrar");
				
			} 
			
			else if (cmd.equalsIgnoreCase("atu")) {		// consulta produto para altera
				produto = dao.procurarProduto(Integer.parseInt(request.getParameter("id")));
				HttpSession session = request.getSession(true);
				session.setAttribute("produto", produto);
				rd = request.getRequestDispatcher("/altProduto.jsp");
				
			} 
			
			else if (cmd.equalsIgnoreCase("atualizar")) {	// altera produto
				produto.setIdProduto(Integer.parseInt(request.getParameter("idProduto")));
				produto.setNomeProduto(request.getParameter("nomeProduto"));
				produto.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
				produto.setPrecoUnitario(Double.parseDouble(request.getParameter("precoUnitario")));
				produto.setImgProduto(request.getParameter("imgProduto"));
				produto.setDescricao(request.getParameter("descricao"));
				dao.atualizar(produto);
				rd = request.getRequestDispatcher("ServletProduto?cmd=mostrar");
				
			} 
			
			else if (cmd.equalsIgnoreCase("consultar")) {	// consulta produto
				String paramValue = request.getParameter("idProduto");
				produto = dao.procurarProduto(Integer.parseInt(paramValue));
				HttpSession session = request.getSession(true);
				session.setAttribute("produto", produto);
				rd = request.getRequestDispatcher("/detalhes.jsp");				
			}
			
			else if (cmd.equalsIgnoreCase("listarCategoria")) {
				String paramValue = request.getParameter("idCategoria");
				List<ProdutoPT> produtoList = dao.listarProdutosCategoriaX(Integer.parseInt(paramValue));
				request.setAttribute("produtoList", produtoList);		// cria uma sess�o para encaminhar a lista para uma JSP
				rd = request.getRequestDispatcher("/produtos.jsp");		// redireciona para a JSP produtos
			}
			
						
			// executa a acao de direcionar para a p�gina JSP
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
