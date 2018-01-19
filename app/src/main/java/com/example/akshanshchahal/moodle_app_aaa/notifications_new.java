package com.example.akshanshchahal.moodle_app_aaa;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class notifications_new extends Fragment {

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
    public notifications_new() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v=inflater.inflate(R.layout.notification_new, container, false);
        final ListView tryList = (ListView) v.findViewById(R.id.notificationlist);
        _preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
        RequestQueue _requestQueue = Volley.newRequestQueue(v.getContext());
        Stringreq jsObjRequest = new Stringreq(Request.Method.GET, "http://tapi.cse.iitd.ernet.in:1805/default/notifications.json",null, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                try{
                    JSONObject obj=new JSONObject(response);
                    JSONArray arr=obj.optJSONArray("notifications");


                    ArrayList<String[]> alist=new ArrayList<String[]>();


                    for(int i=0;i<arr.length();i++)
                    {
                        String test[]=new String[2];
                        JSONObject h=arr.getJSONObject(i);

                        test[0]=h.getString("description");
                        test[1]=h.getString("created_at");


                        alist.add(test);
                    }
                    ListAdapter myAdapter1 = new notification_customadapter(v.getContext(), alist);

                    tryList.setAdapter(myAdapter1);

                }
                catch(JSONException exp)
                {
                    Toast.makeText(v.getContext(), "ERROR", Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });


        _requestQueue.add(jsObjRequest);


        // Inflate the layout for this fragment
        return v;
    }

}
