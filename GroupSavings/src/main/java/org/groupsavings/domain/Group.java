package org.groupsavings.domain;

/**
 * Created by shashank on 8/3/14.
 */
public class Group {

    public int UID;
    public String GroupName;
    public String AddressLine1;
    public String AddressLine2;
    public int FOId;
    public int PresidentId;
    public int RecurringSavings;
    public String CreatedAt;
    public int CreatedBy;
    public String BankAccount;
    public String MonthlyMeetingDate;
    public int NoOfSubgroups;
    public long TotalSavings;
    public long TotalOutstanding;
    public int NoOfMembers;

    @Override
    public String toString()
    {
        return GroupName + "    Area : " + AddressLine2 + "    Savings : " + TotalSavings + "    Outstanding Loans : " + TotalOutstanding
                + "    Members : "+NoOfMembers;
    }

}
