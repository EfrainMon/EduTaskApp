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
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bandup.edutask.BaseDatosHelper;
import com.bandup.edutask.R;

import java.util.Comparator;

public class AsignacionesFragment extends Fragment {

    private ListView listViewAssignments;
    private BaseDatosHelper baseDatosHelper;

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

        Cursor cursorMaterias = baseDatosHelper.getAsignacionesPorMateria(materiaId);

        // Configura el adaptador
        String[] fromColumns = {
                BaseDatosHelper.getMateriaNombreColumn(),
                BaseDatosHelper.COL_ASIGNACION_FECHA
        };
        int[] toViews = {
                R.id.textViewMateriaNombre,
                R.id.textViewFechaAsignacion
        };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item_materia,
                cursorMaterias,
                fromColumns,
                toViews,
                0
        );
        // Asigna el adaptador al ListView
        listViewAssignments.setAdapter(adapter);

        // Ahora puedes usar el ID de la materia para obtener las asignaciones correspondientes
        /*if (materiaId != -1) {
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

                    // Nuevoo, Ordena los elementos alfabéticamente
                    this.sort(new Comparator<String>() {
                        @Override
                        public int compare(String s1, String s2) {
                            return s1.compareToIgnoreCase(s2);
                        }
                    });

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
                // Obtén el ID de la materia seleccionada
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int materiaId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                // Abre el fragmento de Asignaciones pasando el ID de la materia seleccionada
                openAsignacionesFragment(materiaId);
            }
        });*/

        /*listViewAssignments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtén el ID de la materia seleccionada
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int materiaId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));


                // Abre el fragmento de Asignaciones pasando el ID de la materia seleccionada
                openAsignacionesFragment(materiaId);
            }
        });*/

        listViewAssignments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtén el ID de la asignación seleccionada
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                int asignacionId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));

                // Obtén el ID de la materia (ya lo tienes en los argumentos)
                int materiaId = getArguments().getInt("materiaId", -1);
                //Log.d("MateriaId = ", materiaId + "");
                //Log.d("AsignacionId = ", asignacionId + "");

                // Abre el fragmento de Asignaciones pasando el ID de la asignación y de la materia seleccionada
                openAsignacionesFragment(asignacionId, materiaId);
            }
        });


        listViewAssignments.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtén los datos de la asignación seleccionada
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                String nombreAsignacion = cursor.getString(cursor.getColumnIndexOrThrow(BaseDatosHelper.COL_ASIGNACION_NOMBRE));
                String fechaAsignacion = cursor.getString(cursor.getColumnIndexOrThrow(BaseDatosHelper.COL_ASIGNACION_FECHA));

                // Inflar la vista del diálogo de edición
                View dialogView = getLayoutInflater().inflate(R.layout.activity_editar_asignacion, null);

                // Configurar los campos EditText con los datos recuperados
                EditText edtNombreAsignacionEditar = dialogView.findViewById(R.id.edtNombreAsignacionEditar);
                EditText txtFechaEntregaAsigEditar = dialogView.findViewById(R.id.txtFechaEntregaAsigEditar);

                edtNombreAsignacionEditar.setText(nombreAsignacion);
                txtFechaEntregaAsigEditar.setText(fechaAsignacion);

                // Lógica cuando se mantiene presionado un elemento
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Editar Asignación")
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
                                    Toast.makeText(requireContext(), "Asignación editada con éxito", Toast.LENGTH_SHORT).show();
                                    // Realizar cualquier otra acción después de la edición
                                } else {
                                    Toast.makeText(requireContext(), "Error al editar la asignación", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(requireContext(), "Asignación eliminada con éxito", Toast.LENGTH_SHORT).show();
                                }

                                dialogInterface.dismiss();
                            }
                        })
                        .create()
                        .show();

                // Devolver true para indicar que se ha manejado el evento
                return true;
            }
        });


        return rootView;
    }

/*    private void openAsignacionesFragment(int asigId) {
        AsignacionFragment asignacionesFragment = new AsignacionFragment();

        // Puedes pasar el ID de la materia como argumento al fragmento de Asignaciones
        Bundle args = new Bundle();
        args.putInt("asigId", asigId);
        asignacionesFragment.setArguments(args);

        // Obtén el FragmentManager y comienza la transacción
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor1, asignacionesFragment)
                .addToBackStack(null)  // Agregar a la pila de retroceso para permitir la navegación hacia atrás
                .commit();
        listViewAssignments.setVisibility(View.GONE);
    }*/
    private void openAsignacionesFragment(int asigId, int materiaId) {
        AsignacionFragment asignacionesFragment = new AsignacionFragment();

        // Puedes pasar tanto el ID de la asignación como el ID de la materia como argumentos al fragmento de Asignaciones
        Bundle args = new Bundle();
        args.putInt("asigId", asigId);
        args.putInt("materiaId", materiaId);
        asignacionesFragment.setArguments(args);

        // Obtén el FragmentManager y comienza la transacción
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.contenedor1, asignacionesFragment)
                .addToBackStack(null)  // Agregar a la pila de retroceso para permitir la navegación hacia atrás
                .commit();
        listViewAssignments.setVisibility(View.GONE);
    }


}