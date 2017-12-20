package bioLib.test.phobia3.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.io.File;
import java.util.ArrayList;

import bioLib.test.phobia3.MenuActivity;
import bioLib.test.phobia3.R;
import bioLib.test.phobia3.model.VJRecord;
import bioLib.test.phobia3.sqlite.MySQLiteHelper;
import bioLib.test.phobia3.ui.AdapterForSeries;

public class FirstFragment extends Fragment {

    //private TextView txt_chosenPoint;
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;

    private ArrayList<VJRecord> list_thisSession;
    private ArrayList<VJRecord> list_allSessions;
    private ArrayList<VJRecord> list_chosenSession;
    private Button btn_back;

    private GridView gridView;
    //private CustomAdapter adapter;
    private AdapterForSeries series_adapter;

    private MySQLiteHelper db;
    private int selectedPosition=-1;
    private int session=0;
    private ArrayList<String> session_names;

    static Integer point1_x=null;
    static Integer point1_y=null;
    static Integer point1_session=null;


    PopUp pop1;
    ImageView image;

    Button btn_compare;

    public FirstFragment() {
// Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, null);
        getAllWidgets(rootView);
        //setAdapter();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_first, container, false);
        return rootView;

    }

    private void getAllWidgets(View view) {
        //gridView = (GridView) view.findViewById(R.id.gridViewFragmentOne);
        //allImages = getResources().obtainTypedArray(R.array.all_images);


        btn_compare=(Button) view.findViewById(R.id.btn_compare);

        //txt_chosenPoint=(TextView) view.findViewById(R.id.txt_chosenPoint);
        image=(ImageView) view.findViewById(R.id.image);

        btn_back=(Button) view.findViewById(R.id.btn_back) ;
        gridView = (GridView) view.findViewById(R.id.gridView);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirstFragment.point1_x=null;
                FirstFragment.point1_y=null;
                SecondFragment.point2_x=null;
                SecondFragment.point2_y=null;
                Intent toMain= new Intent(getContext(), MenuActivity.class);
                startActivity(toMain);
            }
        });


        db = new MySQLiteHelper(getContext());

        /*

        ArrayList<Integer> pulse_array_final=new ArrayList<Integer>();

        pulse_array_final.add(1);
        pulse_array_final.add(3);
        pulse_array_final.add(2);
        pulse_array_final.add(4);
        pulse_array_final.add(5);
        pulse_array_final.add(9);

        for(int i=0;i<pulse_array_final.size();i++){
            db.addRecord(new VJRecord(pulse_array_final.get(i), 1));
        }

        */



        list_allSessions = db.getAllRecords();

        int id=list_allSessions.size();
        if(id==0)
            session=0;
        else session=db.getRecord(id).getSession();

        session_names = new ArrayList<String>();
        for(int i=0; i<session; i++)
            session_names.add("session "+(i+1));

        list_thisSession = db.getAllRecordsOfSession(session);

        //list_thisSession=stringsToList(getIntent().getStringArrayExtra("strings_thisSession"));
        //list_allSessions=stringsToList(getIntent().getStringArrayExtra("strings_allSessions"));

        //if(list_thisSession!= null) {
        if (list_thisSession.size() != 0) {
            //adapter = new CustomAdapter(this, list_thisSession);
            //gridView.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
            series_adapter = new AdapterForSeries(getContext(), session_names);
            gridView.setAdapter(series_adapter);
            series_adapter.notifyDataSetChanged();
        }
        //}

        graph=(GraphView) view.findViewById(R.id.graph);
        graph.removeAllSeries();
        series=new LineGraphSeries<DataPoint>(getDataPoint(list_thisSession));
        graph.addSeries(series);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        //graph.getViewport().setMaxX(40);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(20f);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPointInterface) {

                /////////
                String msg="X:"+dataPointInterface.getX()+"\nY: "+dataPointInterface.getY();
                point1_x=(int) dataPointInterface.getX();
                point1_y=(int) dataPointInterface.getY();
                //txt_chosenPoint.setText("Chosen point: x= "+point1_x.toString()+" y= "+point1_y.toString());
                point1_session=session;



                //_---------------------------------------------------------


                /*
                //File imgFile = new File("/sdcard/Images/test_image.jpg");
                File imgFile = new File(Environment.getExternalStorageDirectory().getPath()+"/session"+session);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                image.setImageBitmap(myBitmap);
                */

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                //File file1 = new File("/storage/emulated/0/iconPNG.png");
                //File file2 = new File("/sdcard/iconPNG.png");
                String session_folder_load = "Session"+String.format("%d",session);
                File file1 = new File(Environment.getExternalStorageDirectory().getPath()+
                        "/Pictures/" + session_folder_load + "/" + point1_x + ".jpg");
                Bitmap bitmap = BitmapFactory.decodeFile(file1.getAbsolutePath(), options);
                image.setImageBitmap(bitmap);


                //_-----------------------------------------------------------

                //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                //////////
            }
        });


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //selectedPosition = position;
                //image.setBackground(null);
                image.setImageResource(android.R.color.transparent);
                session=position+1;
                list_chosenSession = db.getAllRecordsOfSession(session);
                series=new LineGraphSeries<DataPoint>(getDataPoint(list_chosenSession));
                graph.removeAllSeries();
                graph.getViewport().setMaxXAxisSize(series.getHighestValueX());
                graph.addSeries(series);
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(20f);
                series.setOnDataPointTapListener(new OnDataPointTapListener() {
                    @Override
                    public void onTap(Series series, DataPointInterface dataPointInterface) {


                        /*
                        series_adapter.notifyDataSetChanged();
                        /////////
                        String msg="X:"+dataPointInterface.getX()+"\nY: "+dataPointInterface.getY();
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        //////////
                     */

                        /////////
                        String msg="X:"+dataPointInterface.getX()+"\nY: "+dataPointInterface.getY();
                        point1_x=(int) dataPointInterface.getX();
                        point1_y=(int) dataPointInterface.getY();
                        //txt_chosenPoint.setText("Chosen point: x= "+point1_x.toString()+" y= "+point1_y.toString());
                        point1_session=session;



                        //_---------------------------------------------------------


                /*
                //File imgFile = new File("/sdcard/Images/test_image.jpg");
                File imgFile = new File(Environment.getExternalStorageDirectory().getPath()+"/session"+session);
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                image.setImageBitmap(myBitmap);
                */

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        //File file1 = new File("/storage/emulated/0/iconPNG.png");
                        //File file2 = new File("/sdcard/iconPNG.png");
                        String session_folder_load = "Session"+String.format("%d",session);
                        File file1 = new File(Environment.getExternalStorageDirectory().getPath()+
                                "/Pictures/" + session_folder_load + "/" + point1_x + ".jpg");
                        Bitmap bitmap = BitmapFactory.decodeFile(file1.getAbsolutePath(), options);
                        image.setImageBitmap(bitmap);


                        //_-----------------------------------------------------------

                        //Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                        /*
                        if(SecondFragment.point2_x!=null){
                            pop1=new PopUp();
                            //Fragment2 fragment2 = new Fragment2();
                            FragmentManager fragmentManager = getFragmentManager();
                            //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            //fragmentTransaction.replace(R.id.container, fragment2);
                            //fragmentTransaction.addToBackStack(null);
                            //fragmentTransaction.commit();
                            //PopUp pop=new PopUp();
                            pop1.show(fragmentManager, "Congrats");
                        }
                        */

                    }
                });
            }
        });

        btn_compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SecondFragment.point2_x!=null && point1_x!=null){
                    pop1=new PopUp();
                    //Fragment2 fragment2 = new Fragment2();
                    FragmentManager fragmentManager = getFragmentManager();
                    //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //fragmentTransaction.replace(R.id.container, fragment2);
                    //fragmentTransaction.addToBackStack(null);
                    //fragmentTransaction.commit();

                    //PopUp pop=new PopUp();
                    pop1.show(fragmentManager, "1st");
                }
            }
        });
    }
    /*
    private void setAdapter()
    {
        for (int i = 0; i < allImages.length(); i++) {
            allDrawableImages.add(allImages.getDrawable(i));
        }
        GridViewAdapter gridViewAdapter = new GridViewAdapter(MainActivity.getInstance(), allDrawableImages);
        gridView.setAdapter(gridViewAdapter);
    }
    */

    private DataPoint[] getDataPoint(ArrayList<VJRecord> list){
        DataPoint[] dp=new DataPoint[list.size()];
        for(int i=0; i<list.size(); i++){
            //dp[i]=new DataPoint(Float.parseFloat(list.get(i).getTime()),list.get(i).getHR());
            dp[i]=new DataPoint(i,list.get(i).getHR());
        }
        return dp;
    }

}