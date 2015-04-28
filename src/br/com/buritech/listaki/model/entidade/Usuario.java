package br.com.buritech.listaki.model.entidade;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Usuario")
public class Usuario implements IEntidade {

	private static final long serialVersionUID = 2109083718304829882L;

	@DatabaseField(generatedId = true)
	private Long id;
	
	@DatabaseField(canBeNull = false)
	private String nome;

	@DatabaseField(canBeNull = false)
	private String email;

	@DatabaseField(canBeNull = false)
	private String senha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
