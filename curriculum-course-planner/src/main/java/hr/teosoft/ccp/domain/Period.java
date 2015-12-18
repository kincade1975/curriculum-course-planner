package hr.teosoft.ccp.domain;

import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.examples.common.swingui.components.Labeled;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * We are given a number of teaching days in the week (typically 5 or 6).
 * Each day is split in a fixed number of time-slots, which is equal for all days.
 * A period is a pair composed of a day and a time-slot. The total number of scheduling
 * periods is the product of the days times the day time-slots.
 */
@XStreamAlias("Period")
public class Period extends AbstractPersistable implements Labeled {

	private static final long serialVersionUID = -3547916299394987415L;

	private Day day;
	private Timeslot timeslot;

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public Timeslot getTimeslot() {
		return timeslot;
	}

	public void setTimeslot(Timeslot timeslot) {
		this.timeslot = timeslot;
	}

	@Override
	public String getLabel() {
		return day.getLabel() + " " + timeslot.getLabel();
	}

	@Override
	public String toString() {
		return day + "-" + timeslot;
	}

}
