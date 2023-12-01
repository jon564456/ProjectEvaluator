package com.itsoeh.jbrigido.projectevaluator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.navigation.NavigationView;
import com.itsoeh.jbrigido.projectevaluator.adapters.AdapterEvaluador;
import com.itsoeh.jbrigido.projectevaluator.config.API;
import com.itsoeh.jbrigido.projectevaluator.config.VolleySingleton;
import com.itsoeh.jbrigido.projectevaluator.databinding.ActivityMainBinding;
import com.itsoeh.jbrigido.projectevaluator.modelo.Evaluador;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private TextView text_nombre, text_correo;
    private Bundle datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Infla la vista de la actividad
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtiene los datos pasados desde la actividad de inicio de sesión
        datos = this.getIntent().getExtras();

        // Configuración del cajón de navegación
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Configuración de los destinos del cajón de navegación
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navInicio, R.id.fragProfile, R.id.fragEvaluador, R.id.fragProyecto, R.id.fragResultados)
                .setOpenableLayout(drawer)
                .build();

        // Configuración del controlador de navegación y la barra de acción
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Obtiene la vista del encabezado del cajón de navegación
        View headerView = navigationView.getHeaderView(0);
        text_nombre = headerView.findViewById(R.id.header_text_nombre);
        text_correo = headerView.findViewById(R.id.header_text_correo);

        // Si hay datos de usuario, carga la información del usuario
        if (datos != null) {
            cargarDatosUsuario();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infla el menú; esto agrega elementos a la barra de acciones si está presente.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Maneja las acciones de los elementos del menú de la barra de acciones.
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            // Si selecciona salir, redirige a la pantalla de inicio de sesión y finaliza la actividad actual.
            datos = null;
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Maneja la navegación hacia atrás en la barra de acción.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Método para cargar los datos del usuario en la vista del cajón de navegación
    public void cargarDatosUsuario() {
        text_correo.setText(datos.getString("email"));
        text_nombre.setText(datos.getString("nombre") + " " + datos.getString("apepa") + " " + datos.getString("apema"));
    }
}
