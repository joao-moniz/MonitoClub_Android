package com.example.joao_.monitoria.Fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.joao_.monitoria.Adapter.Adapter_Listview_Escolha_Universidade;
import com.example.joao_.monitoria.Modelo.Universidade;
import com.example.joao_.monitoria.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by joao- on 07/06/2017.
 */

public class Fragment_Lista_Universidades extends Fragment {
    Adapter_Listview_Escolha_Universidade adapter;
    ArrayList<Universidade> universidades;
    OnArticleSelectedListener mListener;
    ListView listViewUniversidades;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_escolha_universidade, container, false);

        universidades = new ArrayList<>();
        adapter = new Adapter_Listview_Escolha_Universidade(getActivity().getApplicationContext(),universidades);
        listViewUniversidades = (ListView) v.findViewById(R.id.lvUniversidades);
        listViewUniversidades.setEmptyView(v.findViewById(R.id.emptyElement));
        listViewUniversidades.setAdapter(adapter);
        listViewUniversidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onArticleSelected(universidades.get(position));
            }
        });
        BuscarUniversidades();
        return v;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    public interface OnArticleSelectedListener {
        public void onArticleSelected(Universidade universidade);

    }
    public void BuscarUniversidades(){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Universidades");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    GenericTypeIndicator<HashMap<String, Universidade>> t = new GenericTypeIndicator<HashMap<String, Universidade>>() {};
                    HashMap<String, Universidade> beans = dataSnapshot.getValue(t);
                    Set set = beans.entrySet();
                    Iterator i = set.iterator();
                    while(i.hasNext()){
                        Map.Entry me = (Map.Entry) i.next();
                        universidades.add((Universidade) me.getValue());
                    }
                    adapter.updateResults(universidades);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
