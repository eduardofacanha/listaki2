package br.com.buritech.listaki.model.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.buritech.listaki.model.entidade.DadosCotacaoListaUsuario;
import br.com.buritech.listaki.model.entidade.Estabelecimento;
import br.com.buritech.listaki.model.entidade.IEntidade;
import br.com.buritech.listaki.model.entidade.ItemListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.ItensDadoCotacao;
import br.com.buritech.listaki.model.entidade.ListaProdutosUsuario;
import br.com.buritech.listaki.model.entidade.Produto;
import br.com.buritech.listaki.model.entidade.ProdutoEstabelecimento;
import br.com.buritech.listaki.model.entidade.Usuario;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Helper do BD usando ORMLite 
 * @author adrianaizel
 *
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String TAG = DataBaseHelper.class.getSimpleName();

	// name of the database file for your application (change to something appropriate for your app)
	private static final String DATABASE_NAME = "ListAki_Adroid.db";

	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 1;

//	private RuntimeExceptionDao<Produto, Integer> produtoRuntimeDao = null;
	
	private Map<Class, Object> daos = new HashMap<Class, Object>();

	private Context context;

	public DataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
	}

	/**
	 * This is called when the database is first created. Usually you should
	 * call createTable statements here to create the tables that will store
	 * your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		
		try {
			Log.d(TAG, "Criando Banco de Dados ....");
			TableUtils.createTable(connectionSource, Usuario.class);
			TableUtils.createTable(connectionSource, Estabelecimento.class);
			TableUtils.createTable(connectionSource, Produto.class);
			TableUtils.createTable(connectionSource, ProdutoEstabelecimento.class);
			TableUtils.createTable(connectionSource, ListaProdutosUsuario.class);
			TableUtils.createTable(connectionSource, ItemListaProdutosUsuario.class);
			TableUtils.createTable(connectionSource, DadosCotacaoListaUsuario.class);
			TableUtils.createTable(connectionSource, ItensDadoCotacao.class);

		} catch (SQLException e) {
			Log.e(TAG, "Nao conseguiu realizar CREATE Banco de Dados ...", e);
			throw new RuntimeException(e);
		}		
	}

	/**
	 * This is called when your application is upgraded and it has a higher
	 * version number. This allows you to adjust the various data to match the
	 * new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
			int oldVersion, int newVersion) {

		try {
			Log.d(TAG, "onUpgrade database");
			TableUtils.dropTable(connectionSource, ItensDadoCotacao.class, true);
			TableUtils.dropTable(connectionSource, DadosCotacaoListaUsuario.class, true);
			TableUtils.dropTable(connectionSource, ItemListaProdutosUsuario.class, true);
			TableUtils.dropTable(connectionSource, ListaProdutosUsuario.class, true);
			TableUtils.dropTable(connectionSource, ProdutoEstabelecimento.class, true);
			TableUtils.dropTable(connectionSource, Produto.class, true);
			TableUtils.dropTable(connectionSource, Estabelecimento.class, true);
			TableUtils.dropTable(connectionSource, Usuario.class, true);

			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);

		} catch (SQLException e) {
			Log.e(TAG, "Nao conseguiu realizar DROP Banco de Dados", e);
			throw new RuntimeException(e);
		}
	}
	

	
	/**
	 * Returns the Database Access Object (DAO) 
	 * @param <T>
	 * @param <T>
	 * @throws java.sql.SQLException
	 */
	public <T>Dao<T, Object> getDAO(Class entidadeClass) throws SQLException {
		
		Dao<IEntidade, Object> dao = null;
		
		if (daos.get(entidadeClass) == null) {
			//Log.i(TAG, "getRuntimeExceptionDao-->"+super.getRuntimeExceptionDao(entidadeClass));
			//dao = super.getRuntimeExceptionDao(entidadeClass);
			dao = super.getDao(entidadeClass);
			
			daos.put(entidadeClass, dao);
		}
		 
		return (Dao<T, Object>) daos.get(entidadeClass);
	}

	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		Log.i(TAG, "close");
	}

	public Context getContext() {
		return context;
	}
}
