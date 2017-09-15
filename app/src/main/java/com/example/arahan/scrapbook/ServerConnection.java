package com.example.arahan.scrapbook;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.arahan.scrapbook.database.DBHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Created by Ashish Pawar(ashishpawar2015.ap@gmail.com) on 15/9/17.
 */

public class ServerConnection {
    private AQuery aQuery;
    private Context context;
    String send = "http://192.168.43.17/ScrapBook/index.php/Scrapbook/sendData";
    String fetch = "http://192.168.43.17/ScrapBook/index.php/Scrapbook/fetchData";

    public ServerConnection(Context context) {
        aQuery = new AQuery(context);
        this.context = context;
    }

    public void sendData(HashMap<String, Object> sendParams) {

        aQuery.ajax(send, sendParams, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                int code = 0;
                if (object != null) {
                    Log.d("sendData","object!=null");
                    try {
                        code = object.getInt("ResultSet");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("sendData","object=null");
                    Toast.makeText(context, "Failed to send data", Toast.LENGTH_SHORT).show();
                }

                if (status.getCode() == 200) {
                    if (code == 0) {
                        Toast.makeText(context, "Data sent successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void FetchData() {
        Map<String, Object> fetchParams = new HashMap<>();

        aQuery.ajax(fetch, fetchParams, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                int length = 0;
                ArrayList<HashMap<String, String>> queryValues = new ArrayList<HashMap<String, String>>();
                if (object != null) {
                    Log.d("fetchData","object!=null");
                    try {
                        String data = object.getString("data");
                        length = object.getInt("num");

                        JSONObject reader = new JSONObject(data);

                        for (int i = 0; i < length; i++) {
                            JSONObject obj = reader.getJSONObject(String.valueOf(i));
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("name", obj.getString("name"));
                            map.put("email", obj.getString("email"));
                            map.put("hobbies", obj.getString("hobbies"));
                            map.put("best_friend", obj.getString("best_friend"));
                            map.put("contact", obj.getString("contact"));
                            map.put("crush", obj.getString("crush"));

                            queryValues.add(map);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.d("fetchData","object=null");
                    Toast.makeText(context, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
                if (status.getCode() == 200) {
                    DBHelper helper = new DBHelper(context);
                    helper.truncateTable();
                    for (int i = 0; i < length; i++)
                        helper.insertUser(queryValues.get(i));
                    Toast.makeText(context, "Data fetched successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
