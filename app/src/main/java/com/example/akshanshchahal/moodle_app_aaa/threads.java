package com.example.akshanshchahal.moodle_app_aaa;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class threads extends AppCompatActivity {

    public String theadid;
    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "session_id_moodleplus";
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;
    public RequestQueue getRequestQueue() {
        return _requestQueue;
    }
    public String threadid;
    private static courses_new _instance;

    public static courses_new get() {
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
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle=getIntent().getExtras();
        code=bundle.getString("code");
        RequestQueue _requestQueue = Volley.newRequestQueue(this);
        final ListView trylist = (ListView)findViewById(R.id.listView2);
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Stringreq jsObjRequest = new Stringreq(Request.Method.GET, "http://tapi.cse.iitd.ernet.in:1805/threads/thread.json/"+code,null, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                try{
                    JSONObject obj=new JSONObject(response);
                    JSONArray arr=obj.optJSONArray("comment_users");
                    JSONArray arr2=obj.optJSONArray("comments");
                    JSONObject info = obj.optJSONObject("thread");
                    TextView name = (TextView)findViewById(R.id.textView20);
                    TextView createdon = (TextView)findViewById(R.id.textView22);
                    TextView modifiedon = (TextView)findViewById(R.id.textView26);
                    TextView details = (TextView)findViewById(R.id.textView21);
                    name.setText(info.getString("title"));
                    details.setText(info.getString("description"));
                    createdon.setText("created at " + info.getString("created_at"));
                    modifiedon.setText("last updated on " + info.getString("updated_at"));

                    ArrayList<String[]> alist = new ArrayList<String[]>();
                    for (int i = 0; i < arr.length(); i++) {
                        String test[] = new String[3];
                        JSONObject p = arr.getJSONObject(i);
                        JSONObject p1 = arr2.getJSONObject(i);
                        test[0] = p.getString("first_name")+" "+p.getString("last_name");
                        test[1] = p1.getString("description");
                        test[2] = p1.getString("created_at");
                        threadid=info.getString("id");
                        alist.add(test);
                    }
                    ListAdapter myAdapter1 = new threaddetails_customadapter(getApplicationContext(), alist);

                    trylist.setAdapter(myAdapter1);


                }
                catch(JSONException exp)
                {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });
        Button b = (Button)findViewById(R.id.button6);


        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick( View v) {
                RequestQueue _requestQueue = Volley.newRequestQueue(getApplicationContext());
                ListView trylist = (ListView)findViewById(R.id.listView2);
                EditText ed = (EditText)findViewById(R.id.editText5);
                theadid=ed.getText().toString();
                _preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Stringreq jsObjRequest = new Stringreq(Request.Method.GET, "http://tapi.cse.iitd.ernet.in:1805/threads/post_comment.json?thread_id="+code+"&description="+theadid,null, new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        try {
                            JSONObject obj1 = new JSONObject(response);
                        } catch (JSONException exp) {
                            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
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

        });

        _requestQueue.add(jsObjRequest);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_courses_detail, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.logout) {
            Stringreq jsObjRequest = new Stringreq(Request.Method.GET, "http://tapi.cse.iitd.ernet.in:1805/default/logout.json", null, new Response.Listener<String>() {

                @Override

                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub

                }
            });
            Intent i = new Intent(getApplicationContext(), loginPage.class);
            startActivity(i);
            return true;
        }
        return true;
    }


}
