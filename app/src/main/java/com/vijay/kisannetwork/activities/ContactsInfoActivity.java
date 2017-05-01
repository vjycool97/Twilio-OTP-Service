package com.vijay.kisannetwork.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.vijay.kisannetwork.R;
import com.vijay.kisannetwork.utility.Contacts;
import com.vijay.kisannetwork.utility.Key;

/**
 * User comes to this activity after selecting contact from first tab fragment
 */
public class ContactsInfoActivity extends AppCompatActivity {


    TextView tvFName, tvLName, tvCCode;
    ImageButton callUser, msgUser;
    EditText tvPhone;
    private Contacts contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_detail);
        contact = this.receiveContactInfo(getIntent());
        this.initializeViews(contact);

        callUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contact.getPhone(), null));
                try {
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getBaseContext(), "Could not find an activity to place the call.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        msgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });

        ImageButton backButton = (ImageButton)findViewById(R.id.back_button1);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    /**
     * Initializes viws
     * @param contact Instance of contact
     */
    private void initializeViews(Contacts contact) {
        tvFName = (TextView) findViewById(R.id.first_name_contactdetails);
        tvLName = (TextView) findViewById(R.id.last_name_contactdetails);
        tvPhone = (EditText) findViewById(R.id.phone_contactdetails);
        tvCCode = (TextView) findViewById(R.id.country_code_contactdetails);

        callUser = (ImageButton)findViewById(R.id.call_user);
        msgUser  = (ImageButton)findViewById(R.id.msg_user);

        tvFName.setText(contact.getFirstName());
        tvLName.setText(contact.getLastName());
        tvPhone.setText(contact.getPhone());
        tvCCode.setText(contact.getCountryCode());

    }

    /**
     * Extracts data from Intent and
     * @param intent Intent containg info
     * @return {@link Contacts instance}
     */
    private Contacts receiveContactInfo(Intent intent) {
        String fName = intent.getStringExtra(Key.FIRST_NAME);
        String lName = intent.getStringExtra(Key.LAST_NAME);
        String phone = intent.getStringExtra(Key.PHONE);
        String ccode = intent.getStringExtra(Key.COUNTRY_CODE);
        return new Contacts(fName, lName, phone, ccode);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, ComposeActivity.class);
        intent.putExtra(Key.FIRST_NAME, contact.getFirstName());
        intent.putExtra(Key.LAST_NAME, contact.getLastName());
        intent.putExtra(Key.PHONE, contact.getPhone());
        intent.putExtra(Key.COUNTRY_CODE, contact.getCountryCode());
        startActivity(intent);

    }

    private void sendSMS() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra("address",contact.getPhone());
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Enter your message here");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        }
        else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address",contact.getPhone());
            smsIntent.putExtra("sms_body","message");
            startActivity(smsIntent);
        }
    }

}
