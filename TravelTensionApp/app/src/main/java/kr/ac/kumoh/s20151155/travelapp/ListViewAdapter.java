package kr.ac.kumoh.s20151155.travelapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    private ArrayList<ListViewItem> filteredItemList = listViewItemList;

    Filter listFilter;

    //ListViewAdapter 생성자
    public ListViewAdapter(){

    }

    @Override
    public int getCount() {
        return filteredItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredItemList.get(position);
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
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
        TextView nameTextView = (TextView)convertView.findViewById(R.id.name);
        TextView fightTextView = (TextView)convertView.findViewById(R.id.fight_cost);
        TextView freightTextView = (TextView)convertView.findViewById(R.id.freight_cost);
        TextView specialTextView = (TextView)convertView.findViewById(R.id.special_cost);
        TextView chargeTextView = (TextView)convertView.findViewById(R.id.charge_cost);

        ListViewItem listViewItem = filteredItemList.get(position);

        imageView.setImageBitmap(listViewItem.getImage());
        nameTextView.setText(listViewItem.getCompanyName());
        fightTextView.setText(listViewItem.getFightCost()+"원");
        freightTextView.setText(listViewItem.getFreightCost()+"원");
        specialTextView.setText(listViewItem.getSpecialCost()+"원");
        chargeTextView.setText(listViewItem.getChargeCost()+"원");

        Log.d("adapter", listViewItem.getCompanyName());

        return convertView;
    }

    public void addItem(String name, int fight, int freight, int special, int charge, Bitmap image){
        ListViewItem item = new ListViewItem();

        item.setCompanyName(name);
        item.setFightCost(fight);
        item.setFreightCost(freight);
        item.setSpecialCost(special);
        item.setChargeCost(charge);
        item.setImage(image);

        Log.d("addItem", name);

        listViewItemList.add(item);
    }

    @Override
    public Filter getFilter() {
        if (listFilter == null) {
            listFilter = new ListFilter() ;
        }

        return listFilter ;
    }

    private class ListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults() ;

            if (constraint == null || constraint.length() == 0) {
                results.values = listViewItemList ;
                results.count = listViewItemList.size() ;
            } else {
                ArrayList<ListViewItem> itemList = new ArrayList<ListViewItem>() ;

                for (ListViewItem item : listViewItemList) {
                    if (item.getCompanyName().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        itemList.add(item) ;
                    }
                }

                results.values = itemList ;
                results.count = itemList.size() ;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            // update listview by filtered data list.
            filteredItemList = (ArrayList<ListViewItem>) results.values ;

            // notify
            if (results.count > 0) {
                notifyDataSetChanged() ;
            } else {
                notifyDataSetInvalidated() ;
            }
        }
    }
}
