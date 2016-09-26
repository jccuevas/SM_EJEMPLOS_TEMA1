package es.ujaen.ejemplostema2;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class Lista extends ListFragment {

    boolean mDualPane = true;
    int mCurCheckPosition = 0;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Populate list with our static array of titles.
        String[] values = new String[]{"uno", "dos"};


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (getActivity().findViewById(R.id.panel) != null) {
            Panel panel = (Panel) getFragmentManager().findFragmentById(R.id.panel);
            if (panel == null) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                panel = new Panel();
                ft.add(panel, "panel");
                ft.addToBackStack("panel");
                ft.commit();

            }
            panel.publica("Pulsado " + position);
        } else {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Panel panel = new Panel();
            ft.add(panel, "panel");
            ft.addToBackStack("panel");
            ft.commit();
            panel.publica("Pulsado " + position);
        }
    }


}
