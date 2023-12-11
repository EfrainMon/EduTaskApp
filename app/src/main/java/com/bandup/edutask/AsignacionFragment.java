package com.bandup.edutask;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AsignacionFragment extends Fragment {

    private ListView listViewStudent1;
    private ListView listViewStudent2;
    private BaseDatosHelper dbHelper;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_asignacion, container, false);

        listViewStudent1 = rootView.findViewById(R.id.ListViewStudent1);
        listViewStudent2 = rootView.findViewById(R.id.ListViewStudent2);

        dbHelper = new BaseDatosHelper(requireContext());

        Cursor cursor = dbHelper.getAllStudents();
        List<String> studentList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String studentName = cursor.getString(cursor.getColumnIndexOrThrow("NombreCompleto"));
                studentList.add(studentName);
            } while (cursor.moveToNext());

            cursor.close();
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(requireContext(), R.layout.custom_list_item_check, R.id.text1, studentList) {
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
                        String selectedStudent = studentList.get(position);
                        Toast.makeText(requireContext(), "Checkbox de no revisadas de: " + selectedStudent, Toast.LENGTH_SHORT).show();

                        // Aquí puedes realizar acciones adicionales cuando se selecciona el CheckBox de un estudiante
                    }
                });

                return view;
            }
        };

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(requireContext(), R.layout.custom_list_item_check, R.id.text1, studentList) {
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
                        String selectedStudent = studentList.get(position);
                        Toast.makeText(requireContext(), "Checkbox de Revisadas de: " + selectedStudent, Toast.LENGTH_SHORT).show();

                        // Aquí puedes realizar acciones adicionales cuando se selecciona el CheckBox de un estudiante
                    }
                });

                return view;
            }
        };

        listViewStudent1.setAdapter(adapter1);
        listViewStudent2.setAdapter(adapter2);

        listViewStudent1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedStudent = studentList.get(position);
                Toast.makeText(requireContext(), "Selected student: " + selectedStudent, Toast.LENGTH_SHORT).show();

                // Aquí puedes realizar acciones adicionales cuando se selecciona un estudiante
            }
        });

        listViewStudent2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedStudent = studentList.get(position);
                Toast.makeText(requireContext(), "Selected student: " + selectedStudent, Toast.LENGTH_SHORT).show();

                // Aquí puedes realizar acciones adicionales cuando se selecciona un estudiante
            }
        });

        return rootView;
    }
}
