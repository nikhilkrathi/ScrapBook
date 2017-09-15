package com.example.arahan.scrapbook;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arahan.scrapbook.database.DBHelper;

import java.util.HashMap;

public class SecondActivity extends AppCompatActivity {

    FloatingActionButton fab;
    EditText name, email, hobbies, best_friend, contact, crush;
    Button save, upload, download;
    DBHelper dbHelper = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        name = (EditText) findViewById(R.id.et_name);
        email = (EditText) findViewById(R.id.et_email);
        hobbies = (EditText) findViewById(R.id.et_hobbies);
        best_friend = (EditText) findViewById(R.id.et_best_friend);
        contact = (EditText) findViewById(R.id.et_contact);
        crush = (EditText) findViewById(R.id.et_crush);
        save = (Button) findViewById(R.id.bt_save);
        upload = (Button) findViewById(R.id.bt_upload);
        download = (Button) findViewById(R.id.bt_download);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(i);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> values = new HashMap();
                values.put("name", name.getText().toString());
                values.put("email", email.getText().toString());
                values.put("hobbies", hobbies.getText().toString());
                values.put("best_friend", best_friend.getText().toString());
                values.put("contact", contact.getText().toString());
                values.put("crush", crush.getText().toString());

                if (dbHelper.insertUser(values))
                    Toast.makeText(getApplicationContext(), "Record Added Successfully!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Error while Adding Record", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
