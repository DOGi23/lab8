package com.dogi.lab8;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnWeb;
    Button btnMap;
    Button btnContact;
    Button btnCall;
    EditText address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btnWeb = (Button) findViewById(R.id.web);
        btnMap = (Button) findViewById(R.id.map);
        btnContact = (Button) findViewById(R.id.contact);
        btnCall = (Button) findViewById(R.id.call);
        address = (EditText) findViewById(R.id.maps);

        btnWeb.setOnClickListener(mButtonClickListener);
        btnMap.setOnClickListener(mButtonClickListener);
        btnContact.setOnClickListener(mButtonClickListener);
        btnCall.setOnClickListener(mButtonClickListener);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            btnWeb = (Button) findViewById(R.id.web);
            btnMap = (Button) findViewById(R.id.map);
            btnContact = (Button) findViewById(R.id.contact);
            btnCall = (Button) findViewById(R.id.call);
            int id = v.getId();
            Intent intent = null;
            try {
                if (id == R.id.web) {
                    String addressStr = address.getText().toString();
                    intent = new Intent(MainActivity.this, com.dogi.lab8.MyBrowser.class );
                    intent.putExtra("url", addressStr);

                } else if (id == R.id.map) {
                    String addressStr = address.getText().toString();
                    intent = new Intent(Intent.ACTION_VIEW);
                    String geoUri;
                    if (addressStr.isEmpty()) {
                        geoUri = "geo:59.397863,56.786704?z=17";
                    } else {
                        geoUri = "geo:0,0?q=" + Uri.encode(addressStr);
                    }
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                } else if (id == R.id.contact) {
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("content://contacts/people/"));
                } else if (id == R.id.call) {
                    intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:*105#"));
                }

                if (intent != null) {
                    startActivity(intent);
                }
            } catch (ActivityNotFoundException e) {
                Toast.makeText(MainActivity.this, "Что-то пошло не так", Toast.LENGTH_SHORT);

            }
        }
    };
}