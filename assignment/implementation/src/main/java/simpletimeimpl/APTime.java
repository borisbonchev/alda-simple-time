package simpletimeimpl;
import simpletimeapi.Duration;
import simpletimeapi.Time;

import java.util.Objects;

/**
 *
 * @author boris
 */
public class APTime implements Time {
    private final int minutes;
    
    public APTime(int hours, int minutes) {
        this.minutes = minutes + (hours * 60);


        int hoursPlusMinutes = this.minutes;

        if (hoursPlusMinutes >= (24 * 60)) throw new IllegalArgumentException("Time cannot have more than 1440 minutes!");
        if (hoursPlusMinutes < 0) throw new IllegalArgumentException("Negative minutes are not allowed!");
    }
    
    @Override
    public Time addTime(Time t) {
        int minutes = (asMinutes() + t.asMinutes()) % (24 * 60);

        return new APTime(0, minutes);
    }

    @Override
    public Time addTime(int minutes) {
        return new APTime(0, (asMinutes() + minutes) % (24 * 60));
    }

    @Override
    public int getHours() {
        int hours = this.minutes / 60;
        return hours;
    }

    @Override
    public int getMinutes() {
        return this.minutes % 60;
    }

    @Override
    public Duration until(Time other) {
        if (this.compareTo(other) > 0) {
            return new APDuration(0, 1440 - (asMinutes() - other.asMinutes()));
        } else {
            return new APDuration(0, other.asMinutes() - asMinutes());
        }
    }

    @Override
    public int compareTo(Time o) {
        return Integer.compare(asMinutes() - o.asMinutes(), 0);
    }

    @Override
    public String toString() {
        String hours = getHours() < 10 && getHours() != 0 ? "0" + Integer.toString(getHours()) : Integer.toString(getHours());
        String minutes = getMinutes() < 10 && getMinutes() != 0 ? "0" + Integer.toString(getMinutes()) : Integer.toString(getMinutes());

        return hours + ":" + minutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        APTime apTime = (APTime) o;
        return minutes == apTime.minutes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minutes);
    }
}
