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
import android.widget.ListAdapter;
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

public class assignment extends AppCompatActivity {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "session_id_moodleplus";
    private RequestQueue _requestQueue;
    private SharedPreferences _preferences;
    public RequestQueue getRequestQueue() {
        return _requestQueue;
    }

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
    public static String func1(String s1,String s2)
    {
        String sa1[] = s1.split(" ");
        String x1[] = sa1[0].split("-");
        String y1[] = sa1[1].split(":");

        int a1[] = new int[3];
        int b1[] = new int[3];

        String sa2[] = s2.split(" ");
        String x2[] = sa2[0].split("-");
        String y2[] = sa2[1].split(":");

        int a2[] = new int[3];
        int b2[] = new int[3];


        for(int i=0;i<3;i++)
        {
            a1[i] = Integer.parseInt(x1[i]);
            b1[i] = Integer.parseInt(y1[i]);

            a2[i] = Integer.parseInt(x2[i]);
            b2[i] = Integer.parseInt(y2[i]);
        }




        int years = a1[0]-a2[0];
        int months = a1[1]-a2[1];
        int days = a1[2]-a2[2];
        int hours = b1[0]-b2[0];
        int min = b1[1]-b2[1];
        int sec = b1[2]-b2[2];



        if(sec<0)
        {
            min--;
            sec+=60;
        }

        while(min<0)
        {
            hours--;
            min+=60;
        }

        while(hours<0)
        {
            days--;
            hours+=24;
        }

        while(days<0)
        {
            months--;
            days+=30;
        }

        String day = new String();
        String month = new String();
        String hour = new String();
        String minn = new String();
        String secc = new String();



        if(days/10==0)
        {
            day = "0" + String.valueOf(days);
        }
        else
            day = String.valueOf(days);


        if(months/10==0)
        {
            month = "0" + String.valueOf(months);
        }
        else
            month =  String.valueOf(months);


        if(hours/10==0)
        {
            hour = "0" + String.valueOf(hours);
        }
        else
            hour =  String.valueOf(hours);


        if(min/10==0)
        {
            minn = "0" + String.valueOf(min);
        }
        else
            minn = String.valueOf(min);

        if(sec/10==0)
        {
            secc = "0" + String.valueOf(sec);
        }
        else
            secc =  String.valueOf(sec);

        String ret="";
        if(!month.equals("00"))
        {
            ret= ret+month+" months ";
        }
        if(!day.equals("00"))
        {
            ret= ret+day+" days ";
        }
        if(!hour.equals("00"))
        {
            ret= ret+hour+" hours ";
        }
        if(!minn.equals("00"))
        {
            ret= ret+minn+" minutes";
        }
        if(ret.equals(""))
            ret="Just Now";


        return ret;

    }
    public static String func2(String s)
    {
        String d=s.replaceAll("<([^<>]*)>", " ");
        d=d.replaceAll("&nbsp;", "");
        d=d.replaceAll("-","\n-");
        return d;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle=getIntent().getExtras();
        code=bundle.getString("code");
        _preferences = PreferenceManager.getDefaultSharedPreferences(this);
        RequestQueue _requestQueue = Volley.newRequestQueue(this);
        Stringreq jsObjRequest = new Stringreq(Request.Method.GET, "http://tapi.cse.iitd.ernet.in:1805/courses/assignment.json/"+code,null, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                try{
                    JSONObject obj=new JSONObject(response);
                    JSONObject arr=obj.optJSONObject("assignment");
                    TextView assnname = (TextView)findViewById(R.id.textView7);
                    TextView details = (TextView)findViewById(R.id.textView9);
                    TextView createdat = (TextView)findViewById(R.id.textView14);
                    TextView deadline = (TextView)findViewById(R.id.textView15);
                    TextView timeremaining = (TextView)findViewById(R.id.textView16);
                    TextView latedateallowed = (TextView)findViewById(R.id.textView17);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());
                    assnname.setText(arr.getString("name"));
                    details.setText(func2(arr.getString("description")));
                    createdat.setText(arr.getString("created_at"));
                    deadline.setText(arr.getString("deadline"));
                    latedateallowed.setText(arr.getString("late_days_allowed"));
                    timeremaining.setText(assignment.func1(currentDateandTime,arr.getString("deadline"))+ " ago");


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
