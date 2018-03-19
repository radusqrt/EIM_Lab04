package ro.pub.cs.systems.eim.lab03.phonedialer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneDialerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);

        View.OnClickListener digitListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numberText = findViewById(R.id.numberText);
                Button digitButton = (Button) v;
                numberText.setText(numberText.getText().toString() + digitButton.getText().toString());
            }
        };

        findViewById(R.id.button0).setOnClickListener(digitListener);
        findViewById(R.id.button1).setOnClickListener(digitListener);
        findViewById(R.id.button2).setOnClickListener(digitListener);
        findViewById(R.id.button3).setOnClickListener(digitListener);
        findViewById(R.id.button4).setOnClickListener(digitListener);
        findViewById(R.id.button5).setOnClickListener(digitListener);
        findViewById(R.id.button6).setOnClickListener(digitListener);
        findViewById(R.id.button7).setOnClickListener(digitListener);
        findViewById(R.id.button8).setOnClickListener(digitListener);
        findViewById(R.id.button9).setOnClickListener(digitListener);
        findViewById(R.id.buttonStar).setOnClickListener(digitListener);
        findViewById(R.id.buttonSharp).setOnClickListener(digitListener);
        findViewById(R.id.buttonBackspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView numberText = findViewById(R.id.numberText);
                String text = numberText.getText().toString();
                if (text.length() > 0) {
                    numberText.setText(text.substring(0, text.length() - 1));
                }
            }
        });

        findViewById(R.id.buttonCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            PhoneDialerActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + ((TextView)findViewById(R.id.numberText)).getText().toString()));
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.buttonHangUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.saveNumberButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = ((TextView)findViewById(R.id.numberText)).getText().toString();
                if (phoneNumber.length() > 0) {
                    Intent intent = new Intent("ro.pub.cs.systems.eim.lab04.contactsmanager.intent.action.ContactsManagerActivity");
                    intent.putExtra("ro.pub.cs.systems.eim.lab03.contactsmanager.PHONE_NUMBER_KEY", phoneNumber);
                    startActivityForResult(intent, 27);
                } else {
                    Toast.makeText(getApplication(), getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
