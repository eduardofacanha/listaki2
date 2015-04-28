package br.com.buritech.listaki.model.dao;

import android.util.Log;

import com.j256.ormlite.dao.Dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import br.com.buritech.listaki.model.entidade.IEntidade;

/**
 * Classe com metodos comuns a qualquer DAO
 * @author adrianaizel
 *
 * @param <T>
 */
public abstract class BaseDAO <T extends IEntidade> {

	private static final String TAG = BaseDAO.class.getSimpleName();

	protected DataBaseHelper getHelper() {
		return DataBaseManager.getInstance().getHelper();
	}

	protected Dao<T, Object> getConnection() {
		return (Dao<T, Object>) getHelper().getRuntimeExceptionDao(getEntityClass());
	}

	private Class getEntityClass() {
		ParameterizedType t = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class) t.getActualTypeArguments()[0];
	}

	public List findAll() {
		try {
			return getHelper().getDAO(getEntityClass()).queryForAll();
		} catch (SQLException e) {
			Log.e(TAG, "Erro metodo findAll BaseDAO", e);
			return Collections.EMPTY_LIST;
		}
	}

	public T findByPK(Object id) {
		try {
			return (T) getHelper().getDAO(getEntityClass()).queryForId(id);
			
		} catch (SQLException e) {
			Log.e(TAG, "Erro metodo findByPK BaseDAO", e);
			return null;
		}
	}

	public boolean createOrUpdate(IEntidade obj) {
		
		try {
			return (getHelper().getDAO(getEntityClass()).createOrUpdate(obj)
					.getNumLinesChanged() > 0);
			
		} catch (SQLException e) {
			Log.e(TAG, "Erro metodo createOrUpdate BaseDAO", e);
			return false;
		}
	}

	public boolean delete(IEntidade obj) {
		
		try {
			return (getHelper().getDAO(getEntityClass()).delete(obj) > 0);
			
		} catch (SQLException e) {
			Log.e(TAG, "Erro metodo delete BaseDAO", e);
			return false;
		}
	}

	public List findForAttribute(String nameAttribute, Object value) {
		try {
			return getHelper().getDAO(getEntityClass()).queryForEq(nameAttribute, value);
			
		} catch (SQLException e) {
			Log.e(TAG, "Erro metodo findByPK BaseDAO", e);
			return null;
		}
	}
}
