package ro.pub.cs.systems.eim.lab03.contactsmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {
    private boolean visibleFields = false;
    private Button saveButton, cancelButton, fieldsButton;
    private EditText imText, jobText, companyText, websiteText;
    private EditText nameText, phoneText, addressText, emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        fieldsButton = findViewById(R.id.showFieldsButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        jobText = findViewById(R.id.jobText);
        companyText = findViewById(R.id.companyText);
        websiteText = findViewById(R.id.websiteText);
        imText = findViewById(R.id.imText);
        nameText = findViewById(R.id.nameText);
        addressText = findViewById(R.id.addressText);
        emailText = findViewById(R.id.emailText);
        phoneText = findViewById(R.id.phoneText);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab03.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }

        fieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibleFields = !visibleFields;
                int visibility = visibleFields ? View.VISIBLE : View.INVISIBLE;

                jobText.setVisibility(visibility);
                companyText.setVisibility(visibility);
                websiteText.setVisibility(visibility);
                imText.setVisibility(visibility);

                if (visibility == View.VISIBLE) {
                    fieldsButton.setText("HIDE ADDITIONAL FIELDS");
                } else {
                    fieldsButton.setText("SHOW ADDITIONAL FIELDS");
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = null, phone = null, email = null, address = null;
                String jobTitle = null, website = null, company = null, im = null;

                if (visibleFields) {
                    jobTitle = jobText.getText().toString();
                    website = websiteText.getText().toString();
                    company = companyText.getText().toString();
                    im = imText.getText().toString();
                }

                name = nameText.getText().toString();
                phone = phoneText.getText().toString();
                email = emailText.getText().toString();
                address = addressText.getText().toString();

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (name != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (phone != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (email != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (address != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (jobTitle != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }
                if (company != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }
                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }
                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }
                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
