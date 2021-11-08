package com.cookandroid.to_da_project;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    Context mContext; // 메인 액티버티의 컨텍스트를 저장
    ArrayList<List> listData = new ArrayList<List>(); // ListViewitem 아이템 데이터를 저장한 배열리스트를 저장

    public ListAdapter(){ }

    public ListAdapter(Context mContext, ArrayList<List> mData) { // 어댑터 생성시 컨텍스트와 데이터배열 가져옴
        this.mContext = mContext;
        this.listData = mData;
    }

    // 리스트 객체 내의 아이템의 개수를 반환
    @Override
    public int getCount() {
        return listData.size();
    }

    // 전달받은 position의 위치에 해당하는 리스트 객체 아이템을 반환
    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    // 전달받은 position의 위치에 해당하는 리스트 객체 아이템의 행번호를 반환
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.todolist_custom, parent, false);
        }
        List listViewItem = listData.get(position);

        TextView list_num = convertView.findViewById(R.id.tx_todolist_num);
        TextView list_value = convertView.findViewById(R.id.tx_todolist_value);

        list_num.setText(listViewItem.getNum());
        list_value.setText(listViewItem.getList_value());

        return convertView;
    }

    public void addItem(String num , String value){
        List item = new List();
        item.setNum(num);
        item.setList_value(value);

        listData.add(item);
    }
}
