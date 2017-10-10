package com.example.joao_.monitoria.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.joao_.monitoria.Modelo.Materia;
import com.example.joao_.monitoria.Modelo.Monitor;
import com.example.joao_.monitoria.R;

import java.util.List;

/**
 * Created by joao- on 08/06/2017.
 */

public class Adapter_ListView_Monitores extends BaseAdapter {
    private Context mContext;
    private List<Monitor> monitores;

    public Adapter_ListView_Monitores(Context mContext, List<Monitor> materias){
        this.mContext = mContext;
        this.monitores = materias;
    }
    public void updateResults(List<Monitor> results) {
        monitores = results;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return monitores.size();
    }

    @Override
    public Object getItem(int position) {
        return monitores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =  View.inflate(mContext, R.layout.adapter_lista_cursos,null);

        TextView tvCurso = (TextView) v.findViewById(R.id.tvCurso);
        tvCurso.setText(monitores.get(position).getName());
        tvCurso.setTextSize(20);

        v.setTag(position);

        return v;
    }
}
