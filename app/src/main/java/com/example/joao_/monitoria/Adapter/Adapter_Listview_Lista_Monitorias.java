package com.example.joao_.monitoria.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.joao_.monitoria.Modelo.Departamento;
import com.example.joao_.monitoria.Modelo.MonitoriaClass;
import com.example.joao_.monitoria.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by joao- on 31/05/2017.
 */

public class Adapter_Listview_Lista_Monitorias extends BaseAdapter{
    private Context mContext;
    private List<MonitoriaClass> monitorias;

    public Adapter_Listview_Lista_Monitorias(Context mContext, List<MonitoriaClass> monitorias){
        this.mContext = mContext;
        this.monitorias = monitorias;
    }

    public void updateResults(List<MonitoriaClass> results) {
        monitorias = results;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return monitorias.size();
    }

    @Override
    public Object getItem(int position) {
        return monitorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.adapter_lista_monitoria, null);

        TextView tvDiaSemana = (TextView) v.findViewById(R.id.tv_AdapterMonitoria_DiaSemana);
        tvDiaSemana.setText(monitorias.get(position).getDiaSemana());

        TextView tvInicio = (TextView) v.findViewById(R.id.tv_adapterMonitoria_inici);
        tvInicio.setText(monitorias.get(position).getInicio());

        TextView tvFim = (TextView) v.findViewById(R.id.tv_AdapterMonitoria_fim);
        tvFim.setText(monitorias.get(position).getFim());

        TextView tvSala = (TextView) v.findViewById(R.id.tv_AdapterMonitoria_Sala);
        tvSala.setText("Sala: " + monitorias.get(position).getSalas());

        v.setTag(position);
        return v;
    }
}
