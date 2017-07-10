package l14.cb.com.cricbuzz.asyntasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

import l14.cb.com.cricbuzz.models.CricketScorePOJO;

/**
 * Created by ip510 feih on 08-07-2017.
 */

public class CricketScoreTask extends AsyncTask<String,Void,CricketScorePOJO> {

    OnItemView oiv;
    public static final String TAG = "CricketScoreTask";
    Boolean matchStarted;
    ProgressBar pbScore;

    public interface OnItemView{
        void setOnItemView(CricketScorePOJO cricketScorePOJO);
    }

    public CricketScoreTask(OnItemView oiv, Boolean matchStarted, ProgressBar pbScore) {

        this.oiv = oiv;
        this.matchStarted = matchStarted;
        this.pbScore = pbScore;
    }

    @Override
    protected CricketScorePOJO doInBackground(String... strings) {

        CricketScorePOJO cricketScorePOJO = null;

        URL url = null;
        try {
            url = new URL(strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d(TAG, "doInBackground1: "+e.getMessage());
        }

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String buffer = "";

            while (buffer != null){

                sb.append(buffer);
                buffer = br.readLine();

            }

            JSONObject jsonObject = new JSONObject(sb.toString());

            String score = "";

            if(matchStarted) {
                try {
                    score = jsonObject.getString("score");
                }catch (Exception e){

                    score = "";
                    Log.d(TAG, "INSIDE EXCEPTION");
                }
            }

            cricketScorePOJO = new CricketScorePOJO(
                    jsonObject.getBoolean("matchStarted"),
                    jsonObject.getString("team-1"),
                    jsonObject.getString("team-2"),
                    jsonObject.getString("type"),
                    score,
                    jsonObject.getString("innings-requirement")
            );

 //Log.d(TAG, "doInBackground: " + jsonObject.getString("types"));

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "doInBackground2: "+e.getMessage());
        }

        return cricketScorePOJO;
    }

    @Override
    protected void onPostExecute(CricketScorePOJO cricketScorePOJO) {

        oiv.setOnItemView(cricketScorePOJO);
        pbScore.setVisibility(View.GONE);
        super.onPostExecute(cricketScorePOJO);

    }
}
