package br.com.playtime.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import br.com.playtime.bean.User;
import br.com.playtime.util.ConnectionFactory;

public class UserDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private User user;

	public UserDAO() throws Exception {
		// chama a classe ConnectionFactory e estabele uma conexão
		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("erro: \n" + e.getMessage());
		}
	}

	// método de salvar
	public void salvar(User user) throws Exception {
		if (user == null)
			throw new Exception("O valor passado nao pode ser nulo");
		try {
			String SQL = "INSERT INTO user (nome, username, senha) values (?, ?, ?)";
			conn = this.conn;
			ps = conn.prepareStatement(SQL);
			ps.setString(2, user.getNome());
			ps.setString(3, user.getUsername());
			ps.setString(4, user.getSenha());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	// método de atualizar
	public void atualizar(User user) throws Exception {
		if (user == null)
			throw new Exception("O valor passado nao pode ser nulo");
		try {
			String SQL = "UPDATE user set nome=?, username=?";
			conn = this.conn;
			ps = conn.prepareStatement(SQL);
			ps.setString(2, user.getNome());
			ps.setString(3, user.getUsername());
		} catch (SQLException sqle) {
			throw new Exception("Erro ao alterar dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	// método de excluir
	public void excluir(User user) throws Exception {
		if (user == null)
			throw new Exception("O valor passado nao pode ser nulo");
		try {
			String SQL = "DELETE FROM user WHERE idUser = ?";
			conn = this.conn;
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, user.getIdUser());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao excluir dados " + sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	// procurar aluno
	public User procurarUser(int idUser) throws Exception {

		try {
			String SQL = "SELECT  * FROM user WHERE idUser=?";
			conn = this.conn;
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, idUser);
			rs = ps.executeQuery();
			if (rs.next()) {
				int ID = rs.getInt(1);
				String nome = rs.getString(2);
				String username = rs.getString(3);
				String senha = rs.getString(4);				
				user = new User(ID, nome, username, senha);
			}
			return user;
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}

	// listar todos os alunos
	public List todosUsers() throws Exception {
		try {
			conn = this.conn;
			ps = conn.prepareStatement("SELECT * FROM user");
			rs = ps.executeQuery();
			List<User> list = new ArrayList<User>();
			while (rs.next()) {
				int ID = rs.getInt(1);
				String nome = rs.getString(2);
				String username = rs.getString(3);
				String senha = rs.getString(4);				
				list.add(new User(ID, nome, username, senha));
			}
			return list;
		} catch (SQLException sqle) {
			throw new Exception(sqle);
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}
}

