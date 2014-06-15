package org.groupsavings.domain;

/**
 * Created by shashank on 8/3/14.
 */
public class Group {

    public int Id;
    public String Name;
    public String PresidentId;
    public String SecretaryId;
    public String TreasurerId;
    public String FieldOfficerId;
    public int Active;
    public int MonthlyCompulsoryAmount;
    public String MonthlyMeetingDate;
    public String Bank;
    public int ClusterId;
    public int CummulativeSavings;
    public int OtherIncome;
    public int OutstandingLoans;
    public String DateOfFormation;
    public int NoOfSubgroups;
    public String AddressLine1;
    public String AddressLine2;
    public String City;
    public String State;
    public String Country;
    public String CreatedDate;
    public int CreatedBy;
    public String ModifiedDate;
    public int ModifiedBy;

    @Override
    public String toString()
    {
        return Name + "    Area : " + AddressLine2 + "    Savings : " + CummulativeSavings + "    Outstanding Loans : " + OutstandingLoans;
             //   + "    Members : "+NoOfMembers;
    }

}
