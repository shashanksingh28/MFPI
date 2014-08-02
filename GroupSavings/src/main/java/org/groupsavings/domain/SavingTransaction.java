package org.groupsavings.domain;

import java.io.Serializable;

/**
 * Created by shashank on 30/3/14.
 */
public class SavingTransaction implements Serializable{

    // Table fields
    public String GroupId;
    public String SavingAccountId;
    public String MeetingId;
    public String Type;
    public float Amount;
    public float CurrentBalance;
    public String DateTime;

    // Derived
    public SavingsAccount SavingAccount;

}
