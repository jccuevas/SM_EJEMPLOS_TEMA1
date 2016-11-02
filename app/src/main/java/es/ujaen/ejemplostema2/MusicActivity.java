package es.ujaen.ejemplostema2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class MusicActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION_READEXTERNAL = 1;

    private ImageView mPlayRaw = null;
    private ImageView mPlayExternal = null;

    private MediaPlayer mMPlayer = null;
    private View mMainView = null;
    private int audioSessionIdRaw = 0;
    private int audioSessionIdExternal = 0;
    final File music = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);

    String mExternalPath = music.getPath() + "/" + "invierno.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mMainView = findViewById(R.id.content_main);


        mPlayRaw = (ImageView) findViewById(R.id.main_play);

        mPlayExternal = (ImageView) findViewById(R.id.main_play2);


        mPlayRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();

            }
        });
        mPlayExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (podemosLeer()) {
                    checkPermissions();
                } else {
                    Snackbar.make(mMainView, R.string.externalstorage_error,
                            Snackbar.LENGTH_LONG).show();
                }

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(mMainView, R.string.no_action,
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }


    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(MusicActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Se pregunta si debemos poner una explicación
            if (ActivityCompat.shouldShowRequestPermissionRationale(MusicActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Se muestra una breve explicación de por qué solicitar el permiso
                Snackbar message = Snackbar.make(mMainView, R.string.permission_readexternal,
                        Snackbar.LENGTH_LONG);
                message.show();
                ActivityCompat.requestPermissions(MusicActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READEXTERNAL);
            } else {

                // No se necesita explicación, se pasa a solicitar el permiso.
                ActivityCompat.requestPermissions(MusicActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSION_READEXTERNAL);

                // REQUEST_PERMISSION_READEXTERNAL es una constante para identificar
                // la petición de permisos concreta
            }
        } else {
            playMusicExternal(mExternalPath);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_READEXTERNAL: {
                // Si la petición es cancelada se devuelve un vector vacío
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permiso concedido
                    playMusicExternal(mExternalPath);
                } else {

                    // Permiso denegado por el usuario
                    Snackbar.make(mMainView, R.string.permission_readexternal_denied,
                            Snackbar.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    protected void playMusic() {
        if (mMPlayer != null)
            if (mMPlayer.isPlaying()) {
                mMPlayer.pause();
                mPlayRaw.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_play));
            } else {

                mMPlayer.start();
                mPlayRaw.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_pause));
            }
    }

    protected void playMusicExternal(String path) {


        if (mMPlayer != null && audioSessionIdRaw!=0) {
            mMPlayer.stop();
            try {
                iniExternalAudio(mExternalPath);
                audioSessionIdRaw=0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mMPlayer != null) {
            if (mMPlayer.isPlaying()) {
                mMPlayer.pause();
                mPlayExternal.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_play));
            } else {

                mMPlayer.start();
                mPlayExternal.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_pause));
            }

        } else {
            try {
                iniExternalAudio(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void iniExternalAudio(String path) throws IOException{
        mMPlayer = new MediaPlayer();
        mMPlayer.setDataSource(path);
        mMPlayer.prepare();
        mMPlayer.start();
        mPlayExternal.setImageDrawable(ContextCompat.getDrawable(MusicActivity.this, android.R.drawable.ic_media_pause));
    }
    @Override
    public void onResume() {
        super.onResume();
        if (audioSessionIdRaw != 0) {
            mMPlayer = MediaPlayer.create(MusicActivity.this, R.raw.audio_vivaldi);
            audioSessionIdRaw = mMPlayer.getAudioSessionId();
        } else if (audioSessionIdExternal != 0) {
            try {
                iniExternalAudio(mExternalPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //playMusicExternal();
        } else {
            mMPlayer = MediaPlayer.create(MusicActivity.this, R.raw.audio_vivaldi);
            audioSessionIdRaw=mMPlayer.getAudioSessionId();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
