package es.ujaen.ejemplostema2;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.graphics.Lienzo;

public class FragmentoGraficos extends Fragment {
    private View mFragmentView = null;
	private ImageView logoandroid = null;
	private ImageView logoandroidscale = null;
	private TextView  textscale=null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
        mFragmentView= inflater.inflate(R.layout.layout_graficos, container, false);


		logoandroid = (ImageView) mFragmentView.findViewById(R.id.imageView_logoandroid);

		ClipDrawable drawable = (ClipDrawable) logoandroid.getDrawable();
		drawable.setLevel((drawable.getLevel() + 1000) % 10000);

		logoandroid.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ClipDrawable drawable = (ClipDrawable) logoandroid
						.getDrawable();
				drawable.setLevel((drawable.getLevel() + 1000) % 10000);

			}
		});

		logoandroidscale = (ImageView) mFragmentView.findViewById(R.id.ImageView_logoandroidscale);
		textscale = (TextView)mFragmentView.findViewById(R.id.graficos_scale);

		logoandroidscale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ScaleDrawable scale = (ScaleDrawable) logoandroidscale.getDrawable();
				scale.setLevel((scale.getLevel() + 1000) % 10000);

				textscale.setText("Nivel="+scale.getLevel());


			}
		});

		Lienzo lienzo = (Lienzo) mFragmentView.findViewById(R.id.custom_drawable_lienzo);
		lienzo.setOnTouchListener(lienzo);

        Button tween = (Button)mFragmentView.findViewById(R.id.graficos_button_tween);
        tween.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onTween();
            }
        });

        Button transition = (Button)mFragmentView.findViewById(R.id.graficos_button_transiton);
        transition.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onTransition();
            }
        });
        Button frame = (Button)mFragmentView.findViewById(R.id.graficos_button_frameAnimation);
        frame.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onFrame();
            }
        });

        return  mFragmentView;
	}

	/**
	 * Inicia una transici�n entre dos drawables: TransitionDrawable La el
	 * drawable de transici�n se ha puesto como fondo del ImageView
	 *
	 */
	public void onTransition() {
		ImageView image = (ImageView) mFragmentView.findViewById(R.id.imageViewTransition);
		TransitionDrawable drawable = (TransitionDrawable) image.getDrawable();
		drawable.startTransition(2000);// 500 ms
		// drawable.reverseTransition(2000);

	}

	/**
	 * Inicia una animaci�n de deformaci�n de un drawable almacenada en un XML,
	 * en este caso en anim/tween.xml
	 *
	 */
	public void onTween() {
		ImageView image = (ImageView) mFragmentView.findViewById(R.id.imageViewTween);

		Animation h = AnimationUtils.loadAnimation(getActivity(), R.anim.tween);
		image.startAnimation(h);

	}

	/**
	 * Inicia una animaci�n por pasos definida en un xml
	 *
	 */
	public void onFrame() {
		ImageView image = (ImageView) mFragmentView.findViewById(R.id.imageViewFrame);

		AnimationDrawable animation = (AnimationDrawable) image.getDrawable();

		if (animation.isRunning())
			animation.stop();
		else
			animation.start();

	}
}
