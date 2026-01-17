package pl.pjatk.s34477Bank;

public class Client {
    private int id;
    private String name;
    private String surname;
    private double balance;

    public Client(int id, String name, String surname, double balance) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public void setName(String newName) {
        this.name = newName;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Name: " + name + ", Surname: " + surname + ", Balance: " + balance;
    }
}

