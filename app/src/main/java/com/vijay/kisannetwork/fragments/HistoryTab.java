package com.vijay.kisannetwork.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vijay.kisannetwork.R;
import com.vijay.kisannetwork.adapters.SecondTabAdapterView;

/**
 * Created by vijay on 16/04/17.
 * License is only applicable to individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */
public class HistoryTab extends Fragment {


    public static final CharSequence TITLE = "Message History";
    private SecondTabAdapterView adapterFirstTab;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.second_tab, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_second_tab);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterFirstTab = new SecondTabAdapterView();
        recyclerView.setAdapter(adapterFirstTab);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapterFirstTab.notifyDataSetChangedOver();
    }
}
