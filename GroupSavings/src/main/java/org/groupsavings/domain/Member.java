package org.groupsavings.domain;

/**
 * Created by shashank on 1/3/14.
 */
public class Member {
    public int UID;
    public int GroupUID;
    public String FirstName;
    public String LastName;
    public char Sex;
    public int age;
    public String ContactInfo;
    public double TotalSavings;
    public double TotalLoan;


    @Override
    public String toString() {
        return FirstName + " " + LastName;
    }
}
