package com.mbp.sudoku.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.mbp.sudoku.R;
import com.mbp.sudoku.entity.GameMap;

import java.util.List;

public class MapAdapter extends ArrayAdapter<GameMap> {

    private int resourceId;

    public MapAdapter( Context context, int textViewResourceId, List<GameMap> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GameMap gameMapEntity = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tv_id = view.findViewById(R.id.map_id);
        TextView tv_time = view.findViewById(R.id.map_time);
        TextView tv_status = view.findViewById(R.id.map_status);
        String level = "第" + gameMapEntity.getId().toString() + "关";
        tv_id.setText(level);
        tv_time.setText(gameMapEntity.getGoodTime());
        tv_status.setText(gameMapEntity.getStatus() == -1 ? "锁定" : "正常");
        return view;
    }
}
