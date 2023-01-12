package br.com.playtime.bean;

public class User {
	private int idUser;
	private String nome;
	private String username;
	private String senha;
	
	public User(int idUser, String nome, String username, String senha) {
		this.idUser = idUser;
		this.nome = nome;
		this.username = username;
		this.senha = senha;
	}
	
	public User() {
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
