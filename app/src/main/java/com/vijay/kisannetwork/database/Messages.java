package com.vijay.kisannetwork.database;

import com.orm.SugarRecord;

/**
 * Created by vijay on 16/04/17.
 * License is only applicable tonumber individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */

/**
 * For storing the data into the database
 */
public class Messages extends SugarRecord {
    private String timenumber;
    private String tonumber;
    private String fromnumber;
    private String message;
    private String otp;
    private String sentstatus;
    private String nameto;

    /**
     * There must be an empty Constructor
     */
    public Messages() {

    }

    public Messages(String time, String tonumber, String fromnumber, String message, String otp, String sentstatus, String nameto) {
        this.otp = otp;
        this.sentstatus = sentstatus;
        this.nameto = nameto;
        this.setTimenumber(time);
        this.setTonumber(tonumber);
        this.setFromnumber(fromnumber);
        this.setMessage(message);
    }

    public String getNameto() {
        return nameto;
    }

    public void setNameto(String nameto) {
        this.nameto = nameto;
    }

    public String getSentstatus() {
        return sentstatus;
    }

    public void setSentstatus(String sentstatus) {
        this.sentstatus = sentstatus;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getTimenumber() {
        return timenumber;
    }

    public void setTimenumber(String timenumber) {
        this.timenumber = timenumber;
    }

    public String getTonumber() {
        return tonumber;
    }

    public void setTonumber(String tonumber) {
        this.tonumber = tonumber;
    }

    public String getFromnumber() {
        return fromnumber;
    }

    public void setFromnumber(String fromnumber) {
        this.fromnumber = fromnumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
