package org.groupsavings.domain;

/**
 * Created by shashank on 22/6/14.
 */
public class MeetingLoanAccTransactions {

    public Group Group;

    public Member Member;

    public LoanAccount EmergencyLoan;
    public LoanTransaction EmergencyLoanTransaction;

    public LoanAccount NonEmergencyLoan;
    public LoanTransaction NonEmergencyLoanTransaction;

    public MeetingLoanAccTransactions(Group group, Member member, LoanAccount activeLoan, LoanAccount emergencyLoan)
    {
        this.Group = group;
        this.Member = member;
        this.NonEmergencyLoan = activeLoan;
        this.EmergencyLoan = emergencyLoan;

        EmergencyLoanTransaction = new LoanTransaction();
        NonEmergencyLoanTransaction = new LoanTransaction();

    }

}
