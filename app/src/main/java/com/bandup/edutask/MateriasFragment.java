package com.bandup.edutask;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;

import com.bandup.edutask.BaseDatosHelper;
import com.bandup.edutask.R;

public class MateriasFragment extends Fragment {

    private ListView listViewMaterias;
    private BaseDatosHelper baseDatosHelper;

    public MateriasFragment() {
        // Constructor vacío requerido por Android
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_materias, container, false);

        baseDatosHelper = new BaseDatosHelper(getActivity());
        listViewMaterias = view.findViewById(R.id.listViewMaterias);

        // Obtén los datos de la base de datos
        Cursor cursorMaterias = baseDatosHelper.getMaterias();

        // Configura el adaptador
        String[] fromColumns = {BaseDatosHelper.getMateriaNombreColumn()};
        int[] toViews = {R.id.textViewMateriaNombre};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item_materia, cursorMaterias, fromColumns, toViews, 0);

        // Asigna el adaptador al ListView
        listViewMaterias.setAdapter(adapter);

        listViewMaterias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtén el ID de la materia seleccionada
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int materiaId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                // Abre el fragmento de Asignaciones pasando el ID de la materia seleccionada
                openAsignacionesFragment(materiaId);
            }
        });


        return view;
    }

    private void openAsignacionesFragment(int materiaId) {
        AsignacionesFragment asignacionesFragment = new AsignacionesFragment();

        // Puedes pasar el ID de la materia como argumento al fragmento de Asignaciones
        Bundle args = new Bundle();
        args.putInt("materiaId", materiaId);
        asignacionesFragment.setArguments(args);

        // Obtén el FragmentManager y comienza la transacción
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor1, asignacionesFragment)
                .addToBackStack(null)  // Agregar a la pila de retroceso para permitir la navegación hacia atrás
                .commit();
        listViewMaterias.setVisibility(View.GONE);
    }

}
