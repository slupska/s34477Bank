package pl.pjatk.s34477Bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static pl.pjatk.s34477Bank.Status.*;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    @Mock
    private ClientStorage clientStorage;

    @InjectMocks
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