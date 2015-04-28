package br.com.buritech.listaki.core.app;

import roboguice.RoboGuice;
import android.app.Application;
import android.util.Log;
import br.com.buritech.listaki.model.dao.DataBaseManager;

/**
 * Application Context da Aplicacao List Aki
 * @author adrianaizel
 *
 */
public class ApplicationContext extends Application {

	private static final String TAG = ApplicationContext.class.getSimpleName(); 

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
		
		// Inicializando configuracoes, BD(ORMLite) e Injecao de Dependencia (RoboGuice)
		DataBaseManager.init(this);
		RoboGuice.setBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
				RoboGuice.newDefaultRoboModule(this), new AppModule());
	}

	@Override
	public void onTerminate() {
		Log.d(TAG, "onTerminate");
		DataBaseManager.close();
		super.onTerminate();
	}
}
