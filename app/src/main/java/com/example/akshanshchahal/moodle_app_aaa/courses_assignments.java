package com.example.akshanshchahal.moodle_app_aaa;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

/**
 * Created by amareshiitd on 21-02-2016.
 */
public class courses_assignments extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener{

    //Cookies
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.courses_assignments, container, false);
        final ListView tryList = (ListView) v.findViewById(R.id.coursesassignmentlist);
        Bundle args = getArguments();
        String ccode = null;
        if (args != null && args.containsKey("code")) {
            ccode = args.getString("code");
        }
        _preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        RequestQueue _requestQueue = Volley.newRequestQueue(v.getContext());
        Stringreq jsObjRequest = new Stringreq(Request.Method.GET, "http://tapi.cse.iitd.ernet.in:1805/courses/course.json/" + ccode + "/assignments", null, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray arr1 = obj.optJSONArray("assignments");

                    ArrayList<String[]> alist = new ArrayList<String[]>();


                    for (int i = 0; i < arr1.length(); i++) {
                        String test[] = new String[3];
                        JSONObject p = arr1.getJSONObject(i);
                        test[0] = p.getString("name");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentDateandTime = sdf.format(new Date());

                        test[1] = assignment.func1(currentDateandTime,p.getString("deadline"))+ " remaining";
                        test[2] = p.getString("id");
                        alist.add(test);
                    }
                    ListAdapter myAdapter1 = new assign_customAdapter(v.getContext(), alist);

                    tryList.setAdapter(myAdapter1);

                } catch (JSONException exp) {
                    Toast.makeText(v.getContext(), "ERROR", Toast.LENGTH_LONG).show();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });


        tryList.setOnItemClickListener(this);
        _requestQueue.add(jsObjRequest);
        return v;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        Intent i = new Intent(view.getContext(), assignment.class);
        String s[]=(String[])parent.getItemAtPosition(position);
        String move = String.valueOf(s[2]);
        i.putExtra("code", move);
        startActivity(i);
    }
}

