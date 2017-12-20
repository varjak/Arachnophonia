package bioLib.test.phobia3.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import bioLib.test.phobia3.R;

import java.util.ArrayList;

/**
 * Created by ricardo-abreu on 12/8/2017.
 */

public class AdapterForSeries extends BaseAdapter {

    ArrayList<String> data;
    Context context;
    private static LayoutInflater inflater = null;
    public AdapterForSeries(Context context, ArrayList<String> data) {
        //public CustomAdapter(Context context, List<VJRecord> data) {
        this.context = context;
        this.data=data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
// See if the view needs to be inflated
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.series_item, null);
        }
// Extract the desired views
        TextView series_item=(TextView) view.findViewById(R.id.series_item);
        String series_num = data.get(position);
        series_item.setText(series_num);
        return view;
    }

    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

}
