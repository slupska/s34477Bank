package pl.pjatk.s34477Bank;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ClientStorage {
    private List<Client> clients;

    public ClientStorage() {
        this.clients = new ArrayList<>();
    }

    public List<Client> getClients() {
        return clients;
    }

    public void addClient(Client client) {
        if (client != null) {
            clients.add(client);
        }
    }
    public void removeClient(int id) {
        clients.removeIf(c -> c.getId() == id);
    }

    public Optional<Client> getClientByID(int id) {
        for (Client client : clients) {
            if (client.getId() == id) {
                return Optional.of(client);
            }
        }
        return Optional.empty();
    }

}
