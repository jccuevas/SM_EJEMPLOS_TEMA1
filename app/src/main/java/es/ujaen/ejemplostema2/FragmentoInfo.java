package es.ujaen.ejemplostema2;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class FragmentoInfo extends Fragment {

	private WebView mInfo = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View fragment = inflater.inflate(R.layout.layout_fragment_info, null);

        mInfo = (WebView) fragment.findViewById(R.id.fragmentinfo_helptext);
        mInfo.setWebChromeClient(new WebChromeClient(){

            private ValueCallback<Uri> uploadMsg;

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                this.uploadMsg = uploadMsg;

                Toast.makeText(getContext(),uploadMsg.toString(),Toast.LENGTH_LONG).show();
                // Update message
//                mUploadMessage = uploadMsg;
//
//                try{
//
//                    // Create AndroidExampleFolder at sdcard
//
//                    File imageStorageDir = new File(
//                            Environment.getExternalStoragePublicDirectory(
//                                    Environment.DIRECTORY_PICTURES)
//                            , "AndroidExampleFolder");
//
//                    if (!imageStorageDir.exists()) {
//                        // Create AndroidExampleFolder at sdcard
//                        imageStorageDir.mkdirs();
//                    }
//
//                    // Create camera captured image file path and name
//                    File file = new File(
//                            imageStorageDir + File.separator + "IMG_"
//                                    + String.valueOf(System.currentTimeMillis())
//                                    + ".jpg");
//
//                    mCapturedImageURI = Uri.fromFile(file);
//
//                    // Camera capture image intent
//                    final Intent captureIntent = new Intent(
//                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//
//                    captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
//
//                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//                    i.addCategory(Intent.CATEGORY_OPENABLE);
//                    i.setType("image/*");
//
//                    // Create file chooser intent
//                    Intent chooserIntent = Intent.createChooser(i, "Image Chooser");
//
//                    // Set camera intent to file chooser
//                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS
//                            , new Parcelable[] { captureIntent });
//
//                    // On select image call onActivityResult method of activity
//                    startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
//
//                }
//                catch(Exception e){
//                    Toast.makeText(getBaseContext(), "Exception:"+e,
//                            Toast.LENGTH_LONG).show();
//                }

            }

        });

        WebSettings webSettings = mInfo.getSettings();
        webSettings.setJavaScriptEnabled(true);



        mInfo.loadUrl("file:///android_asset/www/help.html");
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
