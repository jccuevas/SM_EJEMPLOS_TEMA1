package es.ujaen.ejemplostema2;


import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.test.RenamingDelegatingContext;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoAudio extends Fragment {

    private View mFragment = null;
    MediaPlayer mMPlayer = null;


    public FragmentoAudio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragment = inflater.inflate(R.layout.fragment_audio, container, false);

        ImageButton play1 = (ImageButton) mFragment.findViewById(R.id.fragment_audio_play1);
        ImageButton play2 = (ImageButton) mFragment.findViewById(R.id.fragment_audio_play2);


        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMPlayer != null)
                    if (mMPlayer.isPlaying())
                        mMPlayer.pause();
                    else
                        mMPlayer.start();

            }
        });

        play2.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                         File music = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                                         String path = music.getPath() + "/" + "invierno.mp3";

                                         if (mMPlayer != null) {

                                             if (mMPlayer.isPlaying()) {
                                                 mMPlayer.stop();
                                                 mMPlayer.release();
                                             }
                                         }

                                         try {
                                             mMPlayer = new MediaPlayer();
                                             mMPlayer.setDataSource(path);
                                             mMPlayer.prepare();
                                             mMPlayer.start();
                                         } catch (IOException e) {
                                             e.printStackTrace();
                                         }
                                     }
                                 }
        );
        return mFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        mMPlayer = MediaPlayer.create(getContext(), R.raw.audio_vivaldi);
    }

    private void permisos(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
            if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Snackbar.make(mFragment, R.string.fragment_audio_permission,
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(android.R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(getActivity(),
                                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                            MainActivity.REQUEST_EXTERNAL_STORAGE);
                                }
                            })
                            .show();
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MainActivity.REQUEST_EXTERNAL_STORAGE);
                }
            }
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        if (mMPlayer.isPlaying())
            mMPlayer.stop();
        mMPlayer.release();

    }

    public boolean podemosLeer() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        } else return Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);

    }

}