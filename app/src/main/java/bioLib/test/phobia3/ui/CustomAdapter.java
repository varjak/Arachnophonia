package bioLib.test.phobia3.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import bioLib.test.phobia3.R;
import bioLib.test.phobia3.model.VJRecord;

import java.util.ArrayList;

/**
 * Created by ricardo-abreu on 12/4/2017.
 */

public class CustomAdapter extends BaseAdapter {
    ArrayList<VJRecord> data;
    //List<VJRecord> data;
    Context context;
    private static LayoutInflater inflater = null;
    public CustomAdapter(Context context, ArrayList<VJRecord> data) {
    //public CustomAdapter(Context context, List<VJRecord> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// See if the view needs to be inflated
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, null);
        }
// Extract the desired views
        //TextView ecgText = (TextView) view.findViewById(R.id.ecgValue);
        TextView hrText = (TextView) view.findViewById(R.id.hrValue);
        //TextView rrText = (TextView) view.findViewById(R.id.rrValue);
        TextView dateText=(TextView) view.findViewById(R.id.dateValue) ;

// Get the data item
        VJRecord item = data.get(position);
// Display the data item's properties
        //ecgText.setText(item.getECG());
        String hr = String.format("%f", item.getHR());
        hrText.setText(hr);
        //String rr = String.format("%f", item.getRR());
        //rrText.setText(rr);
        //rrText.setText(item.getRR().toString());
        dateText.setText(item.getTime());

        return view;
    }
}