package simpletimeimpl;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import simpletimeapi.AbstractAPFactory;
import simpletimeapi.Time;
import simpletimeapi.Duration;

/**
 *
 * @author boris
 */
public class APTimeTest {
    private AbstractAPFactory APFactory = ServiceFinder.getFactory();  


    @Test
    void testAddTime() {
        Time time1 = APFactory.createTime(7, 30);
        Time time2 = APFactory.createTime(9, 45);
        Time expectedTime = APFactory.createTime(17, 15);

        assertThat(time1.addTime(time2)).isEqualByComparingTo(expectedTime);
    }

    @Test
    void testCreateIllegalTimeObject() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
            APFactory.createTime(0, -2);
                });
    }
    
    @Test
    void testAddTimeOnlyMinutes() {
        Time time1 = APFactory.createTime(7, 30);
        Time expectedTime = APFactory.createTime(8, 35);

        assertThat(time1.addTime(65)).isEqualByComparingTo(expectedTime);
    }

    @Test
    void testGetHoursAndGetMinutes() {
        Time time1 = APFactory.createTime(7, 30);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(time1.getHours()).isEqualTo(7);
            softly.assertThat(time1.getMinutes()).isEqualTo(30);
        });
    }

    @ParameterizedTest
    @CsvSource({
        //time1Hours, time1Minutes, time2Hours, time2Minutes, expectedHours, expectedMinutes
        "7,30,8,30,1,0",
        "8,30,12,15,3,45",
        "0,15,2,15,2,0",
        "12,0,19,15,7,15",
        "22,15,0,30,2,15"
    })
    void testUntil(int hours1, int minutes1, int hours2, int minutes2, int expectedHours, int expectedMinutes) {
        Time time1 = APFactory.createTime(hours1, minutes1);
        Time time2 = APFactory.createTime(hours2, minutes2);
        Duration expectedDuration = APFactory.createDuration(expectedHours, expectedMinutes);

        assertThat(time1.until(time2)).isEqualByComparingTo(expectedDuration);
    }

    @Test
    void testCompareTo() {
        Time time1 = APFactory.createTime(12, 30);
        Time time2 = APFactory.createTime(14, 25);
        Time time3 = APFactory.createTime(7, 25);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(time1.compareTo(time2)).isEqualTo(-1);
            softly.assertThat(time2.compareTo(time1)).isEqualTo(1);
            softly.assertThat(time1.compareTo(time3)).isEqualTo(1);
        });
    }

    @Test
    void testToString() {
        assertThat(APFactory.createTime(5, 20).toString()).isEqualTo("05:20");
        assertThat(APFactory.createTime(0, 59).toString()).isEqualTo("0:59");
    }

    @Test
    void testEquals() {
        Time time1 = APFactory.createTime(4, 20);
        Time time2 = APFactory.createTime(4, 20);
        Time time3 = APFactory.createTime(23, 30);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(time1.equals(time2)).isTrue();
            softly.assertThat(time1.equals(time3)).isFalse();
            softly.assertThat(time1.equals("24")).isFalse();
            softly.assertThat(time1.equals(null)).isFalse();
            softly.assertThat(time1.equals(time1)).isTrue();
        });
    }

    @Test
    void testHashCode() {
        Time time1 = APFactory.createTime(4,20);
        Time time2 = APFactory.createTime(4,20);

        assertThat(time1.equals(time2)).isTrue();
        assertThat(time1.hashCode() == time2.hashCode()).isTrue();
    }
}
