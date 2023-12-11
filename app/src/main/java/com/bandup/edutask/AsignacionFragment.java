package com.bandup.edutask;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AsignacionFragment extends Fragment {

    private ListView listViewStudent1;
    private ListView listViewStudent2;
    private BaseDatosHelper dbHelper;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Set<String> estudiantesSeleccionados = new HashSet<>();
    private Set<String> estudiantesRealizados = new HashSet<>();

    public AsignacionFragment() {
        // Required empty public constructor
    }

    public static AsignacionFragment newInstance(String param1, String param2) {
        AsignacionFragment fragment = new AsignacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    int asigId;

    List<String> estudiantesRevisados = new ArrayList<>();
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;

    List<String> studentListNo;

    List<String> studentListSi;

    private int estadoRevisada = 1; // Por defecto, asumiremos que está revisada

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_asignacion, container, false);

        asigId = getArguments().getInt("asigId", -1);

        Log.d("NombreDeTuTag", "Mensaje a imprimir: " + asigId);

        listViewStudent1 = rootView.findViewById(R.id.ListViewStudent1);
        listViewStudent2 = rootView.findViewById(R.id.ListViewStudent2);

        dbHelper = new BaseDatosHelper(requireContext());

        Cursor cursor = dbHelper.getAsignados(asigId, 0);
        studentListNo = new ArrayList<>();

        Cursor cursor2 = dbHelper.getAsignados(asigId, 1);
        studentListSi = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String studentName = cursor.getString(cursor.getColumnIndexOrThrow("Alumno_ID"));
                studentListNo.add(studentName);
            } while (cursor.moveToNext());

            cursor.close();
        }

        if (cursor2 != null && cursor2.moveToFirst()) {
            do {
                String studentName = cursor2.getString(cursor2.getColumnIndexOrThrow("Alumno_ID"));
                studentListSi.add(studentName);
            } while (cursor2.moveToNext());

            cursor2.close();
        }

        adapter1 = new ArrayAdapter<String>(requireContext(), R.layout.custom_list_item_check, R.id.text1, studentListNo) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                // Ordena los elementos alfabéticamente
                this.sort(new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });

                // Configura cualquier otra personalización según sea necesario

                // Obtener el CheckBox asociado a este elemento de la lista
                CheckBox checkBox = view.findViewById(R.id.check);

                // Establecer un Listener para el clic del CheckBox
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String selectedStudent = studentListNo.get(position); // Corregido de studentListNo a studentListSi

                        // Actualiza el conjunto de estudiantes revisados
                        if (isChecked) {
                            estudiantesRevisados.add(selectedStudent);
                        } else {
                            estudiantesRevisados.remove(selectedStudent);
                        }

                        // Actualiza la variable de estado
                        estadoRevisada = (estudiantesRevisados.size() > 0) ? 1 : 0;

                        // Toast.makeText(requireContext(), "Checkbox de revisadas de: " + selectedStudent, Toast.LENGTH_SHORT).show();
                    }
                });


                return view;
            }
        };

        adapter2 = new ArrayAdapter<String>(requireContext(), R.layout.custom_list_item_check, R.id.text1, studentListSi) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                // Ordena los elementos alfabéticamente
                this.sort(new Comparator<String>() {
                    @Override
                    public int compare(String s1, String s2) {
                        return s1.compareToIgnoreCase(s2);
                    }
                });

                // Configura cualquier otra personalización según sea necesario

                // Obtener el CheckBox asociado a este elemento de la lista
                CheckBox checkBox = view.findViewById(R.id.check);

                // Establecer un Listener para el clic del CheckBox
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        String selectedStudent = studentListSi.get(position);

                        // Actualiza el conjunto de estudiantes revisados
                        if (isChecked) {
                            estudiantesRevisados.add(selectedStudent);
                            estadoRevisada = 0; // Cambia a 1 cuando el checkbox se marque
                        } else {
                            estudiantesRevisados.remove(selectedStudent);
                            estadoRevisada = 1; // Cambia a 0 cuando el checkbox se desmarque
                        }

                        // Toast.makeText(requireContext(), "Checkbox de revisadas de: " + selectedStudent, Toast.LENGTH_SHORT).show();
                    }
                });


                return view;
            }
        };

        Button btnAplicarCambios = rootView.findViewById(R.id.btnAplicarCheckboxes);
        btnAplicarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Imprime el tamaño de la lista de estudiantes revisados
                //Log.d("TamañoLista", "Tamaño de estudiantesRevisados: " + estudiantesRevisados.size());

                actualizarListViews();

                // Verifica si hay estudiantes revisados antes de realizar la actualización
                if (estudiantesRevisados.size() > 0) {
                    // Accede a la base de datos para realizar la actualización
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    // Crea la consulta de actualización
                    String updateQuery = "UPDATE Alumno_Asignacion SET Realizada = ? WHERE Alumno_ID IN (" +
                            TextUtils.join(",", Collections.nCopies(estudiantesRevisados.size(), "?")) + ")";

                    // Crea el array de valores para la consulta de actualización
                    String[] updateValues = new String[estudiantesRevisados.size() + 1];
                    updateValues[0] = String.valueOf(estadoRevisada); // Valor para la columna "Realizada"

                    for (int i = 0; i < estudiantesRevisados.size(); i++) {
                        updateValues[i + 1] = estudiantesRevisados.get(i);
                    }

                    // Ejecuta la consulta de actualización
                    db.execSQL(updateQuery, updateValues);

                    // Cierra la conexión a la base de datos
                    db.close();

                    // Notifica al usuario que la actualización se realizó con éxito
                    Toast.makeText(requireContext(), "Cambios aplicados correctamente.", Toast.LENGTH_SHORT).show();
                } else {
                    // Notifica al usuario que no hay estudiantes seleccionados para actualizar
                    Toast.makeText(requireContext(), "No hay estudiantes seleccionados para actualizar.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        listViewStudent1.setAdapter(adapter1);
        listViewStudent2.setAdapter(adapter2);

        listViewStudent1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedStudent = studentListNo.get(position);
                //Toast.makeText(requireContext(), "Selected student: " + selectedStudent, Toast.LENGTH_SHORT).show();

                // Aquí puedes realizar acciones adicionales cuando se selecciona un estudiante
            }
        });

        listViewStudent2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedStudent = studentListSi.get(position);
                //Toast.makeText(requireContext(), "Selected student: " + selectedStudent, Toast.LENGTH_SHORT).show();

                // Aquí puedes realizar acciones adicionales cuando se selecciona un estudiante
            }
        });

        return rootView;
    }

    private void actualizarListViews() {
        // ... (tu código existente para obtener datos actualizados de la base de datos)

        // Crea nuevos adaptadores con los datos actualizados
        ArrayAdapter<String> newAdapter1 = new ArrayAdapter<>(requireContext(), R.layout.custom_list_item_check, R.id.text1, studentListNo);
        ArrayAdapter<String> newAdapter2 = new ArrayAdapter<>(requireContext(), R.layout.custom_list_item_check, R.id.text1, studentListSi);

        // Establece los nuevos adaptadores en los ListView
        listViewStudent1.setAdapter(newAdapter1);
        listViewStudent2.setAdapter(newAdapter2);
    }
}
