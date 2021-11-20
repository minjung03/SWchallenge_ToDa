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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ListAdapter extends BaseAdapter {

    /*
                            // String.valueOf(listChk_cnt) 리스트 개수 변수 출력
                        // list.get(i).getList_value().toString() 내용출력
                        // String.valueOf(getCount()) 현재 리스트 전체 개수 or db count
     */

    ArrayList<List> list = new ArrayList<List>();
    ListDBHelper listDBHelper;
    SQLiteDatabase listDB;
    TodoList mtodoList;

    String id;

    ListAdapter(TodoList activity){
        mtodoList = activity;
        id = mtodoList.tx_getID.getText().toString();
    }

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
        CheckBox list_chk = (CheckBox) view.findViewById(R.id.list_chk);

        List listitem = list.get(i);

        //가져온 객체안에 있는 글자들을 각 뷰에 적용한다
        list_value.setText(listitem.getList_value());

     //   Toast.makeText(context, listdata.getList_chk() ,Toast.LENGTH_SHORT).show();
          if(list.get(i).getList_chk() == "true"){
                list_chk.setChecked(true);
          } else list_chk.setChecked(false);

        btn_list_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String listvalue = listitem.getList_value();

                    listDB = listDBHelper.getWritableDatabase();

                    String list_sql = "DELETE FROM listTBL WHERE userid = '"+id+"' And list_value = '" + listvalue + "';";

                    listDB.execSQL(list_sql);
                    listDB.close();
                    list.remove(i);
                    notifyDataSetChanged();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context,"실패하였습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        list_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.list_chk){
                    if(list_chk.isChecked()){

                        list.get(i).setList_chk("true");
                        listDB = listDBHelper.getWritableDatabase();
                        String list_sql = "UPDATE listTBL SET list_chk = 'true' WHERE userid = '"+id+"' And list_value = '" + list.get(i).getList_value().toString() + "';";
                        listDB.execSQL(list_sql);
                        listDB.close();
                        notifyDataSetChanged();
                    }

                    else {
                        list.get(i).setList_chk("false");
                        listDB = listDBHelper.getWritableDatabase();
                        String list_sql = "UPDATE listTBL SET list_chk = 'false' WHERE userid = '"+id+"' And list_value = '" + list.get(i).getList_value().toString() + "';";
                        listDB.execSQL(list_sql);
                        listDB.close();
                        notifyDataSetChanged();
                    }
                }
            }
        });
        return view;
    }

    //ArrayList로 선언된 list 변수에 목록을 채워주기 위함 다른방시으로 구현해도 됨
    public void addItemToList(String userid, String list_value, String list_chk){

        List listdata = new List();
        listdata.setUserid(userid);
        listdata.setList_value(list_value);
        if(list_chk.equals("true")){
            listdata.setList_chk("true");
        }
        else listdata.setList_chk("false");

        // 값들의 조립이 완성된 listdata객체 한개를 list배열에 추가
        list.add(listdata);

    }
}