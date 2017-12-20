package bioLib.test.phobia3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import bioLib.test.phobia3.fragments.FirstFragment;
import bioLib.test.phobia3.fragments.SecondFragment;
import bioLib.test.phobia3.model.VJRecord;
import com.jjoe64.graphview.series.DataPoint;
import java.util.ArrayList;
import android.support.design.widget.TabLayout;
import android.widget.FrameLayout;


public class GraphActivity extends AppCompatActivity {

    FrameLayout simpleFrameLayout;
    TabLayout tabLayout;
    FragmentManager fm;
    FragmentTransaction ft;

    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Window w = this.getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
                */

        Window w = this.getWindow(); // in Activity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

        setContentView(R.layout.activity_graph);

// get the reference of FrameLayout and TabLayout
        simpleFrameLayout = (FrameLayout) findViewById(R.id.simpleFrameLayout);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
// Create a new Tab named "First"
        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText("First"); // set the Text for the first Tab
        //firstTab.setIcon(R.drawable.ic_launcher); // set an icon for the
// first tab
        tabLayout.addTab(firstTab); // add  the tab at in the TabLayout
// Create a new Tab named "Second"
        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText("Second"); // set the Text for the second Tab
        //secondTab.setIcon(R.drawable.ic_launcher); // set an icon for the second tab
        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout

        //fragment = null;
        fragment=new FirstFragment();
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();


// perform setOnTabSelectedListener event on TabLayout
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
// get the current selected tab's position and replace the fragment accordingly
                //Fragment fragment = null;
                fragment = null;
                switch (tab.getPosition()) {
                    case 0:
                        fragment = new FirstFragment();
                        break;
                    case 1:
                        fragment = new SecondFragment();
                        break;
                    case 2:
                        //fragment = new ThirdFragment();
                        break;
                }
                //FragmentManager fm = getSupportFragmentManager();
                fm = getFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private DataPoint[] getDataPoint(ArrayList<VJRecord> list){
        DataPoint[] dp=new DataPoint[list.size()];
        for(int i=0; i<list.size(); i++){
            //dp[i]=new DataPoint(Float.parseFloat(list.get(i).getTime()),list.get(i).getHR());
            dp[i]=new DataPoint(i,list.get(i).getHR());
        }
        return dp;
    }
}

