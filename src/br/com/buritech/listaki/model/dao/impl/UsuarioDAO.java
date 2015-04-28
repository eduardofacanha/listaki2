package br.com.buritech.listaki.model.dao.impl;

import android.database.Cursor;
import br.com.buritech.listaki.model.dao.BaseDAO;
import br.com.buritech.listaki.model.entidade.Usuario;

public class UsuarioDAO extends BaseDAO<Usuario> {

	private static UsuarioDAO dao;

	private UsuarioDAO() {
		super();
	}

	public static UsuarioDAO getInstance() {
		if (dao == null) {
			dao = new UsuarioDAO();
		}

		return dao;
	}
	
	/*Autor dos métodos abaixo: Lucas Feitosa.
	 * Não consegui utilizar os métodos presentes em BaseDAO para fazer consulta ao banco de dados,
	 * portanto para fechar a atividade de login, criei esses dois métodos de consulta.
	 */
		
		
		public boolean ConsultaSenha(String email, String senha) { //Recebe o email e a senha de LoginActivity
			Cursor resultSet = getHelper().getReadableDatabase().rawQuery(
					"Select * from Usuario where email = ? AND senha = ?",
					new String[] { email, senha });

			if (resultSet.getCount() > 0) {

				return true; //Retorna verdadeiro se existir o email e se a senha for correspondente.
			
			} else {
			
				return false;
			
			}
		}

		public boolean consultaIgual(String email) { //Recebe o email de LoginActivity e consulta o banco para saber se já foi cadastrado.
			Cursor resultSet = getHelper().getReadableDatabase().rawQuery(
					"Select * from Usuario where email = ?",
					new String[] { email });

			if (resultSet.getCount() > 0) {

				return true; //Retorna verdadeiro se o email já estiver cadastrado.
			
			} else {
			
				return false;
			
			}

		}
}
