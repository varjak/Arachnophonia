package bioLib.test.phobia3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


// SDK v1.0.07 @MAR15
public class MenuActivity extends AppCompatActivity
{

    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private String difficulty_level=null;

	private final int menu_id = 1;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/////

		//////

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();


		if(!mPreferences.contains("difficulty_level")) {

			//Indicate that the default shared prefs have been set

			//Set some default shared pref
			mEditor.putString("difficulty_level", "normal");
			mEditor.commit();
		}

		difficulty_level = mPreferences.getString("difficulty_level", null);


		int session=1;
		int id=1;
		String path = Environment.getExternalStorageDirectory().getPath()+"/"+String.format("%d",session)+"/"+String.format("%d",id);

		Button button_1 = (Button) findViewById(R.id.button1);

		button_1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent_vjActivity = new Intent(getApplicationContext(), vjActivity.class);
				intent_vjActivity.putExtra("calling-activity", menu_id);
				if (intent_vjActivity != null) {
					startActivity(intent_vjActivity);
				}
			}


		});

		Button button_2 = (Button) findViewById(R.id.button);
		button_2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent intent_GraphActivity = new Intent(getApplicationContext(), GraphActivity.class);
				if (intent_GraphActivity != null) {
					startActivity(intent_GraphActivity);
				}
			}


		});

	}



    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){



        //save stuff
        super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //restore stuff
    }


	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu){
		// use a MenuInflater object to convert, or inflate, the XML for the menu items into Java objects and store them in the menu parameter
		//getMenuInflater().inflate(R.menu.menu_layout,menu);
		//return true; // must return true to display the menu
        // Inflate your main_menu into the menu
        getMenuInflater().inflate(R.menu.menu_layout, menu);

        // Find the menuItem to add your SubMenu
        MenuItem myMenuItem = menu.findItem(R.id.difficulty);

        // Inflating the sub_menu menu this way, will add its menu items
        // to the empty SubMenu you created in the xml
        getMenuInflater().inflate(R.menu.difficulty_item, myMenuItem.getSubMenu());
        return true;
    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
	    switch(item.getItemId()){ // use a selection structure to determine which menu item was selected....
			/*
            case R.id.difficulty:
				Toast.makeText(this, "Choose the difficulty level!", Toast.LENGTH_SHORT).show();

				// navegate between activities using this menu item
				//Intent intent_chooseCurrencies = new Intent(getApplicationContext(), CurrencyPool.class); // create a Intent object for the activity
				//startActivity(intent_chooseCurrencies);

                switch (item.getItemId()){
                    case R.id.submenu_one:
                        Toast.makeText(this, "Difficulty set to Normal!", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.submenu_two:
                        Toast.makeText(this, "Difficulty set to Hard!", Toast.LENGTH_SHORT).show();
                        return true;
                }

				return true; // indicates it has consumed the event and stops further processing. Return false to allow more processing
*/
                case R.id.submenu_normal:
                    Toast.makeText(this, "Difficulty set to Brave!", Toast.LENGTH_SHORT).show();
                    mEditor.putString("difficulty_level","NORMAL");
                    mEditor.commit();
                    return true;
                case R.id.submenu_hard:
                    Toast.makeText(this, "Difficulty set to Heroic!", Toast.LENGTH_SHORT).show();
                    mEditor.putString("difficulty_level","HARD");
                    mEditor.commit();
                    return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}