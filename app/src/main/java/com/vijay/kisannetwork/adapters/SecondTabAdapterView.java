package com.vijay.kisannetwork.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vijay.kisannetwork.R;
import com.vijay.kisannetwork.database.Messages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by vijay on 16/04/17.
 * License is only applicable to individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */

public class SecondTabAdapterView extends RecyclerView.Adapter<SecondTabAdapterView.ViewHolder> {


    private List<Messages> arrayListMessages;

    public SecondTabAdapterView() {
        this.arrayListMessages = Messages.listAll(Messages.class);
        Collections.reverse(this.arrayListMessages);
    }

    @Override
    public SecondTabAdapterView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_secondtab_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SecondTabAdapterView.ViewHolder holder, int position) {

        Messages message = this.arrayListMessages.get(position);


        holder.textViewFullName.setText(message.getNameto());
        holder.textViewTime.setText(getFormat12HourHumarReadableWithoutYear(message.getTimenumber()));
        holder.textViewOtp.setText("OTP : " + message.getOtp());
        holder.textViewStatus.setText(message.getSentstatus());
        if (message.getSentstatus().equalsIgnoreCase("Sent")) {
            holder.textViewStatus.setTextColor(Color.parseColor("#519351"));
        } else {
            holder.textViewStatus.setTextColor(Color.parseColor("#ff0000"));
        }
    }


    @Override
    public int getItemCount() {
        return this.arrayListMessages.size();
    }

    public void notifyDataSetChangedOver() {
        this.arrayListMessages = Messages.listAll(Messages.class);
        Collections.reverse(this.arrayListMessages);
        super.notifyDataSetChanged();
    }

    public String getFormat12HourHumarReadableWithoutYear(String date) {
        if (date != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat reformatDateFormat = new SimpleDateFormat("dd MMM KK:mm a", Locale.getDefault());
            try {
                return reformatDateFormat.format(simpleDateFormat.parse(date)).replace("am", "AM").replace("pm", "PM");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return "-";
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFullName, textViewTime, textViewOtp, textViewStatus;

        ViewHolder(View itemView) {
            super(itemView);
            textViewFullName = (TextView) itemView.findViewById(R.id.t_name_sento_itemhistory);
            textViewTime = (TextView) itemView.findViewById(R.id.t_time_itemhistory);
            textViewOtp = (TextView) itemView.findViewById(R.id.t_otp_itemhistory);
            textViewStatus = (TextView) itemView.findViewById(R.id.t_status_itemhistory);

        }


    }
}
