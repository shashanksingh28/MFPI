package org.groupsavings.domain;

import java.io.Serializable;

/**
 * Created by shashank on 22/6/14.
 */
public class MeetingLoanAccTransaction implements Serializable {

    public Group Group;

    public Member Member;

    public LoanAccount LoanAccount;
    public LoanTransaction LoanAccTransaction;

    public MeetingLoanAccTransaction(Group group, Member member, LoanAccount loanAccount)
    {
        this.Group = group;
        this.Member = member;
        this.LoanAccount = loanAccount;
        LoanAccTransaction = new LoanTransaction();
        LoanAccTransaction.LoanAccountId = loanAccount.Id;
        LoanAccTransaction.GroupId = loanAccount.GroupId;
        LoanAccTransaction.Repayment = loanAccount.EMI;
        LoanAccTransaction.Outstanding = loanAccount.Outstanding - LoanAccTransaction.Repayment;
    }

    public float getOutstanding()
    {
        return LoanAccount.Outstanding - LoanAccTransaction.Repayment;
    }

}
