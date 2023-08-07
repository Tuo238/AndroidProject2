package com.example.projectandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ToppingAdapter_update extends ArrayAdapter {

    Context context;
    ArrayList<Topping> arrayList;
    int layout;
    public ToppingAdapter_update(@NonNull Context context, int resource, @NonNull ArrayList<Topping> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrayList = objects;
        this.layout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Topping tp = arrayList.get(position);
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
        }
        TextView txt1 = convertView.findViewById(R.id.tv_name_topping);
        txt1.setText(tp.getName());
        TextView txt2 = convertView.findViewById(R.id.tv_money);
        txt2.setText(String.valueOf(tp.getGiatien()));
        CheckBox cb = convertView.findViewById(R.id.cB_topping);

        return convertView;
    }

}
