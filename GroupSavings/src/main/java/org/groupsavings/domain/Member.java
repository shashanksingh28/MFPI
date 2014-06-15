package org.groupsavings.domain;

import java.util.Date;

/**
 * Created by shashank on 1/3/14.
 */
public class Member {

    public int UID;
    public int GroupUID;
    public String FirstName;
    public String LastName;
    public String GuardianName;
    public String Email;
    public char Sex;
    public String DOB;
    public String AddressLine1;
    public String AddressLine2;
    public String ContactInfo;
    public String Passbook;
    public String Occupation;
    public long AnnualIncome;
    public String EconomicCondition;
    public String Education;
    public String Disability;
    public int NoOfFamilyMembers;
    public double TotalSavings;
    public double OutstandingLoan;
    public Date CreatedAt;


    @Override
    public String toString() {
        return FirstName + " " + LastName;
    }
}
