package claudiu.exchangerates;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by claudiu.haidu on 7/16/2015.
 */
public class ExchangeDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EXCHANGERATES.db";
    private static final int DATABASE_VERSION = 3;
    private static final String CREATE_QUERY = "CREATE TABLE " + ExchangeContract.TABLE_NAME +
            " (" + ExchangeContract.EXCHANGE_NAME + " TEXT, "
            + ExchangeContract.EXCHANGE_VALUE + " TEXT);";

    public ExchangeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("Database opened", "database created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_QUERY);
        Log.e("Database", "Table created!");
    }

    public void insertInfo(String exchangeName, String exchangeValue, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ExchangeContract.EXCHANGE_NAME, exchangeName);
        contentValues.put(ExchangeContract.EXCHANGE_VALUE, exchangeValue);
        db.insert(ExchangeContract.TABLE_NAME, null, contentValues);
        Log.i("Database operation", "one row inserted");
    }

    public Cursor selectInfo(SQLiteDatabase db) {
        Cursor cursor;
        String[] projections = {ExchangeContract.EXCHANGE_NAME,
                ExchangeContract.EXCHANGE_VALUE
        };
        cursor = db.query(ExchangeContract.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }
    public void deleteTable(){
        String query = "DELETE FROM "+ ExchangeContract.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + ExchangeContract.TABLE_NAME);
        onCreate(db);
    }

}
