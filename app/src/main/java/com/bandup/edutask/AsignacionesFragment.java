package com.bandup.edutask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bandup.edutask.BaseDatosHelper;
import com.bandup.edutask.R;

public class AsignacionesFragment extends Fragment {

    private ListView listViewAssignments;
    private BaseDatosHelper baseDatosHelper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AsignacionesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AsignacionesFragment newInstance(String param1, String param2) {
        AsignacionesFragment fragment = new AsignacionesFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_asignaciones, container, false);

        // Inicializa el BaseDatosHelper
        baseDatosHelper = new BaseDatosHelper(requireContext());

        // Obtén la referencia del ListView desde el diseño del fragmento
        listViewAssignments = rootView.findViewById(R.id.listViewAssignments);

        // Recupera el ID de la materia de los argumentos
        int materiaId = getArguments().getInt("materiaId", -1);

        // Ahora puedes usar el ID de la materia para obtener las asignaciones correspondientes
        if (materiaId != -1) {
            Cursor cursorAsignaciones = baseDatosHelper.getAsignacionesPorMateria(materiaId);

            // Configura el adaptador y realiza otras configuraciones según sea necesario
            String[] items = new String[cursorAsignaciones.getCount()];
            String[] subItems = new String[cursorAsignaciones.getCount()];

            // Llena los arreglos con datos del cursor
            if (cursorAsignaciones.moveToFirst()) {
                int i = 0;
                do {
                    items[i] = cursorAsignaciones.getString(cursorAsignaciones.getColumnIndexOrThrow(BaseDatosHelper.COL_ASIGNACION_NOMBRE));
                    subItems[i] = cursorAsignaciones.getString(cursorAsignaciones.getColumnIndexOrThrow(BaseDatosHelper.COL_ASIGNACION_FECHA));
                    i++;
                } while (cursorAsignaciones.moveToNext());
            }

            // Crea un adaptador personalizado para el ListView
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.custom_list_item, R.id.text1, items) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);

                    // Configura el texto del subitem
                    TextView text2 = view.findViewById(R.id.text2);
                    text2.setText(subItems[position]);

                    // Configura el icono de lápiz
                    ImageView imageViewEdit = view.findViewById(R.id.imageViewEdit);
                    imageViewEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Obtener datos de la asignación seleccionada
                            String nombreAsignacion = items[position];
                            String fechaAsignacion = subItems[position];

                            // Inflar la vista del diálogo de edición
                            View dialogView = getLayoutInflater().inflate(R.layout.activity_editar_asignacion, null);

                            // Configurar los campos EditText con los datos recuperados
                            EditText edtNombreAsignacionEditar = dialogView.findViewById(R.id.edtNombreAsignacionEditar);
                            EditText txtFechaEntregaAsigEditar = dialogView.findViewById(R.id.txtFechaEntregaAsigEditar);

                            edtNombreAsignacionEditar.setText(nombreAsignacion);
                            txtFechaEntregaAsigEditar.setText(fechaAsignacion);

                            // Lógica cuando se hace clic en el icono de lápiz
                            AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                            builder.setTitle("Editar")
                                    .setView(dialogView)
                                    .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // Obtener los nuevos datos de los campos de edición
                                            String nuevoNombre = edtNombreAsignacionEditar.getText().toString();
                                            String nuevaFecha = txtFechaEntregaAsigEditar.getText().toString();
                                            int idAsignacion = baseDatosHelper.getIdAsignacionPorNombreYFecha(nombreAsignacion, fechaAsignacion);


                                            // Aquí debe ir el código para la edición
                                            boolean exito = baseDatosHelper.actualizarAsignacion(idAsignacion, nuevoNombre, nuevaFecha);
                                            if (exito) {
                                                Toast.makeText(v.getContext(), "Asignación editada con éxito", Toast.LENGTH_SHORT).show();
                                                // Realizar cualquier otra acción después de la edición
                                            } else {
                                                Toast.makeText(v.getContext(), "Error al editar la asignación", Toast.LENGTH_SHORT).show();
                                            }

                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setNeutralButton("Eliminar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            // Obtener el ID de la asignación
                                            int idAsignacion = baseDatosHelper.getIdAsignacionPorNombreYFecha(nombreAsignacion, fechaAsignacion);

                                            // Realizar la lógica para eliminar la asignación
                                            if (idAsignacion != -1) {
                                                baseDatosHelper.deleteAsignacion(idAsignacion);
                                                Toast.makeText(v.getContext(), "Asignación eliminada con éxito", Toast.LENGTH_SHORT).show();
                                            }

                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .create()
                                    .show();
                        }

                    });

                    return view;
                }
            };

            // Configura el adaptador en el ListView
            listViewAssignments.setAdapter(adapter);
        }

        listViewAssignments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AsignacionFragment asignacionFragment = new AsignacionFragment();

                //Pasar datos al fragment
                // Obtener el FragmentManager y comenzar la transacción

                asignacionFragment.setArguments(savedInstanceState);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contenedor1, asignacionFragment)
                        .addToBackStack(null)  // Agregar a la pila de retroceso para permitir la navegación hacia atrás
                        .commit();
                listViewAssignments.setVisibility(View.GONE);

            }
        });
        return rootView;
    }
}
