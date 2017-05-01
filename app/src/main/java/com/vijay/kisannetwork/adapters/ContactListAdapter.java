package com.vijay.kisannetwork.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vijay.kisannetwork.R;
import com.vijay.kisannetwork.utility.Contacts;

import java.util.ArrayList;

/**
 * Created by vijay on 16/04/17.
 * License is only applicable to individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */

/**
 * Adapter for recycle view in first tab
 */
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private final ArrayList<Contacts> contactsList;
    private final ContactsListener listenerCallbackClicked;

    public ContactListAdapter(ArrayList<Contacts> contactsArrayList, ContactsListener contactsListener) {
        this.contactsList = contactsArrayList;
        this.listenerCallbackClicked = contactsListener;
    }

    @Override
    public ContactListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_firsttab_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ViewHolder holder, int position) {
        Contacts contact = this.contactsList.get(position);
        String fullName = contact.getFirstName() + " " + contact.getLastName();

        holder.textViewFullName.setText(fullName);

    }


    @Override
    public int getItemCount() {
        return this.contactsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewFullName;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewFullName = (TextView) itemView.findViewById(R.id.first_item_fullname);

        }

        @Override
        public void onClick(View view) {
            if (listenerCallbackClicked != null) {
                listenerCallbackClicked.contactClicked(contactsList.get(this.getLayoutPosition()));
            }

        }

    }
}
