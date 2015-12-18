package hr.teosoft.ccp.domain;

import org.optaplanner.examples.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * We are given a number of teaching days in the week (typically 5 or 6).
 * Each day is split in a fixed number of time-slots, which is equal for all days.
 * A period is a pair composed of a day and a time-slot. The total number of scheduling
 * periods is the product of the days times the day time-slots.
 */
@XStreamAlias("Timeslot")
public class Timeslot extends AbstractPersistable {

	private static final long serialVersionUID = 7366775807088476786L;

	private static final String[] TIMES = {"07:15", "08:00", "08:45", "09:30", "10:30", "11:15", "12:15", "13:00", "14:00", "14:45", "15:45", "16:30", "17:30", "18:15", "19:15"};

	private int timeslotIndex;

	public int getTimeslotIndex() {
		return timeslotIndex;
	}

	public void setTimeslotIndex(int timeslotIndex) {
		this.timeslotIndex = timeslotIndex;
	}

	public String getLabel() {
		String time = TIMES[timeslotIndex % TIMES.length];
		if (timeslotIndex > TIMES.length) {
			return "Timeslot " + timeslotIndex;
		}
		return time;
	}

	@Override
	public String toString() {
		return Integer.toString(timeslotIndex);
	}

}
