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
    public String Email;
    public char Sex;
    public String DOB;
    public String AddressLine1;
    public String AddressLine2;
    public String ContactInfo;
    public double TotalSavings;
    public double TotalLoan;
    public Date CreatedAt;


    @Override
    public String toString() {
        return FirstName + " " + LastName;
    }
}
