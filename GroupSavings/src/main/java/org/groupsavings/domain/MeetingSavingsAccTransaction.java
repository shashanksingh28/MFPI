package org.groupsavings.domain;

import java.io.Serializable;

/**
 * Created by shashank on 22/6/14.
 * Class that encapsulates different types of Saving Acc related transactions
 * that a member can do on a meeting. Each row in UI corresponds to one such object
 */
public class MeetingSavingsAccTransaction implements Serializable {

    public Group Group;

    public Member Member;

    public SavingsAccount SavingsAccount;

    public SavingTransaction CompulsorySavingTransaction;

    public SavingTransaction OptionalSavingTransaction;

    public SavingTransaction WithdrawOptionalSavingTransaction;

    public MeetingSavingsAccTransaction(Group group, Member member, SavingsAccount account)
    {
        this.Group = group;
        this.Member = member;

        this.CompulsorySavingTransaction = new SavingTransaction();
        CompulsorySavingTransaction.Type = "C";
        CompulsorySavingTransaction.Amount = group.MonthlyCompulsoryAmount;

        this.OptionalSavingTransaction = new SavingTransaction();
        OptionalSavingTransaction.Type = "O";

        this.WithdrawOptionalSavingTransaction  = new SavingTransaction();
        WithdrawOptionalSavingTransaction.Type = "W";

        this.SavingsAccount = CompulsorySavingTransaction.SavingAccount = OptionalSavingTransaction.SavingAccount
                = WithdrawOptionalSavingTransaction.SavingAccount = account;
        CompulsorySavingTransaction.SavingAccountId = OptionalSavingTransaction.SavingAccountId
                = WithdrawOptionalSavingTransaction.SavingAccountId = account.Id;
    }

    public float getTotalSavings()
    {
        return CompulsorySavingTransaction.Amount + OptionalSavingTransaction.Amount;
    }
}
