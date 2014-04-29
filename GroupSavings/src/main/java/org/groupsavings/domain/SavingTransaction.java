package org.groupsavings.domain;

/**
 * Created by shashank on 30/3/14.
 */
public class SavingTransaction {

    public int Id;
    public int groupId;
    public int grpMeetingId;
    public int memberSavingAccId;
    public int optionalSavings;
    public int groupCompulsorySavings;
    public int transactionTotalSaving;
    public String timeStamp;

    public int getTotalSavings() {
        return groupCompulsorySavings + optionalSavings;
    }

}
