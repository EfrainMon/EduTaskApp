package com.bandup.edutask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AsignacionesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsignacionesFragment extends Fragment {

    private ListView listViewAssignments;

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

        // Obtén la referencia del ListView desde el diseño del fragmento
        listViewAssignments = rootView.findViewById(R.id.listViewAssignments);

        // Datos de ejemplo para la lista
        String[] items = {"Item 1", "Item 2", "Item 3"};
        String[] subItems = {"Subitem 1", "Subitem 2", "Subitem 3"};

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
                        // Lógica cuando se hace clic en el icono de lápiz
                        v = getLayoutInflater().inflate(R.layout.activity_editar_asignacion, null);
                        AlertDialog.Builder builder = new AlertDialog.Builder ( v.getContext() );
                        builder.setTitle ( "Editar" )
                                .setView ( v )
                                .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Aqui debe ir el codigo para la edicion

                                            dialogInterface.dismiss();
                                        //Toast.makeText(v.getContext(), "Se ha Editado con Exito", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
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
