package com.example.menu;

import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import es.ujaen.ejemplostema2.Lista;
import es.ujaen.ejemplostema2.R;

public class Settings extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);

	}

	public void onClickShow(View view) {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_list);

		FragmentTransaction ft = fm.beginTransaction();

		if (fragment != null)
			ft.remove(fragment);

		Lista lista = new Lista();

		ft.replace(R.id.fragment_viewer, lista);
		ft.addToBackStack(null);
		ft.commit();

	}

}
