package com.bandup.edutask;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        return view;
    }
}
