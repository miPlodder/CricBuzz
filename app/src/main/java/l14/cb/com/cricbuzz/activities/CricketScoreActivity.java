package l14.cb.com.cricbuzz.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_score);

        Log.d(TAG, "onCreate: unique_key -> " + unique_key);
        Intent i = getIntent();
        if (i != null) {

            unique_key = i.getStringExtra("unique_id");
            matchStarted = i.getBooleanExtra("matchStarted",false);
            Log.d(TAG, "onCreate: inside IF");

        }

        rv = (RecyclerView) findViewById(R.id.rvCricketScore);
        rv.setLayoutManager(new LinearLayoutManager(this));

        //adapter = new CricketScoreAdapter(new CricketScorePOJO(true,"","","","",""),this,matchStarted);

        StringBuilder URL = new StringBuilder();
        URL.append("http://cricapi.com/api/cricketScore?apikey=JUQuaCHs9yfYxcsT1InOJ1wOC9I3&unique_id=");
        URL.append(unique_key);

        CricketScoreTask cricketScoreTask = new CricketScoreTask(new CricketScoreTask.OnItemView() {
            @Override
            public void setOnItemView(CricketScorePOJO cricketScorePOJO) {

                CricketScoreAdapter cricketScoreAdapter = new CricketScoreAdapter(cricketScorePOJO,CricketScoreActivity.this,matchStarted);
                rv.setAdapter(cricketScoreAdapter);

            }
        },matchStarted);

      cricketScoreTask.execute(URL.toString());

    }
}
