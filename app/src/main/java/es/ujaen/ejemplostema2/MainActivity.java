package es.ujaen.ejemplostema2;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ContextMenu;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentoControles.OnFragmentInteractionListener {


    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    public static final String FRAGMENTO_DETALLES = "detalles";

    public static final int MENU_CONTEXTUAL_AYUDA = 1;

    FragmentManager mFM = null;
    private View mLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.content_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mFM = getSupportFragmentManager();


        Fragment f = mFM.findFragmentById(R.id.fragmento_lista);
        if (f == null) showHelpFragment();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHelpFragment();

                Snackbar.make(view, getString(R.string.help_title), Snackbar.LENGTH_LONG).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void showHelpFragment() {
        FragmentTransaction ft = mFM.beginTransaction();
        FragmentoInfo info = new FragmentoInfo();
        Fragment f = mFM.findFragmentById(R.id.fragmento_lista);
        if (f != null) {
            ft.remove(f);
            ft.replace(R.id.fragmento_lista, info);
        } else {
            ft.add(R.id.fragmento_lista, info, "INFO");
        }
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Se controla el evento de pulsación de la tecla de volver, haciendo que si está abierto el
     * menú lateral se cierre, y si ya está cerrado se actúe por defecto
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Ejemplo de creación de un submenú
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        boolean result = super.onCreateOptionsMenu(menu);
        SubMenu fileMenu = menu.addSubMenu("File");
        fileMenu.add("new");
        fileMenu.add("open");
        fileMenu.add("save");
        return result;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_help) {
            showHelpFragment();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, MENU_CONTEXTUAL_AYUDA, 0, R.string.menu_help);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_CONTEXTUAL_AYUDA:
                showHelpFragment();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = mFM.beginTransaction();
        switch (item.getItemId()) {
            case R.id.nav_controls: //Opción mostrar fragmento de controles básicos

                //Se obtiene el fragmento que se esté mostrando actualemente

                Fragment f = mFM.findFragmentById(R.id.fragmento_lista);
                FragmentoControles controles = new FragmentoControles();
                if (f != null) {//Si ya hay un fragmento se elimina
                    ft.remove(f);
                    ft.replace(R.id.fragmento_lista, controles);
                } else {//Si no hay fragmento, se añade simplemente
                    ft.add(R.id.fragmento_lista, controles, "controles");
                }
                ft.commit();
                break;
            case R.id.nav_almacenamiento: //Opción mostrar fragmento de almacenamiento

                //Se obtiene el fragmento que se esté mostrando actualemente

                Fragment f2 = mFM.findFragmentById(R.id.fragmento_lista);
                FragmentoAlmacenamiento almacenamiento = new FragmentoAlmacenamiento();
                if (f2 != null) {//Si ya hay un fragmento se elimina
                    ft.remove(f2);
                    ft.replace(R.id.fragmento_lista, almacenamiento);
                } else {//Si no hay fragmento, se añade simplemente
                    ft.add(R.id.fragmento_lista, almacenamiento, "almacenamiento");
                }
                ft.commit();
                break;
            case R.id.nav_layouts:
                Fragment f3 = mFM.findFragmentById(R.id.fragmento_lista);
                FragmentoLista lista = new FragmentoLista();
                if (f3 != null) {
                    ft.remove(f3);
                    ft.replace(R.id.fragmento_lista, lista);
                } else {
                    ft.add(R.id.fragmento_lista, lista, "lista");
                }


                if (findViewById(R.id.fragmento_detalles) != null) {
                    FragmentoPanel panel = (FragmentoPanel) getSupportFragmentManager().findFragmentByTag(FRAGMENTO_DETALLES);
                    if (panel == null) {
                        panel = new FragmentoPanel();
                        ft.add(R.id.fragmento_detalles, panel, FRAGMENTO_DETALLES);
                        ft.addToBackStack(FRAGMENTO_DETALLES);
                    }
                }

                ft.commit();
                break;

            case R.id.nav_fragmentosdinamicos:
                startActivity(new Intent(this, FragmentsActivity.class));
                break;
            case R.id.nav_about:
                FragmentoAcercade fragmentoAcercade = FragmentoAcercade.newInstance("uno", "dos");
                fragmentoAcercade.show(mFM, "acercade");
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    /**
     * Método que podrá ser ejecutado cuando se invoque desde el fragmento FragmentoControles
     * Este es un ejemplo de comunicación entre Actividades y Fragmentos
     */
    public void onFragmentInteraction(Uri uri) {
        //TODO Agregar una operación
    }
}
