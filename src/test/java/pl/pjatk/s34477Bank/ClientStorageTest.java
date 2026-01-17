package pl.pjatk.s34477Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientStorageTest {
    private ClientStorage clientStorage;

    @BeforeEach
    void setUp() {
        this.clientStorage = new ClientStorage();
    }

    @Test
    void addClientTest() {
        // GIVEN
        Client client = new Client(1, "Jan", "Kowalski", 100.0);
        // WHEN
        clientStorage.addClient(client);
        // THEN
        assertEquals(1, clientStorage.getClients().size());
    }

    @Test
    void addClientAddNullTest() {
        // WHEN
        clientStorage.addClient(null);
        // THEN
        assertEquals(0, clientStorage.getClients().size());
    }

    @Test
    void getClientByIDTest() {
        // GIVEN
        Client client = new Client(1, "Jan", "Kowalski", 100.0);
        clientStorage.addClient(client);
        // WHEN
        Optional<Client> result = clientStorage.getClientByID(1);
        // THEN
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void getClientByIDNotFoundTest() {
        // GIVEN
        clientStorage.addClient(new Client(1, "Jan", "Kowalski", 100.0));
        // WHEN
        Optional<Client> result = clientStorage.getClientByID(15);
        // THEN
        assertTrue(result.isEmpty());
    }

    @Test
    void removeClientRemoveClientByIdTest() {
        // GIVEN
        clientStorage.addClient(new Client(1, "Jan", "Kowalski", 100.0));
        clientStorage.addClient(new Client(2, "Anna", "Nowak", 200.0));
        // WHEN
        clientStorage.removeClient(1);
        // THEN
        assertEquals(1, clientStorage.getClients().size());
        assertTrue(clientStorage.getClientByID(1).isEmpty());
        assertTrue(clientStorage.getClientByID(2).isPresent());
    }

}