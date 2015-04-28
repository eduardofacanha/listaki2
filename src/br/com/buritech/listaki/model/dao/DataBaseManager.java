package br.com.buritech.listaki.model.dao;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
/**
 * Classe gerenciadora do BD
 * @author adrianaizel
 *
 */
public class DataBaseManager {
	
	private static DataBaseManager instance;
	private DataBaseHelper databaseHelper;

	private DataBaseManager(Context ctx) {
		databaseHelper = OpenHelperManager.getHelper(ctx, DataBaseHelper.class);
		databaseHelper.getWritableDatabase();
	}

	public static void init(Context ctx) {
		if (instance == null) {
			instance = new DataBaseManager(ctx);
		}
	}
	
	public static void close() {
		OpenHelperManager.releaseHelper();
		instance.setHelper(null);
	}

	private void setHelper(Object object) {
		databaseHelper = null;
	}

	public static DataBaseManager getInstance() {
		return instance;
	}

	public DataBaseHelper getHelper() {
		return databaseHelper;
	}
}
