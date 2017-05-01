package com.vijay.kisannetwork.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.vijay.kisannetwork.R;
import com.vijay.kisannetwork.database.Messages;
import com.vijay.kisannetwork.utility.Constants;
import com.vijay.kisannetwork.utility.Contacts;
import com.vijay.kisannetwork.utility.Key;
import com.vijay.kisannetwork.fragments.FragmentDialog;
import com.vijay.kisannetwork.network.StringRequestOverridden;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Activity for Composing message after seleting contant and generating otp
 */
public class ComposeActivity extends AppCompatActivity {

    EditText editText;
    String otpSent;
    Contacts contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);


        contacts = this.receiveContactInfo(getIntent());

        editText = (EditText) findViewById(R.id.e_message_to_send);
        TextView noteTextView = (TextView) findViewById(R.id.note);
        noteTextView.setText("Messages will be sent to this number +91" + contacts.getPhone());
        otpSent = this.getSixDigitRandomNumber();

        String textToSend = "Hi, Your OTP is : " + otpSent;
        editText.setText(textToSend);
    }

    public void sendMessageUsingTwilio(final View view) {


        final Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(date);
        final Messages messages = new Messages(currentDateandTime, contacts.getPhone(), Constants.FROM,
                this.editText.getText().toString(), otpSent, "Unknown", contacts.getFirstName() + " " + contacts.getLastName());

//        ((Button) view).setText("Sending to " + contacts.getFirstName());
        view.setEnabled(false);

        StringRequestOverridden stringRequestOverridden = new StringRequestOverridden
                (
                        Constants.TWILIO_BASE,
                        Constants.ACCOUNT_SID,
                        Constants.AUTH_TOKEN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                FragmentDialog fragmentDialog = new FragmentDialog();

                                Snackbar snackbar = Snackbar.make(view, "Message successfully sent!", Snackbar.LENGTH_SHORT);
                                try {
                                    JSONObject object = new JSONObject(response);
                                    if (object.has(Key.BODY) && !object.isNull(Key.BODY)) {


                                        snackbar.setCallback(new Snackbar.Callback() {
                                            @Override
                                            public void onDismissed(Snackbar snackbar, int event) {
                                                super.onDismissed(snackbar, event);
                                                Log.e("Ondismissed", "Called");
                                                clearStackAndLandHomeActivity();
                                            }

                                            @Override
                                            public void onShown(Snackbar snackbar) {
                                                super.onShown(snackbar);
                                                Log.e("ONShown", "called");
                                            }
                                        });
                                        snackbar.show();
                                        messages.setSentstatus("Sent");
                                        Messages.save(messages);
                                        return;

//                                        fragmentDialog.initDialogue(object.getString(Key.BODY), "OK");
//                                        Toast.makeText(ComposeActivity.this, object.getString(Key.BODY), Toast.LENGTH_LONG).show();
//                                        ((Button) view).setText("Sent to " + contacts.getFirstName());
//                                        finish();
                                    } else {
                                        fragmentDialog.initDialogue("Server Error : Not Specified", "OK");
                                        messages.setSentstatus("Body Not Received");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                fragmentDialog.show(getSupportFragmentManager(), "DialogFragment");
                                view.setEnabled(true);
                                Log.e("TWILIO", "" + response);
                                Messages.save(messages);
                            }
                        },
                        new Response.ErrorListener() {
                            boolean isOk(JSONObject object, String jsonKey) {
                                return object.has(jsonKey) && !object.isNull(jsonKey);
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                FragmentDialog fragmentDialog = new FragmentDialog();
                                String message = null;
                                if (error instanceof NetworkError) {
                                    message = "Cannot connect to Internet.\nPlease check your connection!";
                                } else if (error instanceof ServerError) {
                                    message = "The server could not be found. \nPlease try again after some time!!";
                                } else if (error instanceof AuthFailureError) {
                                    message = "Authentication Failure!\nAccount SID and Auth Token Mismatch";
                                } else if (error instanceof ParseError) {
                                    message = "Parsing error!\nPlease try again after some time!!";
                                } else if (error instanceof TimeoutError) {
                                    message = "Connection TimeOut!\nPlease check your internet connection.";
                                }
                                if (error.networkResponse != null && error.networkResponse.data != null) {
                                    try {
                                        String string = new String(error.networkResponse.data);
                                        JSONObject object = new JSONObject(string);
                                        if (isOk(object, Key.MESSAGE)) {
                                            message = object.getString(Key.MESSAGE);

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        fragmentDialog.initDialogue("Error : " + String.valueOf(error.networkResponse.statusCode), "OK");

                                    }

                                }
                                fragmentDialog.initDialogue(message, "OK");
                                fragmentDialog.show(getSupportFragmentManager(), "DialogError");

                                messages.setSentstatus("Failed");

                                view.setEnabled(true);

                                Log.e("Erro", "" + error.getMessage());
                                Messages.save(messages);

                            }
                        }
                ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("To", "+" + contacts.getCountryCode() + contacts.getPhone());
                params.put("From", Constants.FROM);
                params.put("Body", editText.getText().toString());
                return params;
            }
        };


        Volley.newRequestQueue(this).add(stringRequestOverridden);

    }

    private void clearStackAndLandHomeActivity() {
        /**
         * go back to HOme Activity
         */
        Intent i = new Intent(ComposeActivity.this, MainActivity.class);//homescreen of your app.
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        finish();
    }

    String getSixDigitRandomNumber() {
        Random random = new Random();
        int numberSix = 100000 + random.nextInt(900000);
        return String.valueOf(numberSix);
    }


    private Contacts receiveContactInfo(Intent intent) {
        String fName = intent.getStringExtra(Key.FIRST_NAME);
        String lName = intent.getStringExtra(Key.LAST_NAME);
        String phone = intent.getStringExtra(Key.PHONE);
        String ccode = intent.getStringExtra(Key.COUNTRY_CODE);
        return new Contacts(fName, lName, phone, ccode);
    }

}
