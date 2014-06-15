package org.groupsavings.domain;

import java.util.Date;

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
    public String Active;
    public String ContactNumber;
    public String AddressLine1;
    public String AddressLine2;
    public String State;
    public String Occupation;
    public double AnnualIncome;
    public String Education;
    public String Disability;
    public String NoOfFamilyMembers;
    public String Nominee;
    public String Insurance;
    public String ExitDate;
    public String ExitReason;
    public String CreatedDate;
    public String CreatedBy;
    public String ModifiedDate;
    public String ModifiedBy;


    @Override
    public String toString() {
        return FirstName + " " + LastName;
    }
}
