package com.example.joao_.monitoria.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.joao_.monitoria.R;

import java.util.List;

/**
 * Created by joao- on 31/05/2017.
 */

public class ListViewAdapterCursos extends BaseAdapter{
    private Context mContext;
    private List<String> Cursos;

    public ListViewAdapterCursos(Context mContext, List<String> Cursos){
        this.mContext = mContext;
        this.Cursos = Cursos;
    }

    public void updateResults(List<String> results) {
        Cursos = results;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return Cursos.size();
    }

    @Override
    public Object getItem(int position) {
        return Cursos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =  View.inflate(mContext, R.layout.adapter_lista_cursos,null);

        TextView tvCurso = (TextView) v.findViewById(R.id.tvCurso);
        tvCurso.setText(Cursos.get(position));

        v.setTag(position);
        return v;
    }
}
