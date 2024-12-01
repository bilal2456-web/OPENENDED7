package AMOUNT1;

class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName() + " deposited " + amount + ". New balance: " + balance);
    }

    public synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrew " + amount + ". New balance: " + balance);
        } else {
            System.out.println(Thread.currentThread().getName() + " tried to withdraw " + amount + ". Insufficient balance!");
        }
    }

    public synchronized int getBalance() {
        return balance;
    }
}

class Transaction implements Runnable {
    private final BankAccount account;
    private final String transactionType;
    private final int amount;

    public Transaction(BankAccount account, String transactionType, int amount) {
        this.account = account;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    @Override
    public void run() {
        try {
            if ("DEPOSIT".equalsIgnoreCase(transactionType)) {
                account.deposit(amount);
            } else if ("WITHDRAW".equalsIgnoreCase(transactionType)) {
                account.withdraw(amount);
            }
            Thread.sleep(500); // Simulate transaction processing time
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " interrupted.");
        }
    }
}


public class AMOUNT1 {

	public AMOUNT1() {
		// TODO Auto-generated constructor stub
		BankAccount account = new BankAccount(1000);

        Thread t1 = new Thread(new Transaction(account, "DEPOSIT", 500), "Thread-1");
        Thread t2 = new Thread(new Transaction(account, "WITHDRAW", 200), "Thread-2");
        Thread t3 = new Thread(new Transaction(account, "WITHDRAW", 1000), "Thread-3");

        t1.start();
        t2.start();
        t3.start();
	}

}
