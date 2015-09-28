
package com.triolabs.tv_uaa_android;

import com.triolabs.adapter.LastChapterAdapter;
import com.triolabs.adapter.ProgramDetailAdapter;
import com.triolabs.fragment.TVOnlineFragment;

import android.app.Activity;
import android.os.Bundle;

/**
 * Esta clase es la actividad de tv online se agrega el fragment de tv online
 * @author Triolabs
 * @Developer Raul Quintero Esparza
 * @Designer Ivan Padilla
 * @version 1.0
 */
public class TVOnlineActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvonline);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new TVOnlineFragment()).commit();
		}         
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ProgramDetailAdapter.playVideo=false;
		LastChapterAdapter.playVideo=false;
	}
	
	

}
