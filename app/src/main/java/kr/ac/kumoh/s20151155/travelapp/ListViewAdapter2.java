package kr.ac.kumoh.s20151155.travelapp;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter2 extends BaseAdapter {

    private ArrayList<ListViewItem2> listViewItemList = new ArrayList<ListViewItem2>();

    //ListViewAdapter 생성자
    public ListViewAdapter2(){

    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_view_item2, parent, false);
        }


        TextView idTextView = (TextView)convertView.findViewById(R.id.id);
        TextView dateTextView = (TextView)convertView.findViewById(R.id.date);
        TextView costTextView = (TextView)convertView.findViewById(R.id.cost);

        ListViewItem2 listViewItem = listViewItemList.get(position);

        idTextView.setText(listViewItem.getUserId());
        dateTextView.setText(" " + listViewItem.getYear() + "년 " + listViewItem.getMonth() + "월 " + listViewItem.getDay() + "일" );
        costTextView.setText(listViewItem.getCost() + " 원");



        return convertView;
    }

    public void addItem(int num, String id, int year, int month, int day, int cost ){
        ListViewItem2 item = new ListViewItem2();

        item.setNum(num);
        item.setUserId(id);
        item.setYear(year);
        item.setMonth(month);
        item.setDay(day);
        item.setCost(cost);

        listViewItemList.add(item);
    }
}
