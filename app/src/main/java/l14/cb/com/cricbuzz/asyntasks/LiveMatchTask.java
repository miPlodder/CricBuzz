package l14.cb.com.cricbuzz.asyntasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import l14.cb.com.cricbuzz.models.LiveMatchPOJO;

/**
 * Created by ip510 feih on 08-07-2017.
 */

public class LiveMatchTask extends AsyncTask<String, Void, ArrayList<LiveMatchPOJO>> {

    public static final String TAG = "LiveMatchTask";
    private OnDownloadListener odl;
    private Boolean matchStarted;

    public interface OnDownloadListener {
        void setOnDownloadListener(ArrayList<LiveMatchPOJO> liveMatches);
    }

    public LiveMatchTask(OnDownloadListener odl, Boolean matchStarted) {

        this.odl = odl;
        this.matchStarted = matchStarted;
    }

    @Override
    protected ArrayList<LiveMatchPOJO> doInBackground(String... strings) {

        ArrayList<LiveMatchPOJO> liveMatches = new ArrayList<>();
        URL url = null;

        try {
            url = new URL(strings[0]);
            Log.d(TAG, "doInBackground: "+strings[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            BufferedReader br = new BufferedReader(isr);
            String buffer = "";
            StringBuilder sb = new StringBuilder();
            while (buffer != null) {

                sb.append(buffer);
                buffer = br.readLine();

            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonArray = jsonObject.getJSONArray("matches");

            int counter = 0;
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject currItem = jsonArray.getJSONObject(i);

                if (matchStarted == currItem.getBoolean("matchStarted")) {

                    counter++;
                    Log.d(TAG, "IF "+counter+", size = "+jsonArray.length());
                    liveMatches.add(new LiveMatchPOJO(

                            new LiveMatchPOJO.MatchDetail(
                                    currItem.getString("unique_id"),
                                    currItem.getString("team-1"),
                                    currItem.getString("team-2"),
                                    currItem.getBoolean("matchStarted"))));

                } else {

                    //do nothing
                    counter++;
                    Log.d(TAG, "ELSE "+counter+", size = "+jsonArray.length());
                }

            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "doInBackground: "+liveMatches);
        return liveMatches;
    }

    @Override
    protected void onPostExecute(ArrayList<LiveMatchPOJO> liveMatchPOJOs) {

        odl.setOnDownloadListener(liveMatchPOJOs);
        Log.d(TAG, "matchStarted -> " + liveMatchPOJOs);
        super.onPostExecute(liveMatchPOJOs);
    }
}
