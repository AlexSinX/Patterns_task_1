package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {
    private DataGenerator.UserInfo userInfo;

    @BeforeEach
    void setUp() {
        userInfo = DataGenerator.Registration.generateUser("ru");
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessPlanDelivery() {
        String firstDate = DataGenerator.generateDate(3);
        String secondDate = DataGenerator.generateDate(7);
    }
}