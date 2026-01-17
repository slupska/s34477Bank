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

		Client client1 = new Client(1, "Robert", "Nowak", 1000);
		Client client2 = new Client(2, "Marek", "Kowalski", 100);


	}

	public static void main(String[] args) {
		SpringApplication.run(S34477BankApplication.class, args);
	}

}
