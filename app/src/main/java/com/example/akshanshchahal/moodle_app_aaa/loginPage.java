package com.example.akshanshchahal.moodle_app_aaa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;

public class loginPage extends AppCompatActivity {
    String s;
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "session_id_moodleplus";
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;
    public RequestQueue getRequestQueue() {
        return _requestQueue;
    }

    private static loginPage _instance;

    public static loginPage get() {
        return _instance;
    }
    public final void checkSessionCookie(Map<String, String> headers) {
        if (headers.containsKey(SET_COOKIE_KEY)
                && headers.get(SET_COOKIE_KEY).startsWith(SESSION_COOKIE)) {
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                SharedPreferences.Editor prefEditor = _preferences.edit();
                prefEditor.putString(SESSION_COOKIE, cookie);
                prefEditor.commit();
            }
        }
    }

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    public final void addSessionCookie(Map<String, String> headers) {
        String sessionId = _preferences.getString(SESSION_COOKIE, "");
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        _instance=this;
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        setSupportActionBar(toolbar);
        final Button button_s = (Button) findViewById(R.id.student_login_button);

        EditText username = (EditText) findViewById(R.id.username_input);
        EditText password = (EditText) findViewById(R.id.password_input);
        final String u = username.getText().toString();
        final String p = password.getText().toString();
        //
        button_s.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Button button_s = (Button) findViewById(R.id.student_login_button);

                EditText username = (EditText) findViewById(R.id.username_input);
                EditText password = (EditText) findViewById(R.id.password_input);
                final String u = username.getText().toString();
                final String p = password.getText().toString();
                sendRequest(u, p);



                }


        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }
    public void sendRequest(String u, String p) {

        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
        RequestQueue _requestQueue = Volley.newRequestQueue(getApplicationContext());


        Stringreq jsObjRequest = new Stringreq(Request.Method.GET, "http://tapi.cse.iitd.ernet.in:1805/default/login.json?userid="+u+"&password="+p,null, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                try{
                    JSONObject obj=new JSONObject(response);
                    if(obj.getString("success").equals("true"))
                    {
                        Intent i = new Intent(getApplicationContext(), navigation_draewer.class);
                        startActivity(i);
                        TextView tv = (TextView)findViewById(R.id.textView27);
                        tv.setText("");
                    }
                    else
                    {
                        TextView tv = (TextView)findViewById(R.id.textView27);
                        tv.setText("Invalid username or password");

                    }
                }
                catch(JSONException exp)
                {
                    Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });


       _requestQueue.add(jsObjRequest);
    }
}

