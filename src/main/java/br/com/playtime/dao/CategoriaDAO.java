package br.com.playtime.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.playtime.bean.CategoriaPT;
import br.com.playtime.bean.ProdutoPT;
import br.com.playtime.util.ConnectionFactory;

public class CategoriaDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private CategoriaPT categoria;

	public CategoriaDAO() throws Exception {
		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro: \n" + e.getMessage());
		}
	}

	// método de salvar
	public void salvar(CategoriaPT categoria) throws Exception {
		
		if (categoria == null)
			throw new Exception("O valor passado nao pode ser nulo");
		
		try {
			
			String SQL = "INSERT INTO categoria (nomeCategoria) values (?)";
			conn = this.conn;			
			ps = conn.prepareStatement(SQL);
			
			ps.setString(1, categoria.getNomeCategoria());
			
			ps.executeUpdate();
			
		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);
			
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	// método de atualizar
	public void atualizar(CategoriaPT categoria) throws Exception {
		if (categoria == null)
			throw new Exception("O valor passado nao pode ser nulo");
		
		try {
			String SQL = "UPDATE categoria SET nomeCategoria = ? WHERE idCategoria = ?";						
			conn = this.conn;			
			ps = conn.prepareStatement(SQL);
			
			ps.setString(1, categoria.getNomeCategoria());
			ps.setInt(2, categoria.getIdCategoria());
			ps.executeUpdate();
			
		} catch (SQLException sqle) {
			throw new Exception("Erro ao alterar dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	// método de excluir
	public void excluir(CategoriaPT categoria) throws Exception {
		if (categoria == null)
			throw new Exception("O valor passado nao pode ser nulo");
		
		try {
			String SQL = "DELETE FROM categoria WHERE idCategoria = ?";
			conn = this.conn;			
			ps = conn.prepareStatement(SQL);
			
			ps.setInt(1, categoria.getIdCategoria());
			ps.executeUpdate();
			
		} catch (SQLException sqle) {
			throw new Exception("Erro ao excluir dados " + sqle);
			
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}	

	public CategoriaPT procurarCategoria(int idCategoria) throws Exception {
		try {
			String SQL = "SELECT * FROM categoria WHERE idCategoria = ?";
			conn = this.conn;
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, idCategoria);
			rs = ps.executeQuery();
						
			if (rs.next()) {				
				int idCategoria_ms = rs.getInt("idCategoria");
				String nomeCategoria = rs.getString("nomeCategoria");	
				categoria = new CategoriaPT(idCategoria_ms, nomeCategoria);
			}
			
			return categoria;
			
		} catch (SQLException sqle) {
			throw new Exception(sqle);
			
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}
	
	// listar todos os produtos
	public List todasCategorias() throws Exception {
		try {
			conn = this.conn;
			ps = conn.prepareStatement("SELECT * FROM categoria");
			rs = ps.executeQuery();
			List<CategoriaPT> list = new ArrayList<CategoriaPT>();
			
			while (rs.next()) {
				int idCategoria = rs.getInt("idCategoria");
				String nomeCategoria = rs.getString("nomeCategoria");
				list.add(new CategoriaPT(idCategoria, nomeCategoria));
			}			
			return list;
			
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}
	
}
