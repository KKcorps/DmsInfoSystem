package com.dmsinfosystem;

/**
 * Created by root on 6/12/14.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

class CustomAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<String> mData = new ArrayList<String>();
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    private LayoutInflater mInflater;
    private int LayoutHead, LayoutSub;

    public CustomAdapter(Context context, int layoutHead, int layoutSub) {
        LayoutHead = layoutHead;
        LayoutSub = layoutSub;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final String item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {

            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(LayoutSub, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.text);
                    break;

                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(LayoutHead, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);
                    break;
            }
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mData.get(position));

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated()
    {
        super.notifyDataSetInvalidated();
    }

    private Filter productFilter;

    public Filter getFilter() {
        if (productFilter == null)
            productFilter = new ProductFilter();

        return productFilter;
    }

    public class ProductFilter extends Filter {


        private List<String> productList = mData;

        public ProductFilter(List<String> productListFromActivity) {
            productList = productListFromActivity;
        }

        public ProductFilter(){

        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = productList;
                results.count = productList.size();
            }
            else {
                // We perform filtering operation
                List<String> nPlanetList = new ArrayList<String>();

                for (String p : productList) {
                    if (p.toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nPlanetList.add(p);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                productList = (List<String>) results.values;
                notifyDataSetChanged();
            }
        }
    }



}