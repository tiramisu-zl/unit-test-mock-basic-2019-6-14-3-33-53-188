package cashregister;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CashRegisterTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private String systemOut() {
        return outContent.toString();
    }

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {
        //given
        Printer printer = new Printer();
        CashRegister cashRegister = new CashRegister(printer);
        Item item = new Item("name", 1.01);
        Item[] items = new Item[]{item};
        Purchase purchase = new Purchase(items);

        //when
        cashRegister.process(purchase);

        //then
        assertThat(systemOut()).isEqualTo("name\t1.01\n");
    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {
        //given
        Printer printer = new Printer();
        CashRegister cashRegister = new CashRegister(printer);
        Purchase purchase = mock(Purchase.class);

        //when
        when(purchase.asString()).thenReturn("mock purchase");
        cashRegister.process(purchase);

        //then
        assertThat(systemOut()).isEqualTo("mock purchase");
    }

    @Test
    public void should_verify_with_process_call_with_mockito() {
        //given
        CashRegister mockCashRegister = mock(CashRegister.class);
        Item item = new Item("name", 1.00);
        Item[] items = new Item[]{item};
        Purchase purchase = new Purchase(items);

        //when
        mockCashRegister.process(purchase);

        //then
        verify(mockCashRegister).process(purchase);
    }

}
