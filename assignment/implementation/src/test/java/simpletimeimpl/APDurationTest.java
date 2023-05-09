package simpletimeimpl;
import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import simpletimeapi.AbstractAPFactory;
import simpletimeapi.Duration;

/**
 *
 * @author boris
 */
public class APDurationTest {
    private AbstractAPFactory APFactory = ServiceFinder.getFactory();

    @ParameterizedTest
    @CsvSource({
        //
        "2,30,4,30,7,0",
        "0,0,0,0,0,0",
        "0,30,1,35,2,5",
        "3,0,2,35,5,35",
        "12,30,10,35,23,5"
    })
    void testPlus(int hours1, int minutes1, int hours2, int minutes2, int expectedHours, int expectedMinutes) {
        Duration dur1 = APFactory.createDuration(hours1, minutes1);
        Duration dur2 = APFactory.createDuration(hours2, minutes2);
        Duration expected = APFactory.createDuration(expectedHours, expectedMinutes);

        assertThat(dur1.plus(dur2)).isEqualByComparingTo(expected);
    }

    @Test
    void testGettersWithHoursAndMinutes() {
        Duration dur1 = APFactory.createDuration(2, 30);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dur1.getHours()).isEqualTo(2);
            softly.assertThat(dur1.getMinutes()).isEqualTo(30);
        });
    }

    @Test
    void testAsMinutes() {
        Duration dur1 = APFactory.createDuration(4, 20);
        assertThat(dur1.asMinutes()).isEqualTo(260);
    }

    @Test
    void testCompareTo() {
        Duration dur1 = APFactory.createDuration(5, 30);
        Duration dur2 = APFactory.createDuration(7, 20);
        Duration dur3 = APFactory.createDuration(2, 0);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dur1.compareTo(dur2)).isEqualTo(-1);
            softly.assertThat(dur2.compareTo(dur3)).isEqualTo(1);
            softly.assertThat(dur1.compareTo(dur3)).isEqualTo(1);
        });
    }

    @Test
    void testToString() {
        assertThat(APFactory.createDuration(5, 30).toString()).isEqualTo("05:30 minutes");
        assertThat(APFactory.createDuration(0, 59).toString()).isEqualTo("0:59 minutes");
    }
    
    @Test
    void testHashCode() {
        Duration dur1 = APFactory.createDuration(4,20);
        Duration dur2 = APFactory.createDuration(4,20);
        
        assertThat(dur1.equals(dur2)).isTrue();
        assertThat(dur1.hashCode() == dur2.hashCode()).isTrue();
    }
    
    @Test
    void testEquals() {
        Duration dur1 = APFactory.createDuration(4,20);
        Duration dur2 = APFactory.createDuration(4,20);
        Duration dur3 = APFactory.createDuration(22,20);
        
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(dur1.equals(dur2)).isTrue();
            softly.assertThat(dur1.equals(dur3)).isFalse();
            softly.assertThat(dur1.equals("24")).isFalse();
            softly.assertThat(dur1.equals(null)).isFalse();
            softly.assertThat(dur1.equals(dur1)).isTrue();
        });
    }
}
