package l14.cb.com.cricbuzz.activities;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import l14.cb.com.cricbuzz.R;
import l14.cb.com.cricbuzz.asyntasks.LiveMatchTask;
import l14.cb.com.cricbuzz.models.LiveMatchPOJO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";
    TextView tvOngoingMatches, tvUpcomingMatches, tvCalender;
    Boolean matchStarted;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOngoingMatches = (TextView) findViewById(R.id.tvOngoingMatches);
        tvUpcomingMatches = (TextView) findViewById(R.id.tvUpcomingMatches);
        tvCalender = (TextView) findViewById(R.id.tvCalender);

        tvUpcomingMatches.setOnClickListener(this);
        tvOngoingMatches.setOnClickListener(this);
        tvCalender.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.tvOngoingMatches:

                matchStarted = true;

                break;
            case R.id.tvUpcomingMatches:

                matchStarted = false;

                break;

            case R.id.tvCalender:

                tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {


                        int result = tts.setLanguage(Locale.FRENCH);

                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        } else {
                            //btnSpeak.setEnabled(true);
                            tts.speak("elle est petite.", TextToSpeech.QUEUE_FLUSH, null);
                            Log.d(TAG, "onInit: inside ELSE");
                        }
                    }
                });

                /*tts.speak("saksham", TextToSpeech.QUEUE_ADD, null);*/
                break;
        }


        /*Intent i = new Intent(this, LiveMatchActivity.class);
        i.putExtra("matchStarted", matchStarted);
        startActivity(i);*/

    }
}
