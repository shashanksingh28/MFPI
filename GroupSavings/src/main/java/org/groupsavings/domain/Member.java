package org.groupsavings.domain;

/**
 * Created by shashank on 1/3/14.
 */
public class Member {

    public String Id;
    public String GroupId;
    public String FirstName;
    public String LastName;
    public String GuardianName;
    public String Gender;
    public String DOB;
    public String EmailId;
    public boolean Active;
    public String ContactNumber;
    public String AddressLine1;
    public String AddressLine2;
    public String Occupation;
    public float AnnualIncome;
    public String Education;
    public boolean Disability;
    public int NoOfFamilyMembers;
    public String Nominee;
    public boolean Insurance;
    public String ExitDate;
    public String ExitReason;
    public String CreatedDate;
    public String CreatedBy;
    public String ModifiedDate;
    public String ModifiedBy;
    public String Passbook;
    public String EconomicCondition;

    public float CurrentSavings;
    public float CurrentOutstanding;


    @Override
    public String toString() {
        return FirstName + " " + LastName;
    }
}
