package com.bandup.edutask;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AsignacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsignacionFragment extends Fragment {

    private ListView ListViewStudent1;
    private ListView ListViewStudent2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AsignacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AsignacionFragment.
     */
    // TODO: Rename and change types and number of parameters
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

        ListViewStudent1 = rootView.findViewById(R.id.ListViewStudent1);
        ListViewStudent2 = rootView.findViewById(R.id.ListViewStudent2);
        // Datos de ejemplo para la lista
        String[] items = {"Efraín Montalvo", "Ernesto Molina", "Tomás Galván", "Ygnacio Martinez"};
        String[] subItems = {"No. 20", "No. 16", "No. 10", "No. 15"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.custom_list_item_check, R.id.text1, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                // Configura el texto del subitem
                TextView text2 = view.findViewById(R.id.text2);
                text2.setText(subItems[position]);
                return view;
            }
        };
        ListViewStudent1.setAdapter(adapter);
        ListViewStudent2.setAdapter(adapter);
        // Inflate the layout for this fragment
        return rootView;



    }
}