package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {
    private DataGenerator.UserInfo userInfo;

    @BeforeEach
    void setUp() {
        userInfo = DataGenerator.Registration.generateUser("ru");
        open("http://localhost:9999");
    }

    @Test
    void shouldPlanAndRescheduleDelivery() throws InterruptedException {
        String firstDate = DataGenerator.generateDate(3);
        String secondDate = DataGenerator.generateDate(7);

        // Планирование первой встречи
        $("[data-test-id=city] input").setValue(userInfo.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstDate);
        $("[data-test-id=name] input").setValue(userInfo.getName());
        $("[data-test-id=phone] input").setValue(userInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $$("button").findBy(Condition.exactText("Забронировать")).click();

        Thread.sleep(2000);
        // Проверка уведомления о первой встрече
        $("[data-test-id=success-notification]").shouldBe(Condition.visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstDate));

        // Перепланирование на новую дату
        $("[data-test-id=date] input").doubleClick().sendKeys(org.openqa.selenium.Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondDate);
        $$("button").findBy(Condition.exactText("Забронировать")).click();

        Thread.sleep(1500);
        // Диалог подтверждения перепланирования
        $(withText("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible);
        $$("button").findBy(Condition.exactText("Перепланировать")).click();

        Thread.sleep(2000);
        // Проверка уведомления после перепланирования
        $("[data-test-id=success-notification]").shouldBe(Condition.visible);
        $("[data-test-id=success-notification] .notification__content")
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondDate));
    }
}