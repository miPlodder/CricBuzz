package l14.cb.com.cricbuzz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import l14.cb.com.cricbuzz.R;
import l14.cb.com.cricbuzz.adapters.LiveMatchAdapter;
import l14.cb.com.cricbuzz.asyntasks.LiveMatchTask;
import l14.cb.com.cricbuzz.models.LiveMatchPOJO;

public class LiveMatchActivity extends AppCompatActivity {

    public static final String TAG = "LiveMatchActivity" ;
    RecyclerView rvLiveMatch;
    final private static String URL = "http://cricapi.com/api/matches?apikey=JUQuaCHs9yfYxcsT1InOJ1wOC9I3";
    Boolean matchStarted ;
    LiveMatchAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_match);

        Intent i = getIntent();
        if(i != null){

            matchStarted = i.getBooleanExtra("matchStarted",false);

        }

        rvLiveMatch = (RecyclerView) findViewById(R.id.rvLiveMatch);
        rvLiveMatch.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LiveMatchAdapter(new ArrayList<LiveMatchPOJO>(),this);
        rvLiveMatch.setAdapter(adapter);

        LiveMatchTask liveMatchTask = new LiveMatchTask(new LiveMatchTask.OnDownloadListener() {
            @Override
            public void setOnDownloadListener(ArrayList<LiveMatchPOJO> liveMatches) {

                adapter.updateData(liveMatches);

            }
        },matchStarted);
        Log.d(TAG, "onCreate: "+ matchStarted);

        liveMatchTask.execute(URL);

    }
}
