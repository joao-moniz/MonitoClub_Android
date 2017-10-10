package com.example.joao_.monitoria.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joao_.monitoria.Modelo.Unidade_Academica;
import com.example.joao_.monitoria.R;

import java.io.InputStream;
import java.util.List;

/**
 * Created by joao- on 08/06/2017.
 */

public class Adapter_ListView_Unidades_Academicas extends BaseAdapter {
    private Context mContext;
    private List<Unidade_Academica> unidade_academicas;

    public Adapter_ListView_Unidades_Academicas(Context mContext, List<Unidade_Academica> unidade_academicas){
        this.mContext = mContext;
        this.unidade_academicas = unidade_academicas;
    }
    public void updateResults(List<Unidade_Academica> results) {
        unidade_academicas = results;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return unidade_academicas.size();
    }

    @Override
    public Object getItem(int position) {
        return unidade_academicas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v =  View.inflate(mContext, R.layout.adapter_lista_cursos,null);

        TextView tvCurso = (TextView) v.findViewById(R.id.tvCurso);
        tvCurso.setText(unidade_academicas.get(position).getName());
        tvCurso.setTextSize(20);

        v.setTag(position);

        return v;
    }
}
