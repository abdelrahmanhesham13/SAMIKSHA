package sahityadiary.samiksha;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class HomeScreenActivity extends AppCompatActivity {

    GridView mGridView;
    private AdView mAdView;

    String[] values = {

            "BA  Ist",
            "BA IInd",
            "BA IIIrd",
            "MA PREVIOUS",
            "MA FINAL",
            "MORE APPS",
            "RATE US",
            "SHARE",
    };

    int[] images = {
            R.drawable.ba_ist,
            R.drawable.ba_iind,
            R.drawable.ba_iiird,
            R.drawable.ma_previous,
            R.drawable.ma_final,
            R.drawable.moreapps,
            R.drawable.likeus,
            R.drawable.share,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setTitle("Welcome");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        mGridView = findViewById(R.id.grid_view);
        GridAdapter gridAdapter = new GridAdapter(HomeScreenActivity.this, values, images);
        mGridView.setAdapter(gridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(HomeScreenActivity.this, BAIstActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(HomeScreenActivity.this, BAIIndActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(HomeScreenActivity.this, BAIIIrdActivity.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(HomeScreenActivity.this, MAPreviousActivity.class);
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent5 = new Intent(HomeScreenActivity.this, MAFinalActivity.class);
                        startActivity(intent5);
                        break;
                    case 5:
                        String devName = "SAHITYA DIARY";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://store/apps/developer?id=" + devName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/developer?id=" + devName)));
                        }
                        break;
                    case 6:
                        final String appPackageName = getPackageName();
                        try {
                            Log.i("Package Name",appPackageName);
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        break;
                    case 7:
                        ShareCompat.IntentBuilder
                                .from(HomeScreenActivity.this)
                                .setChooserTitle("Share Via")
                                .setType("text/plain")
                                .setText("I FOUND AN AWSUM APP NAME \"SAMIKSHA\"\n" +
                                        "MUST DOWNLOAD\n"+"https://play.google.com/store/apps/details?id=sahityadiary.samiksha")
                                .startChooser();
                        break;
                }
            }
        });

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
        super.onDestroy();
    }

}
