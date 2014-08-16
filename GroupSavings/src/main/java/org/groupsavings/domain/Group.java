package org.groupsavings.domain;

import java.io.Serializable;

/**
 * Created by shashank on 8/3/14.
 */
public class Group implements Serializable{

    public String Id;
    public String Name;
    public Member President;
    public Member Secretary;
    public Member Treasurer;
    public String FieldOfficerId;
    public boolean Active;
    public int MonthlyMeetingDate;
    public float MonthlyCompulsoryAmount;
    public String Bank;
    public float ClusterId;
    public float CummulativeSavings;
    public float OtherIncome;
    public float OutstandingLoans;
    public String DateOfFormation;
    public int NoOfSubgroups;
    public String AddressLine1;
    public String AddressLine2;
    public String City;
    public String State;
    public String Country;
    public String CreatedDate;
    public String CreatedBy;
    public String ModifiedDate;
    public String ModifiedBy;
    public String NoOfActiveMembers;

    @Override
    public String toString()
    {
        return Name + "    Area : " + AddressLine2 + "    Savings : " + CummulativeSavings + "    Outstanding Loans : " + OutstandingLoans
                + "    Other Income : " + OtherIncome ;
    }

}
