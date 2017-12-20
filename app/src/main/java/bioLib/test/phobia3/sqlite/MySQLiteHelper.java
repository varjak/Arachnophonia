package bioLib.test.phobia3.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import bioLib.test.phobia3.model.VJRecord;

import java.util.ArrayList;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static long num_tables=0; //incluir este valor no nome da foto!
    // Database Version
    private static final int DATABASE_VERSION = 25; //provavelmente vai-s ter que incrementar automaticamente
    // Database Name
    private static final String DATABASE_NAME = "VJDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        num_tables++;
        // SQL statement to create book table
        /*
        String CREATE_VJ_TABLE = "CREATE TABLE vj ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ecg TEXT, "+
                "hr REAL, "+
                "rr REAL, "+
                "author TEXT )";

        String CREATE_VJ_TABLE = "CREATE TABLE vj ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ecg TEXT, "+
                "hr REAL, "+
                "rr REAL )";

        String CREATE_VJ_TABLE = "CREATE TABLE vj ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hr REAL, "+
                "time TEXT )";


        String CREATE_VJ_TABLE = "CREATE TABLE vj"+num_tables+" ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time DATETIME DEFAULT CURRENT_TIMESTAMP, "+
                "hr REAL )";
                */
        String CREATE_VJ_TABLE = "CREATE TABLE vj ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "time DATETIME DEFAULT CURRENT_TIMESTAMP, "+
                "hr INTEGER, "+
                "session INTEGER )";
        // create books table
        db.execSQL(CREATE_VJ_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        //db.execSQL("DROP TABLE IF EXISTS vj"+num_tables);
        db.execSQL("DROP TABLE IF EXISTS vj");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    //private static String TABLE_VJ = "vj"+num_tables;
    private static String TABLE_VJ = "vj";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    //private static final String KEY_ECG = "ecg";
    private static final String KEY_HR="hr";
    //private static final String KEY_RR="rr";
    //private static final String KEY_AUTHOR = "author";
    private static final String KEY_TIME="time";
    private static final String KEY_SESSION="session";

    //private static final String[] COLUMNS = {KEY_ID,KEY_ECG,KEY_AUTHOR};
    //private static final String[] COLUMNS = {KEY_ID,KEY_ECG,KEY_HR, KEY_RR, KEY_AUTHOR};
    private static final String[] COLUMNS = {KEY_ID,KEY_TIME,KEY_HR, KEY_SESSION};

    public void addRecord(VJRecord VJRecord){
        Log.d("addRecord", VJRecord.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(KEY_ECG, VJRecord.getECG()); // get title
        //values.put(KEY_TIME,VJRecord.getTime());
        values.put(KEY_HR,VJRecord.getHR());
        //values.put(KEY_RR,VJRecord.getRR());
        //values.put(KEY_AUTHOR, VJRecord.getAuthor()); // get author
        values.put(KEY_SESSION, VJRecord.getSession());

        // 3. insert
        db.insert(TABLE_VJ, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public VJRecord getRecord(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_VJ, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build VJRecord object
        VJRecord VJRecord = new VJRecord();
        VJRecord.setId(Integer.parseInt(cursor.getString(0)));
        //VJRecord.setECG(cursor.getString(1));
        VJRecord.setTime(cursor.getString(1));
        VJRecord.setHR(Integer.parseInt(cursor.getString(2)));
        //VJRecord.setRR(Float.parseFloat(cursor.getString(3)));
        //VJRecord.setAuthor(cursor.getString(2));
        //VJRecord.setAuthor(cursor.getString(4));
        VJRecord.setSession(Integer.parseInt(cursor.getString(3)));

        Log.d("getRecord("+id+")", VJRecord.toString());

        // 5. return VJRecord
        return VJRecord;
    }

    public VJRecord getRecordOfSession(int session, int num){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_VJ, // a. table
                        COLUMNS, // b. column names
                        " session = ?", // c. selections
                        new String[] { String.valueOf(session) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        "id", // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build VJRecord object
        VJRecord VJRecord = new VJRecord();
        VJRecord.setId(Integer.parseInt(cursor.getString(0)));
        //VJRecord.setECG(cursor.getString(1));
        VJRecord.setTime(cursor.getString(1));
        VJRecord.setHR(Integer.parseInt(cursor.getString(2)));
        //VJRecord.setRR(Float.parseFloat(cursor.getString(3)));
        //VJRecord.setAuthor(cursor.getString(2));
        //VJRecord.setAuthor(cursor.getString(4));
        VJRecord.setSession(Integer.parseInt(cursor.getString(3)));

        Log.d("getRecord("+num+"of session"+session+")", VJRecord.toString());

        // 5. return VJRecord
        return VJRecord;
    }

    /*
    // Get All Books
    public List<VJRecord> getAllRecords() {
        List<VJRecord> VJRecords = new LinkedList<VJRecord>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_VJ;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build VJRecord and add it to list
        VJRecord VJRecord = null;
        if (cursor.moveToFirst()) {
            do {
                VJRecord = new VJRecord();
                VJRecord.setId(Integer.parseInt(cursor.getString(0)));
                VJRecord.setECG(cursor.getString(1));

                VJRecord.setHR(Float.parseFloat(cursor.getString(2)));
                VJRecord.setRR(Float.parseFloat(cursor.getString(3)));

                //VJRecord.setAuthor(cursor.getString(2));
                VJRecord.setAuthor(cursor.getString(4));

                // Add VJRecord to VJRecords
                VJRecords.add(VJRecord);
            } while (cursor.moveToNext());
        }

        Log.d("getAllRecords()", VJRecords.toString());

        // return VJRecords
        return VJRecords;
    }

*/

    // Get All Books
    public ArrayList<VJRecord> getAllRecords() {
        ArrayList<VJRecord> VJRecords = new ArrayList<VJRecord>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_VJ;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build VJRecord and add it to list
        VJRecord VJRecord = null;
        if (cursor.moveToFirst()) {
            do {
                VJRecord = new VJRecord();
                VJRecord.setId(Integer.parseInt(cursor.getString(0)));
                //VJRecord.setECG(cursor.getString(1));
                VJRecord.setTime(cursor.getString(1));
                VJRecord.setHR(Integer.parseInt(cursor.getString(2)));
                //VJRecord.setRR(Float.parseFloat(cursor.getString(3)));
                //VJRecord.setAuthor(cursor.getString(2));
                //VJRecord.setAuthor(cursor.getString(4));
                VJRecord.setSession(Integer.parseInt(cursor.getString(3)));

                // Add VJRecord to VJRecords
                VJRecords.add(VJRecord);
            } while (cursor.moveToNext());
        }

        Log.d("getAllRecords()", VJRecords.toString());

        // return VJRecords
        return VJRecords;
    }

    public ArrayList<VJRecord> getAllRecordsOfSession(int session) {
        ArrayList<VJRecord> VJRecords = new ArrayList<VJRecord>();

        SQLiteDatabase db = this.getReadableDatabase();
        // 1. build the query
        Cursor cursor =
                db.query(TABLE_VJ, // a. table
                        COLUMNS, // b. column names
                        " session = ?", // c. selections
                        new String[] { String.valueOf(session) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        "id", // g. order by
                        null); // h. limit

        // 3. go over each row, build VJRecord and add it to list
        VJRecord VJRecord = null;
        if (cursor.moveToFirst()) {
            do {
                VJRecord = new VJRecord();
                VJRecord.setId(Integer.parseInt(cursor.getString(0)));
                //VJRecord.setECG(cursor.getString(1));
                VJRecord.setTime(cursor.getString(1));
                VJRecord.setHR(Integer.parseInt(cursor.getString(2)));
                //VJRecord.setRR(Float.parseFloat(cursor.getString(3)));
                //VJRecord.setAuthor(cursor.getString(2));
                //VJRecord.setAuthor(cursor.getString(4));
                VJRecord.setSession(Integer.parseInt(cursor.getString(3)));

                // Add VJRecord to VJRecords
                VJRecords.add(VJRecord);
            } while (cursor.moveToNext());
        }

        //Log.d("getAllRecordsOfSession()", VJRecords.toString());

        // return VJRecords
        return VJRecords;
    }
    // Updating single VJRecord
    public int updateRecord(VJRecord VJRecord) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put("ecg", VJRecord.getECG()); // get title
        //values.put("time", VJRecord.getTime());
        values.put("hr",VJRecord.getHR());
        //values.put("rr",VJRecord.getRR());
        //values.put("author", VJRecord.getAuthor()); // get author
        values.put("session",VJRecord.getSession());

        // 3. updating row
        int i = db.update(TABLE_VJ, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(VJRecord.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single VJRecord
    public void deleteRecord(VJRecord VJRecord) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_VJ,
                KEY_ID+" = ?",
                new String[] { String.valueOf(VJRecord.getId()) });

        // 3. close
        db.close();

        Log.d("deleteRecord", VJRecord.toString());

    }


}