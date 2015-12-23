package claudiu.exchangerates;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    //TextView output;
    ProgressBar pb;
    List<MyTask> tasks;
    ListView list;
    List<String> finalList = new ArrayList<>();
    ExchangeDbHelper exchangeDbHelper;
    SQLiteDatabase sqLiteDatabase;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        list = (ListView) findViewById(R.id.listView);
        tasks = new ArrayList<>();
    }

    public void populateListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item, finalList);
        list.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if (isOnline() && finalList.size() < 1) {
                requestData("https://openexchangerates.org/api/latest.json?app_id=8d96eb1163334b3aa27b37617ec253a6");
            } else if (finalList.size() > 0) {
                Toast.makeText(this, "List Already Loaded", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Network isn't Available", Toast.LENGTH_LONG).show();
            }
        }
        if (id == R.id.action_db) {
            Intent intent = new Intent();
            intent.setClass(this, SelectInfoActivity.class);
            startActivity(intent);
        }

        return false;
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        // task.execute("Param 1","Param 2","Param 3");
        task.execute(uri);
    }

    protected void updateDisplay(String message) {
        exchangeDbHelper = new ExchangeDbHelper(context);
        exchangeDbHelper.deleteTable();
        String strAppend = "";
        InputStream is = new ByteArrayInputStream(message.getBytes());

        BufferedReader in = new BufferedReader(new InputStreamReader(is));

        String str;

        try {
            while ((str = in.readLine()) != null) {
                // str is one line of text; readLine() strips the newline character(s)

                Pattern p = Pattern.compile("\"([^\"]*)\"");
                Matcher m = p.matcher(str);
                while (m.find()) {
                    Pattern no = Pattern.compile(":([^\"]*),");
                    Matcher ma = no.matcher(str);
                    while (ma.find()) {
                        if (!(m.group(1).trim()).equals("timestamp")) {
                            str = (m.group(1) + " " + ma.group(1));
                            insertToDB(m.group(1), ma.group(1), exchangeDbHelper);
                            strAppend = strAppend + str + "\r\n";
                            finalList.add(str);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Error" + e, Toast.LENGTH_LONG).show();
        }
    }

    private void insertToDB(String exchangeName, String exchangeValue, ExchangeDbHelper exchangeDbHelper) {

            sqLiteDatabase = exchangeDbHelper.getWritableDatabase();
            exchangeDbHelper.insertInfo(exchangeName, exchangeValue, sqLiteDatabase);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            Toast.makeText(MainActivity.this, "Starting task!", Toast.LENGTH_SHORT).show();
            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected String doInBackground(String... params) {

            String content = null;
            try {
                content = OkHttpManager.run(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onPostExecute(String result) {

            updateDisplay(result);
            populateListView();

            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }
            Toast.makeText(MainActivity.this, "Finished task!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(MainActivity.this, "Working" + values[0], Toast.LENGTH_LONG).show();
        }
    }
}
