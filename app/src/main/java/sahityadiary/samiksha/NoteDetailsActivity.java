package sahityadiary.samiksha;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NoteDetailsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;
    private Intent intent;
    private String selectedTopicId;
    private ArrayList<Note> notes;
    private RecyclerView recyclerView;
    private NotesAdapter notesAdapter;
    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    AdRequest adRequest2;
    private String noteUrl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intent = getIntent();
        if (intent.getStringExtra("Topic").equals("BA1")){
            noteUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mGueqTtAEXm-FZrYt4j69uQJGdPLyp6Q5_PRUA0KdzU/values/BA%201ST%20NOTES!A:F?key=AIzaSyAHRy3U0-UXRMxVJ09ubzT66KCmPeKkC98";
        } else if (intent.getStringExtra("Topic").equals("BA2")){
            noteUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mGueqTtAEXm-FZrYt4j69uQJGdPLyp6Q5_PRUA0KdzU/values/BA%202ND%20NOTES!A:F?key=AIzaSyAHRy3U0-UXRMxVJ09ubzT66KCmPeKkC98";
        } else if (intent.getStringExtra("Topic").equals("BA3")){
            noteUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mGueqTtAEXm-FZrYt4j69uQJGdPLyp6Q5_PRUA0KdzU/values/BA%203RD%20NOTES!A:F?key=AIzaSyAHRy3U0-UXRMxVJ09ubzT66KCmPeKkC98";
        } else if (intent.getStringExtra("Topic").equals("MAP")){
            noteUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mGueqTtAEXm-FZrYt4j69uQJGdPLyp6Q5_PRUA0KdzU/values/MA%20PREVIOUS%20NOTES!A:F?key=AIzaSyAHRy3U0-UXRMxVJ09ubzT66KCmPeKkC98";
        } else if (intent.getStringExtra("Topic").equals("MAF")){
            noteUrl = "https://sheets.googleapis.com/v4/spreadsheets/1mGueqTtAEXm-FZrYt4j69uQJGdPLyp6Q5_PRUA0KdzU/values/MA%20FINAL%20NOTES!A:F?key=AIzaSyAHRy3U0-UXRMxVJ09ubzT66KCmPeKkC98";
        }
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        textView = findViewById(R.id.quiz_no_internet);
        progressBar = findViewById(R.id.quiz_progress_bar);
        notes = new ArrayList<>();
        notesAdapter = new NotesAdapter(notes);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(notesAdapter);
        selectedTopicId = intent.getStringExtra("selectedTopicId");
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new NoteAsyncTask().execute(noteUrl);
        } else {
            textView.setText("No internet connection");
            textView.setVisibility(View.VISIBLE);
        }
        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial1));

        adRequest2 = new AdRequest.Builder().build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest2);
    }

    private class NoteAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            URL url;
            HttpURLConnection conn = null;
            InputStream in = null;
            try {
                url = new URL(stringUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");

                conn.connect();
                in = conn.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonResponse) {
            super.onPostExecute(jsonResponse);
            progressBar.setVisibility(View.INVISIBLE);
            if (jsonResponse != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonResponse);
                    JSONArray jsonArray = jsonObject.getJSONArray("values");
                    for (int i = 1; i < jsonArray.length(); i++) {
                        JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                        String topicId = jsonArray1.getString(0);
                        String topicName = jsonArray1.getString(1);
                        String title = jsonArray1.getString(2);
                        String parId = jsonArray1.getString(3);
                        String head = jsonArray1.getString(4);
                        String content = jsonArray1.getString(5);
                        if (topicId.equals(selectedTopicId)) {
                            notes.add(new Note(topicId, topicName, title, parId, head, content));
                        }
                    }
                    setTitle(notes.get(0).getTitle());
                    notesAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                textView.setText("Error");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }

        return (super.onOptionsItemSelected(item));
    }
    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        if (mInterstitialAd.isLoaded()) {
            showInterstitial();
        }
        super.onDestroy();
    }
}
