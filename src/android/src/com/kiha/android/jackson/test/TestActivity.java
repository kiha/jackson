package com.kiha.android.jackson.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mTextView = new TextView(this);
        mTextView.setText("Use the menu button to run a test. This is not an exhaustive test suite; just a sanity check of the features we use. If you want one, run the official Jackson tests.");
        setContentView(mTextView);
    }
    
    TextView mTextView;
    
    private MenuItem mTestMenu;
    
    static {
        try {
            mTestUrl = new URL("http://api.twitter.com/1/statuses/public_timeline.json");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    
    private static URL mTestUrl;
    
    private InputStream openTestUrlStream() {
        try {
            return mTestUrl.openStream();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mTestMenu = menu.add("Test");
        
        mTestMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                try {
                    JsonFactory factory = new JsonFactory();
                    JsonParser parser = factory.createJsonParser(openTestUrlStream());
                    // read in the array token
                    parser.nextToken();
                    parser.nextToken();
                    // read the first status
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode node = null;
                    StringBuilder builder = new StringBuilder();
                    while (null != (node = mapper.readValue(parser, JsonNode.class))) {
                        builder.append(node.get("text") + "\n");
                    }
                    mTextView.setText(builder.toString());
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(TestActivity.this, "Test failed. See logcat.", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
        
        return super.onCreateOptionsMenu(menu);
    }
}
