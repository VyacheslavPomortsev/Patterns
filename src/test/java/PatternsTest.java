import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PatternsTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

        @Test
        void shouldTest () {
            open("http://localhost:9999/");
            $("[data-test-id='city'] input").setValue("Хабаровск");
            $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
            $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
            $("[data-test-id='name'] input").setValue("Петров Петр");
            $("[data-test-id='phone'] input").setValue("+79990009900");
            $("[data-test-id='agreement']").click();
            $(".button").click();
            $(".notification__title")
                    .shouldBe(Condition.visible, Duration.ofSeconds(15))
                    .shouldBe(Condition.text("Успешно!"));

        }

        @Test
        void shouldTestSecondTime () {
            open("http://localhost:9999/");
            $("[data-test-id='city'] input").setValue("Хабаровск");
            $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
            String planningDate = generateDate(3, "dd.MM.yyyy");
            $("[data-test-id='date'] input").setValue(generateDate(3, "dd.MM.yyyy"));
            $("[data-test-id='name'] input").setValue("Петров Петр");
            $("[data-test-id='phone'] input").setValue("+79990009900");
            $("[data-test-id='agreement']").click();
            $(".button").click();
            $("[data-test-id='replan-notification']")
                   .shouldBe(Condition.text("Необходимо подтверждение"));
            $("button .button__content").click();
            $(".notification__content")
                    .shouldHave(Condition.text("Встреча успешно запланирована на " + planningDate));


        }
    }


