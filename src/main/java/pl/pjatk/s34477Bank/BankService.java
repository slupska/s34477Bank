package pl.pjatk.s34477Bank;

import org.springframework.stereotype.Component;

import java.util.Optional;

import static pl.pjatk.s34477Bank.Status.ACCEPTED;
import static pl.pjatk.s34477Bank.Status.DECLINED;

@Component
public class BankService {
    private ClientStorage clientStorage;

    public BankService(ClientStorage clientStorage) {
        this.clientStorage = clientStorage;
    }

    public Optional<Client> registerClient(int id, String name, String surname, double balance) {
        Client client = new Client(id, name, surname, balance);
        clientStorage.addClient(client);
        return Optional.of(client);
    }


    public Optional<Client> readClient(int clientId) {
        Optional<Client> c = clientStorage.getClientByID(clientId);
        c.ifPresent(client -> System.out.println(client));
        return c;
    }

    public TransactionResult makeTransfer(int clientId, double amount) {
        Optional<Client> clientOpt = clientStorage.getClientByID(clientId);

        if (clientOpt.isEmpty()) {
            return new TransactionResult(DECLINED, 0.0);
        }

        if (amount <= 0) {
            return new TransactionResult(DECLINED, clientOpt.get().getBalance());
        }

        Client client = clientOpt.get();
        double balance = client.getBalance();

        if (amount > balance) {
            return new TransactionResult(DECLINED, balance);
        }

        double newBalance = balance - amount;
        client.setBalance(newBalance);

        return new TransactionResult(ACCEPTED, newBalance);
    }

    public TransactionResult deposit(int clientId, double amount) {
        Optional<Client> clientOpt = clientStorage.getClientByID(clientId);

        if (clientOpt.isEmpty()) {
            return new TransactionResult(DECLINED, 0.0);
        }

        if (amount <= 0) {
            return new TransactionResult(DECLINED, clientOpt.get().getBalance());
        }

        Client client = clientOpt.get();
        double newBalance = client.getBalance() + amount;
        client.setBalance(newBalance);

        return new TransactionResult(ACCEPTED, newBalance);
    }



}








