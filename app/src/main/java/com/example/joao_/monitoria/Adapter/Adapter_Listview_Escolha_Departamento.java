package com.example.joao_.monitoria.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.joao_.monitoria.Modelo.Departamento;
import com.example.joao_.monitoria.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by joao- on 31/05/2017.
 */

public class Adapter_Listview_Escolha_Departamento extends BaseAdapter{
    private Context mContext;
    private List<Departamento> departamentos;

    public Adapter_Listview_Escolha_Departamento(Context mContext, List<Departamento> departamentos){
        this.mContext = mContext;
        this.departamentos = departamentos;
    }

    public void updateResults(List<Departamento> results) {
        departamentos = results;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return departamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return departamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.adapter_lista_departamento, null);

        TextView tvCurso = (TextView) v.findViewById(R.id.tv_AdapaterListaDepartamento_NameDepartamento);
        tvCurso.setText(departamentos.get(position).getName());
        SimpleDraweeView draweeView = (SimpleDraweeView) v.findViewById(R.id.im_AdapaterListaDepartamento_icone);

        if (departamentos.get(position).getIconeUrl() != null) {

            Uri uri = Uri.parse(departamentos.get(position).getIconeUrl());
            draweeView.setImageURI(uri);
        }
        else{
            draweeView.setImageResource(R.drawable.school);
        }
        v.setTag(position);
        return v;
    }
}
