package org.groupsavings.domain;

/**
 * Created by shashank on 22/6/14.
 * Class that encapsulates different types of Saving Acc related transactions
 * that a member can do on a meeting. Each row in UI corresponds to one such object
 */
public class MeetingSavingsAccTransaction {

    public Group Group;

    public Member Member;

    public SavingsAccount SavingsAccount;

    public SavingTransaction CompulsorySavingTransaction;

    public SavingTransaction OptionalSavingTransaction;

    public MeetingSavingsAccTransaction(Group group, Member member, SavingsAccount account)
    {
        this.Group = group;
        this.Member = member;
        this.SavingsAccount = account;
        this.CompulsorySavingTransaction = new SavingTransaction();
        CompulsorySavingTransaction.Type = "C";
        CompulsorySavingTransaction.Amount = group.MonthlyCompulsoryAmount;
        CompulsorySavingTransaction.SavingAccount = account;
        this.OptionalSavingTransaction = new SavingTransaction();
        OptionalSavingTransaction.Type = "O";
        OptionalSavingTransaction.SavingAccount = account;
    }

    public float getTotalSavings()
    {
        return CompulsorySavingTransaction.Amount + OptionalSavingTransaction.Amount;
    }
}
