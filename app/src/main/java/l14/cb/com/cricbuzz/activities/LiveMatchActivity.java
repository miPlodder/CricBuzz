package l14.cb.com.cricbuzz.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;

import l14.cb.com.cricbuzz.R;
import l14.cb.com.cricbuzz.adapters.LiveMatchAdapter;
import l14.cb.com.cricbuzz.asyntasks.LiveMatchTask;
import l14.cb.com.cricbuzz.models.LiveMatchPOJO;
import l14.cb.com.cricbuzz.sensors.MySensorManager;

public class LiveMatchActivity extends AppCompatActivity {

    public static final String TAG = "LiveMatchActivity";
    RecyclerView rvLiveMatch;
    final private static String URL = "http://cricapi.com/api/matches?apikey=JUQuaCHs9yfYxcsT1InOJ1wOC9I3";
    Boolean matchStarted;
    LiveMatchAdapter adapter;
    ProgressBar pbLiveMatch;
    SwipeRefreshLayout str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_match);

        Intent i = getIntent();
        if (i != null) {

            matchStarted = i.getBooleanExtra("matchStarted", false);

        }

        MySensorManager.accelSensorActivate(this, new MySensorManager.OnEventListener() {
            @Override
            public void setOnEventListener() {

                Log.d(TAG, "FROM LIVE MATCH");
                finish();

            }
        });


        str = (SwipeRefreshLayout) findViewById(R.id.str);
        str.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                str.setRefreshing(false);
                str.destroyDrawingCache();
                str.clearAnimation();

            }
        });
        pbLiveMatch = (ProgressBar) findViewById(R.id.pbLiveMatch);
        rvLiveMatch = (RecyclerView) findViewById(R.id.rvLiveMatch);
        rvLiveMatch.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LiveMatchAdapter(new ArrayList<LiveMatchPOJO>(), this);
        rvLiveMatch.setAdapter(adapter);

        /*
        This type of animation will only work when item is add or removed in recycler vier
        Not when the recycler view is made for the first time

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);
        rvLiveMatch.setItemAnimator(animator);
*/
        LiveMatchTask liveMatchTask = new LiveMatchTask(new LiveMatchTask.OnDownloadListener() {
            @Override
            public void setOnDownloadListener(ArrayList<LiveMatchPOJO> liveMatches) {

                adapter.updateData(liveMatches);

            }
        }, matchStarted, pbLiveMatch);
        Log.d(TAG, "onCreate: " + matchStarted);

        liveMatchTask.execute(URL);


     /*   new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: of handlerACTIVITY");
            }
        },10000);*/
    }
}
