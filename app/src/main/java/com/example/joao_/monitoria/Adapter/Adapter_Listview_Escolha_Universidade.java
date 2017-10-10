package com.example.joao_.monitoria.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.joao_.monitoria.Drawers.CircleProgressDrawable;
import com.example.joao_.monitoria.Modelo.Universidade;
import com.example.joao_.monitoria.R;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by joao- on 31/05/2017.
 */

public class Adapter_Listview_Escolha_Universidade extends BaseAdapter{
    private Context mContext;
    private List<Universidade> universidades;

    public Adapter_Listview_Escolha_Universidade(Context mContext, List<Universidade> universidades){
        this.mContext = mContext;
        this.universidades = universidades;
    }

    public void updateResults(List<Universidade> results) {
        universidades = results;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return universidades.size();
    }

    @Override
    public Object getItem(int position) {
        return universidades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.adapter_lista_universidades, null);

        TextView tvCurso = (TextView) v.findViewById(R.id.tvNameUniversidade);
        tvCurso.setText(universidades.get(position).getName());
        SimpleDraweeView draweeView = (SimpleDraweeView) v.findViewById(R.id.imUniversidade);

        if (universidades.get(position).getImagem_url() != null) {

            Uri uri = Uri.parse(universidades.get(position).getImagem_url());
            draweeView.setImageURI(uri);
        }
        else{
            draweeView.setImageResource(R.drawable.school);
        }
        v.setTag(position);
        return v;
    }
}
