package com.example.joao_.monitoria.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.joao_.monitoria.Modelo.Materia;
import com.example.joao_.monitoria.Modelo.Unidade_Academica;
import com.example.joao_.monitoria.R;

import java.util.List;

/**
 * Created by joao- on 08/06/2017.
 */

public class Adapter_ListView_Materias extends BaseAdapter {
    private Context mContext;
    private List<Materia> materias;

    public Adapter_ListView_Materias(Context mContext, List<Materia> materias){
        this.mContext = mContext;
        this.materias = materias;
    }
    public void updateResults(List<Materia> results) {
        materias = results;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return materias.size();
    }

    @Override
    public Object getItem(int position) {
        return materias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =  View.inflate(mContext, R.layout.adapter_lista_cursos,null);

        TextView tvCurso = (TextView) v.findViewById(R.id.tvCurso);
        tvCurso.setText(materias.get(position).getDescricao());
        tvCurso.setTextSize(20);

        v.setTag(position);

        return v;
    }
}
