package pl.pjatk.s34477Bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class S34477BankApplication {
	private BankService bankService;
	private ClientStorage clientStorage;

	public S34477BankApplication(BankService bankService) {
		this.bankService = bankService;
		this.clientStorage = new ClientStorage();

		bankService.registerClient(1, "Robert", "Nowak", 1000);
		bankService.registerClient(2, "Marek", "Kowalski", 100);

		System.out.println("lista: " + clientStorage.getClients());
		clientStorage.removeClient(2);
		System.out.println("lista: " + clientStorage.getClients());

		bankService.readClient(1);

		System.out.println("\nPrzelew 200 z klienta 1:");
		TransactionResult t1 = bankService.makeTransfer(1, 200);
		System.out.println("Status: " + t1.getStatus() + ", nowe saldo: " + t1.getNewBalance());
		System.out.println("Po przelewie klient 1:");
		bankService.readClient(1);

		System.out.println("\nWpłata 200 do klienta 1:");
		TransactionResult t2 = bankService.deposit(1, 250);
		System.out.println("Status: " + t1.getStatus() + ", nowe saldo: " + t1.getNewBalance());
		System.out.println("Po przelewie klient 1:");
		bankService.readClient(1);

		System.out.println("\nWpłata 0 na klienta 2:");
		TransactionResult d2 = bankService.deposit(2, 0);
		System.out.println("Status: " + d2.getStatus() + ", saldo: " + d2.getNewBalance());

	}

	public static void main(String[] args) {
		SpringApplication.run(S34477BankApplication.class, args);
	}

}
