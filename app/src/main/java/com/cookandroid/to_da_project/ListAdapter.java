package com.cookandroid.to_da_project;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ListAdapter extends BaseAdapter {

    ArrayList<List> list = new ArrayList<List>();
    ListDBHelper listDBHelper;
    SQLiteDatabase listDB;

    @Override
    public int getCount() {
        return list.size(); //그냥 배열의 크기를 반환하면 됨
    }

    @Override
    public Object getItem(int i) {
        return list.get(i); //배열에 아이템을 현재 위치값을 넣어 가져옴
    }

    @Override
    public long getItemId(int i) {
        return i; //그냥 위치값을 반환해도 되지만 원한다면 아이템의 num 을 반환해도 된다.
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();
        listDBHelper = new ListDBHelper(context);

        //리스트뷰에 아이템이 인플레이트 되어있는지 확인한후
        //아이템이 없다면 아래처럼 아이템 레이아웃을 인플레이트 하고 view객체에 담는다.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.todolist_custom, viewGroup, false);
        }
        //이제 아이템에 존재하는 텍스트뷰 객체들을 view객체에서 찾아 가져온다
        TextView list_value = (TextView) view.findViewById(R.id.tx_todolist_value);
        Button btn_list_delete = (Button) view.findViewById(R.id.btn_list_delete);

        List listitem = list.get(i);

        btn_list_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String list_value = listitem.getList_value();
                    list.remove(i);

                    listDB = listDBHelper.getWritableDatabase();
                    String list_sql = "DELETE FROM listTBL WHERE list_value= '" + list_value + "'";

                    listDB.execSQL(list_sql);
                    listDB.close();
                    notifyDataSetChanged();

                } catch (Exception e) {

                }
            }
        });
        //가져온 객체안에 있는 글자들을 각 뷰에 적용한다
        list_value.setText(listitem.getList_value());

        return view;
    }

    //ArrayList로 선언된 list 변수에 목록을 채워주기 위함 다른방시으로 구현해도 됨
    public void addItemToList(String userid, String name){
        List listdata = new List();

        listdata.setUserid(userid);
        listdata.setList_value(name);

        //값들의 조립이 완성된 listdata객체 한개를 list배열에 추가
        list.add(listdata);
    }
}