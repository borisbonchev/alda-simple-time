package simpletimeimpl;

import simpletimeapi.Duration;

/**
 *
 * @author boris
 */
public class APDuration implements Duration {
    private final int minutes;

    public APDuration(int hours, int minutes) {
        this.minutes = minutes + (hours * 60);
    }

    @Override
    public Duration plus(Duration duration) {
        return new APDuration(0, this.minutes + duration.asMinutes());
    }

    @Override
    public int getHours() {
        return this.minutes / 60;
    }

    @Override
    public int getMinutes() {
        return this.minutes % 60;
    }

    @Override
    public int asMinutes() {
        return this.minutes;
    }
    
    @Override
    public int compareTo(Duration arg0) {
        return Integer.compare(asMinutes() - arg0.asMinutes(), 0);
    }

    @Override
    public String toString() {
        String hours = getHours() < 10 && getHours() != 0 ? "0" + Integer.toString(getHours()) : Integer.toString(getHours());
        String minutes = getMinutes() < 10 && getMinutes() != 0 ? "0" + Integer.toString(getMinutes()) : Integer.toString(getMinutes());

        return hours + ":" + minutes + " minutes";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.minutes;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final APDuration other = (APDuration) obj;
        return this.minutes == other.minutes;
    }
}