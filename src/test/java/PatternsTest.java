import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PatternsTest {

    @Test
    void shouldTest() {
        open("http://localhost:9999/");
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        int firstMeetingDay = 4;
        String firstDay = DataGenerator.generateDate(firstMeetingDay);
        int secondMeetingDay = 7;
        String secondDay = DataGenerator.generateDate(secondMeetingDay);
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstDay);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__title")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.text("Успешно!"));
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String planningDate = secondDay;
        $("[data-test-id='date'] input").setValue(secondDay);
        $(".button").click();
        $("[data-test-id='replan-notification'] span.button__text").click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate));

    }
}


