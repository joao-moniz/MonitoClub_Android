package com.example.joao_.monitoria.Fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.joao_.monitoria.Adapter.Adapter_Listview_Lista_Monitorias;
import com.example.joao_.monitoria.Modelo.MonitoriaClass;
import com.example.joao_.monitoria.R;

import java.util.ArrayList;


/**
 * Created by João MOniz on 15/06/2017.
 * Projeto monitoria dialog cadastro Unidade Academica
 */

public class DialogFragment_ListaMonitorias extends DialogFragment {
    ArrayList<MonitoriaClass> monitorias;
    String monitorName;
    Adapter_Listview_Lista_Monitorias adapter;

    ListView lvMonitorias;
    TextView tvMonitorName;

    public static DialogFragment_ListaMonitorias newInstance() {
        DialogFragment_ListaMonitorias f = new DialogFragment_ListaMonitorias();
        f.setShowsDialog(true);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        monitorias = new ArrayList<>();
        monitorias = (ArrayList<MonitoriaClass>) getArguments().getSerializable("monitorias");
        monitorName = getArguments().getString("monitorName");

        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.custom_dialog_lista_monitorias, container, false);
        tvMonitorName = (TextView) v.findViewById(R.id.tv_listaMonitorias_MonitorName);
        tvMonitorName.setText(monitorName);

        adapter = new Adapter_Listview_Lista_Monitorias(getContext(),monitorias);
        lvMonitorias = (ListView) v.findViewById(R.id.lv_ListaMonistoria_Monitrias);
        lvMonitorias.setAdapter(adapter);

        return v;
    }

}
