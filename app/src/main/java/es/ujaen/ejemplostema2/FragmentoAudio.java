package es.ujaen.ejemplostema2;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoAudio extends Fragment {

    private View mFragment = null;

    public FragmentoAudio() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragment = inflater.inflate(R.layout.fragment_audio, container, false);

        ImageButton play1 = (ImageButton)mFragment.findViewById(R.id.fragment_audio_play1);

        play1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.audio_vivaldi);
                mp.start();

            }
        });
        return mFragment;
    }

}
