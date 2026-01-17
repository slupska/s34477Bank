package pl.pjatk.s34477Bank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static pl.pjatk.s34477Bank.Status.ACCEPTED;
import static pl.pjatk.s34477Bank.Status.DECLINED;

@SpringBootTest
public class ServiceIntegrartionTest {

    @Test
    void contextLoads() {
    }

    @MockitoBean
    private ClientStorage clientStorage;

    @Autowired
    private BankService bankService;


    @Test
    void registerClientTest() {
        // WHEN
        Optional<Client> result = bankService.registerClient(1, "Jan", "Kowalski", 100.0);
        // THEN
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        assertEquals(100.0, result.get().getBalance(), 0.0001);
        assertEquals("Jan", result.get().getName());
        assertEquals("Kowalski", result.get().getSurname());
    }

    @Test
    void readClientTest() {
        // GIVEN
        Client mockClient = new Client(1, "Jan", "Kowalski", 100.0);
        when(clientStorage.getClientByID(1)).thenReturn(Optional.of(mockClient));
        // WHEN
        Optional<Client> result = bankService.readClient(1);
        // THEN
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
    }

    @Test
    void readClientNotFoundTest() {
        // GIVEN
        when(clientStorage.getClientByID(1)).thenReturn(Optional.empty());
        // WHEN
        Optional<Client> result = bankService.readClient(1);
        // THEN
        assertTrue(result.isEmpty());
    }

    @Test
    void makeTransferTest() {
        // GIVEN
        Client mockClient = new Client(1, "Jan", "Kowalski", 100.0);
        when(clientStorage.getClientByID(1)).thenReturn(Optional.of(mockClient));
        // WHEN
        TransactionResult result = bankService.makeTransfer(1, 40.0);
        // THEN
        assertEquals(ACCEPTED, result.getStatus());
        assertEquals(60.0, result.getNewBalance(), 0.0001);
        assertEquals(60.0, mockClient.getBalance(), 0.0001);
        verify(clientStorage, times(1)).getClientByID(1);
    }

    @Test
    void makeTransferClientNotFoundTest() {
        // GIVEN
        when(clientStorage.getClientByID(1)).thenReturn(Optional.empty());
        // WHEN
        TransactionResult result = bankService.makeTransfer(1, 40.0);
        // THEN
        assertEquals(DECLINED, result.getStatus());
        assertEquals(0.0, result.getNewBalance(), 0.0001);
        verify(clientStorage, times(1)).getClientByID(1);
    }

    @Test
    void depositTest() {
        // GIVEN
        Client mockClient = new Client(1, "Jan", "Kowalski", 100.0);
        when(clientStorage.getClientByID(1)).thenReturn(Optional.of(mockClient));
        // WHEN
        TransactionResult result = bankService.deposit(1, 25.5);
        // THEN
        assertEquals(ACCEPTED, result.getStatus());
        assertEquals(125.5, result.getNewBalance(), 0.0001);
        assertEquals(125.5, mockClient.getBalance(), 0.0001);
    }

    @Test
    void depositClientNotFoundTest() {
        // GIVEN
        when(clientStorage.getClientByID(1)).thenReturn(Optional.empty());
        // WHEN
        TransactionResult result = bankService.deposit(1, 25.5);
        // THEN
        assertEquals(DECLINED, result.getStatus());
        assertEquals(0.0, result.getNewBalance(), 0.0001);
    }

}
