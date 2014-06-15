package org.groupsavings.domain;

/**
 * Created by shashank on 30/3/14.
 */
public class SavingsAccount {

    public String Id;
    public String MemberId;
    public String GroupId;
    public float CompulsorySavings;
    public float OptionalSavings;
    public float InterestAccumulated;
    public float TotalSavings;
    public String CreatedDate;
    public String CreatedBy;
    public String Active;


    public float getTotalSavings() {
        return CompulsorySavings + OptionalSavings;
    }
}
