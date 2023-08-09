import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {


    String city = "Москва";
    String name = "Анна Блашонкова";
    String phone = "+70000000000";
    int plusDays = 4;
    String currentDate = generateDate(plusDays, "dd.MM.yyyy");

    @BeforeEach
    void setupUp() {
        open("http://localhost:9999/");
    }

    public String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }
    @Test
    public void shouldTestForm() {
            $("[data-test-id=city] input").setValue("Москва");
            $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
            $("[data-test-id=date] input").setValue(currentDate);
            $("[data-test-id=name] input").setValue("Анна Блашонкова");
            $("[data-test-id=phone] input").setValue("+70000000000");
            $("[data-test-id=agreement]").click();
            $("button.button").click();
            $(".notification__content")
                    .shouldBe(Condition.visible, Duration.ofSeconds(15))
                    .shouldBe(Condition.exactText("Встреча успешно забронирована на " + currentDate));
    }
    @Test
    public void shouldTestEmptyForm() {
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("");
        $("button.button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }
    @Test
    public void shouldTestFormWithInvalidCityV1() {
        $("[data-test-id=city] input").setValue("Подольск");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue("Анна Блашонкова");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("button.button").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }
    @Test
    public void shouldTestFormWithInvalidName() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue("dfhfgjj");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("button.button").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);
    }
    @Test
    public void shouldTestFormWithInvalidName2() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue("436347");
        $("[data-test-id=phone] input").setValue("+70000000000");
        $("button.button").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(Condition.visible);
    }
    @Test
    public void shouldTestFormWithInvalidPhone() {
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue("Анна Блашонкова");
        $("[data-test-id=phone] input").setValue("+7000000000");
        $("button.button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }
    @Test
    public void shouldTestFormWithEmptyPhone() {
        $("[data-test-id=city] input").setValue(city);
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(currentDate);
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue("");
        $("button.button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }
}
