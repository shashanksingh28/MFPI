package org.groupsavings.domain;

/**
 * Created by shashank on 30/3/14.
 */
public class SavingTransaction {

    public String GroupId;
    public String SavingAccountId;
    public String MeetingId;
    public String Type;
    public float Amount;
    public float CurrentBalance;
    public String DateTime;

    // Moved to Saving Account
/*    public int getTotalSavings() {
        return groupCompulsorySavings + optionalSavings;
    }
    */

}
