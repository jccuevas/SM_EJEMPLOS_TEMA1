package es.ujaen.ejemplostema2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebBackForwardList;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import es.ujaen.ejemplostema2.R;

public class FragmentoInfo extends Fragment {

	private WebView mInfo = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View fragment = inflater.inflate(R.layout.layout_fragment_info, null);

        mInfo = (WebView) fragment.findViewById(R.id.fragmentinfo_helptext);



        BufferedReader bis = new BufferedReader(new InputStreamReader(getContext().getResources().openRawResource(R.raw.help)));
        String datos="";
        String linea="";

        try {
            linea=bis.readLine();
            while((linea=bis.readLine())!=null){
                datos=datos+linea;
            }
            bis.close();
            mInfo.loadData(datos,"text/html","UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fragment;
		
	}
	
	public void publica(CharSequence text)
	{

		TextView t= (TextView) getActivity().findViewById(R.id.fragmentinfo_helptext);
		if(t!=null)
		{
			t.setText(text);
		}
	}

	
	
	
	
	
}
