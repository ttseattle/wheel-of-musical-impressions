package com.tishat.musicapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView songInput;
    Button addSongs;
    ListView songsListView;
    ArrayList<String> songsList = new ArrayList<String>();
    //ArrayAdapter<String> songAdapter;
    CustomAdapter songAdapter;

    TextView artistInput;
    Button addArtists;
    ListView artistsListView;
    ArrayList<String> artistsList = new ArrayList<String>();
    //ArrayAdapter artistAdapter;
    CustomAdapter artistAdapter;
    
    ImageButton spin;
    TextView selectedSong;
    TextView selectedArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SONGS
        //Initialize variables
        songInput = (TextView) findViewById(R.id.editSongs);
        addSongs = (Button) findViewById(R.id.addSongsButton);
        addSongs.setOnClickListener(this);

        //Prefill song bank with some sample songs
        songsList.add("\"Heads, Shoulders, Knees, and Toes\"");
        songsList.add("\"Sesame Street Theme\"");
        songsList.add("\"Old McDonald Had a Farm\"");

        //Initialize more variables
        songsListView = (ListView) findViewById(R.id.songs);
        songsListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        songAdapter = new CustomAdapter(songsList, this);
        songsListView.setAdapter(songAdapter);
        //setListViewHeightBasedOnChildren(songsListView);
        /*songsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> aV, View v, int position, long id) {
                Log.d("Clicked on position:", position + "");
                songsList.remove(position);
                songAdapter.notifyDataSetChanged();
            }
        });*/

        //ARTISTS
        //Initialize variables
        artistInput = (TextView) findViewById(R.id.editArtists);
        addArtists = (Button) findViewById(R.id.addArtistsButton);
        addArtists.setOnClickListener(this);

        //Prefill artist bank with some sample artists
        artistsList.add("Bob Dylan");
        artistsList.add("Michael Jackson");
        artistsList.add("Janis Joplin");

        //Initialize more variables
        artistsListView = (ListView) findViewById(R.id.artists);
        artistsListView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        artistAdapter = new CustomAdapter(artistsList, this);
        artistsListView.setAdapter(artistAdapter);
        //setListViewHeightBasedOnChildren(artistsListView);
        //artistsListView.setOnItemClickListener(this);
        
        spin = (ImageButton) findViewById(R.id.spinButton);
        spin.setOnClickListener(this);
        selectedSong = (TextView) findViewById(R.id.selectedSong);
        selectedArtist = (TextView) findViewById(R.id.selectedArtist);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.addSongsButton:
                songsList.add("\"" + songInput.getText().toString() + "\"");
                songAdapter.notifyDataSetChanged();
                songsListView.setSelection(songsList.size() - 1);
                //setListViewHeightBasedOnChildren(songsListView);
                break;
            case R.id.addArtistsButton:
                artistsList.add(artistInput.getText().toString());
                artistAdapter.notifyDataSetChanged();
                artistsListView.setSelection(artistsList.size() - 1);
                //setListViewHeightBasedOnChildren(artistsListView);
                break;
            case R.id.spinButton:
                spin();
                break;
        }
    }

    private void spin() {
        if (songsList.size() < 1) {
            Toast.makeText(getApplicationContext(), "Your song bank is empty!", Toast.LENGTH_LONG).show();
        } else if (artistsList.size() < 1) {
            Toast.makeText(getApplicationContext(), "Your artist bank is empty!", Toast.LENGTH_LONG).show();
        } else {
            Random r = new Random();
            int songIndex = r.nextInt(songsList.size());
            int artistIndex = r.nextInt(artistsList.size());
            String song = songsList.get(songIndex);
            String artist = artistsList.get(artistIndex);
            selectedSong.setText(song);
            selectedArtist.setText(artist);
        }
    }

    /**** Method for Setting the Height of the ListView dynamically.
     **** Hack to fix the issue of not showing all the items of the ListView
     **** when placed inside a ScrollView  ****/
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    //For GIFs
    public class GIFView extends View{
        private Movie movie;
        private InputStream is;
        private long moviestart;
        public GIFView(Context context) {
            super(context);
            is=getResources().openRawResource(R.drawable.anim_cerca);
            movie=Movie.decodeStream(is);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            long now=android.os.SystemClock.uptimeMillis();

            if (moviestart == 0)
                moviestart = now;

            int relTime = (int)((now - moviestart) % movie.duration());
            movie.setTime(relTime);
            movie.draw(canvas,10,10);
            this.invalidate();
        }

    }

}
