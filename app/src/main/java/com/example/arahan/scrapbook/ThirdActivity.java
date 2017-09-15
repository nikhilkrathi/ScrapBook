package com.example.arahan.scrapbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arahan.scrapbook.database.DBHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.data;
import static android.R.id.list;
import static android.system.Os.remove;
import static com.example.arahan.scrapbook.R.id.listview;

public class ThirdActivity extends AppCompatActivity {

    ListView listView;
    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        listView = (ListView) findViewById(listview);

        final ArrayList<String> values = new ArrayList<>();
        ArrayList<HashMap<String, String>> data = dbHelper.getAllUsers();
        for (HashMap<String, String> hm : data) {
            String output = "";
            output += "Name: ";
            output += hm.get("name") + "\n";
            output += "Email: ";
            output += hm.get("email") + "\n";
            output += "Hobbies: ";
            output += hm.get("hobbies") + "\n";
            output += "Best friend: ";
            output += hm.get("best_friend") + "\n";
            output += "Contact: ";
            output += hm.get("contact") + "\n";
            output += "Crush: ";
            output += hm.get("crush") + "\n";
            values.add(output);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, values);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), item, Toast.LENGTH_SHORT).show();
            }

        });

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
