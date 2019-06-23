package org.cadenhead.android;
 
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
 
public class LeaderActivity extends Activity {
    public static final String TAG = "Leader";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.main);
    }

    public void processClicks(View display) {
        Intent action;
        int id = display.getId();
        switch (id) {
            case (R.id.phonebutton):
            	Log.i(TAG, "Making call");
                action = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:202-456-1111"));
                startActivity(action);
                break;
            case (R.id.webbutton):
            	Log.i(TAG, "Loading browser");
                action = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://whitehouse.gov"));
                startActivity(action);
                break;
            case (R.id.mapbutton):
            	Log.i(TAG, "Loading map");
                action = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=White House, Washington, DC"));
                startActivity(action);
                break;
            default:
                break;
        }
    }
}