package org.groupsavings.domain;

/**
 * Created by shashank on 30/3/14.
 */
public class SavingTransaction {

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
