package xyz.endtech.swapimages;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView beach;
        ImageView soccer;
        ImageView volley;
        ImageView pokeball;
        ImageView tennis;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        beach = (ImageView) findViewById(R.id.ivBeach);
        soccer = (ImageView) findViewById(R.id.ivSoccer);
        volley = (ImageView) findViewById(R.id.ivVolley);
        pokeball = (ImageView) findViewById(R.id.ivPokeball);
        tennis = (ImageView) findViewById(R.id.ivTennis);

        View.OnLongClickListener listenClick = new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder dragShadow = new View.DragShadowBuilder(v);

                v.startDrag(data, dragShadow, v, 0);

                return false;
            }
        };

        View.OnDragListener listenDrag = new View.OnDragListener() {

            @Override
            public boolean onDrag(View v, DragEvent event) {
                int dragEvent = event.getAction();

                switch (dragEvent) {

                    case DragEvent.ACTION_DROP:
                        ImageView target = (ImageView) v;

                        ImageView dragged = (ImageView) event.getLocalState();

                        Drawable targetDraw = target.getDrawable();
                        Drawable draggedDraw = dragged.getDrawable();

                        dragged.setImageDrawable(targetDraw);
                        target.setImageDrawable(draggedDraw);

                        break;

                }

                return true;
            }
        };
        beach.setOnLongClickListener(listenClick);
        soccer.setOnLongClickListener(listenClick);
        volley.setOnLongClickListener(listenClick);
        pokeball.setOnLongClickListener(listenClick);
        tennis.setOnDragListener(listenDrag);
    }

    public void swap(ImageView imageView, View.OnLongClickListener listenClick) {
        imageView.setOnLongClickListener(listenClick);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
