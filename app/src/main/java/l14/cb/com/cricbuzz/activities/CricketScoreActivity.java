package l14.cb.com.cricbuzz.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import l14.cb.com.cricbuzz.R;
import l14.cb.com.cricbuzz.adapters.CricketScoreAdapter;
import l14.cb.com.cricbuzz.asyntasks.CricketScoreTask;
import l14.cb.com.cricbuzz.models.CricketScorePOJO;

public class CricketScoreActivity extends AppCompatActivity {

    String unique_key;
    RecyclerView rv;
    public static final String TAG = "CricketScoreActivity";
    Boolean matchStarted;
    CricketScoreAdapter adapter;
    ImageView ivAnim;
    ProgressBar pbScore;
    SwipeRefreshLayout str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_score);

        Log.d(TAG, "onCreate: unique_key -> " + unique_key);
        Intent i = getIntent();

        if (i != null) {

            unique_key = i.getStringExtra("unique_id");
            matchStarted = i.getBooleanExtra("matchStarted", false);
            Log.d(TAG, "onCreate: inside IF");

        }

        str = (SwipeRefreshLayout) findViewById(R.id.str);
        str.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                str.setRefreshing(false);
                str.destroyDrawingCache();
                str.clearAnimation();

            }
        });

        ivAnim = (ImageView) findViewById(R.id.ivAnim);
        ivAnim.setBackgroundResource(R.drawable.anim_items);

        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(1000);
        animator.setRemoveDuration(1000);

        ivAnim.setVisibility(View.GONE);
        pbScore = (ProgressBar) findViewById(R.id.pbScore);
        rv = (RecyclerView) findViewById(R.id.rvCricketScore);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(animator);

        StringBuilder URL = new StringBuilder();
        URL.append("http://cricapi.com/api/cricketScore?apikey=JUQuaCHs9yfYxcsT1InOJ1wOC9I3&unique_id=");
        URL.append(unique_key);

        CricketScoreTask cricketScoreTask = new CricketScoreTask(new CricketScoreTask.OnItemView() {
            @Override
            public void setOnItemView(CricketScorePOJO cricketScorePOJO) {

                ivAnim.setVisibility(View.VISIBLE);
                CricketScoreAdapter cricketScoreAdapter = new CricketScoreAdapter(cricketScorePOJO, CricketScoreActivity.this, matchStarted);
                rv.setAdapter(cricketScoreAdapter);

            }
        }, matchStarted, pbScore);

        cricketScoreTask.execute(URL.toString());

        AnimationDrawable ar = (AnimationDrawable) ivAnim.getBackground();
        ar.start();

    }
}
