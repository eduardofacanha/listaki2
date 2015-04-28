package br.com.buritech.listaki.model.entidade;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Estabelecimento")
public class Estabelecimento implements IEntidade{

	private static final long serialVersionUID = -4624479182151306933L;

	@DatabaseField(generatedId=true)
	private Long id;
	
	@DatabaseField
	private Long idWeb;
	
	@DatabaseField(canBeNull = false)
	private String razaoSocial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdWeb() {
		return idWeb;
	}

	public void setIdWeb(Long idWeb) {
		this.idWeb = idWeb;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
}
