package com.vijay.kisannetwork.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orm.util.Collection;
import com.vijay.kisannetwork.R;
import com.vijay.kisannetwork.activities.ContactsInfoActivity;
import com.vijay.kisannetwork.adapters.ContactListAdapter;
import com.vijay.kisannetwork.adapters.ContactsListener;
import com.vijay.kisannetwork.utility.Contacts;
import com.vijay.kisannetwork.utility.Key;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.R.id.list;

/**
 * Created by vijay on 16/04/17.
 * License is only applicable to individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */
public class ContactTab extends Fragment implements ContactsListener{
    public static final CharSequence TITLE = "Contacts";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.first_tab, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_first_tab);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ContactListAdapter contactListAdapter = new ContactListAdapter(this.parseJsonAndReturnContacts(this.loadJSONFromAsset()), this);

        recyclerView.setAdapter(contactListAdapter);

        return rootView;
    }


    ArrayList<Contacts> parseJsonAndReturnContacts(String jsonData) {
        ArrayList<Contacts> arrayList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonData);
            if (isOk(root, Key.CONTACTS)) {
                JSONArray arrayContacts = root.getJSONArray(Key.CONTACTS);
                for (int i = 0; i < arrayContacts.length(); i++) {
                    String fName = null, lName = null, phone = null, cCode = null;
                    JSONObject objectCont = arrayContacts.getJSONObject(i);
                    if (isOk(objectCont, Key.FIRST_NAME))
                        fName = objectCont.getString(Key.FIRST_NAME);
                    if (isOk(objectCont, Key.LAST_NAME))
                        lName = objectCont.getString(Key.LAST_NAME);
                    if (isOk(objectCont, Key.PHONE)) phone = objectCont.getString(Key.PHONE);
                    if (isOk(objectCont, Key.COUNTRY_CODE))
                        cCode = objectCont.getString(Key.COUNTRY_CODE);
                    Contacts contact = new Contacts(fName, lName, phone, cCode);
                    arrayList.add(contact);
                    Collections.sort(arrayList, new ContactComparator());


                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }



    boolean isOk(JSONObject object, String jsonKey) {
        return object.has(jsonKey) && !object.isNull(jsonKey);
    }


    @Override
    public void contactClicked(Contacts contacts) {
        Intent intent = new Intent(getContext(), ContactsInfoActivity.class);
        intent.putExtra(Key.FIRST_NAME, contacts.getFirstName());
        intent.putExtra(Key.LAST_NAME, contacts.getLastName());
        intent.putExtra(Key.PHONE, contacts.getPhone());
        intent.putExtra(Key.COUNTRY_CODE, contacts.getCountryCode());
        startActivity(intent);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getContext().getAssets().open("contactformat.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


class ContactComparator implements Comparator<Contacts>{
    @Override
    public int compare(Contacts p1, Contacts p2) {

        return p1.getFirstName().compareTo(p2.getFirstName());
    }
}


}
