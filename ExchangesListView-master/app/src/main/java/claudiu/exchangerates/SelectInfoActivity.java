package claudiu.exchangerates;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

/**
 * Created by claudiu.haidu on 7/24/2015.
 */
public class SelectInfoActivity extends AppCompatActivity {
    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    ExchangeDbHelper exchangeDBHelper;
    Cursor cursor;
    ListDataAdapter listDataAdapter;

    protected void onCreate(Bundle stateIntance) {
        super.onCreate(stateIntance);
        setContentView(R.layout.select_info_activity);


        listView = (ListView) findViewById(R.id.lvListView);
        listDataAdapter = new ListDataAdapter(getApplicationContext(), R.layout.rowlayout);
        listView.setAdapter(listDataAdapter);

        exchangeDBHelper = new ExchangeDbHelper(getApplicationContext());
        sqLiteDatabase = exchangeDBHelper.getReadableDatabase();

        cursor = exchangeDBHelper.selectInfo(sqLiteDatabase);

        if (cursor.moveToFirst()) {

            do {
                String exchangeName, exchangeValue;
                exchangeName = cursor.getString(0);
                exchangeValue = cursor.getString(1);
                DataProvider dataProvider = new DataProvider(exchangeName, exchangeValue);
                listDataAdapter.add(dataProvider);
            }
            while (cursor.moveToNext());
        }
        //registerClickCallback();
        listDataAdapter.notifyDataSetChanged();
        cursor.close();
    }
}
