package br.com.buritech.listaki.core.view.activity;

import br.com.buritech.listaki.core.app.ApplicationContext;
import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import android.os.Bundle;
import android.util.Log;

public abstract class BaseActivity extends RoboActivity {

	private static final String TAG = BaseActivity.class.getSimpleName(); 
	
	protected ApplicationContext appContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		RoboGuice.getInjector(this).injectMembers(this);
        super.onCreate(savedInstanceState);
        
        Log.i(TAG, "getApplicationContext-->"+getApplicationContext());
        Log.i(TAG, "appContext-->"+appContext);
        
        appContext = (ApplicationContext)getApplicationContext();
    }

	public ApplicationContext getAppContext() {
		return appContext;
	}
}
