package xyz.endtech.dragimages;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    ImageView imageView;
    float x,y =0.0f;
    boolean moving= false;

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
        imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnTouchListener(this);
    }
    public boolean onTouch(View arg0, MotionEvent arg1){
        switch (arg1.getAction()){
            case MotionEvent.ACTION_DOWN:
                moving = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (moving){
                    x = arg1.getRawX()-imageView.getWidth()/2;
                    y = arg1.getRawY()-imageView.getHeight()*3/2;
                    imageView.setX(x);
                    imageView.setY(y);
                }
                break;
            case MotionEvent.ACTION_UP:
                moving = false;
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
