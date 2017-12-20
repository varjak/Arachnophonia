package bioLib.test.phobia3.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bioLib.test.phobia3.R;

public class PopUp extends DialogFragment {

    Integer previous_session=null;
    Integer next_session=null;
    Integer previous_hr=null;
    Integer next_hr=null;

    /*
    TextView txt_better;
    TextView txt_worse;
    */
    TextView txt_result;
    ImageView image;
    View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 2. Chain together various setter methods to set the dialog characteristics

        LayoutInflater inflater = getActivity().getLayoutInflater(); //for teh custom message

        /*
        builder.setMessage(R.string.resultMessage_dialog);
        builder.setTitle(R.string.titleMessage_dialog);
        */
        builder.setPositiveButton(R.string.positiveButton_dialog, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        if(FirstFragment.point1_session>SecondFragment.point2_session){
            next_session=FirstFragment.point1_session;
            next_hr=FirstFragment.point1_y;
            previous_session=SecondFragment.point2_session;
            previous_hr=SecondFragment.point2_y;
        }
        else if (FirstFragment.point1_session<SecondFragment.point2_session) {
            next_session = SecondFragment.point2_session;
            next_hr = SecondFragment.point2_y;
            previous_session = FirstFragment.point1_session;
            previous_hr = FirstFragment.point1_y;
        }
        else{
            if( FirstFragment.point1_x> SecondFragment.point2_x){
                next_hr=FirstFragment.point1_y;
                previous_hr=SecondFragment.point2_y;
            }
            else{
                next_hr=SecondFragment.point2_y;
                previous_hr=FirstFragment.point1_y;
            }


        }


        /*
        view=inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(view);
        if(previous_hr>next_hr){
            //txt_result=(TextView) getView().findViewById(R.id.txt_result);
            //image=(ImageView) getView().findViewById(R.id.image) ;
            txt_result=(TextView) view.findViewById(R.id.txt_result);
            //image=(ImageView) getView().findViewById(R.id.image) ;
            txt_result.setText("You became fearless!");
        }
        else{
            //File imgFile = new File("/sdcard/Images/test_image.jpg");
            txt_result=(TextView) view.findViewById(R.id.txt_result);
            image=(ImageView) view.findViewById(R.id.image) ;
            File imgFile = new File("/drawable-xhdpi/red_spider.png");
            txt_result.setText("The spiders got you, sorry!");
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
            image.setScaleType(CENTER);
        }
        */


        //builder.setMessage("You must choose values from different sessions!");

        if (previous_hr > next_hr) {
            view=inflater.inflate(R.layout.custom_dialog_better2, null);
            TextView txt_before=(TextView) view.findViewById(R.id.txt_before);
            txt_before.setText(previous_hr.toString());
            TextView txt_after=(TextView) view.findViewById(R.id.txt_after);
            txt_after.setText(next_hr.toString());
            builder.setView(view);


        } else{
            //builder.setView(inflater.inflate(R.layout.custom_dialog_worse, null));
            view=inflater.inflate(R.layout.custom_dialog_worse2, null);
            TextView txt_before=(TextView) view.findViewById(R.id.txt_before2);
            txt_before.setText(previous_hr.toString());
            TextView txt_after=(TextView) view.findViewById(R.id.txt_after2);
            txt_after.setText(next_hr.toString());
            builder.setView(view);

        }




        /*
        builder.setMessage(R.string.resultDialog)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
                */
        // Create the AlertDialog object and return it
        // 3. Get the AlertDialog from create()
        return builder.create();
    }
}