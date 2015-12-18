package hr.teosoft.ccp.domain;

import java.util.List;

import org.optaplanner.examples.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * We are given a number of teaching days in the week (typically 5 or 6).
 * Each day is split in a fixed number of time-slots, which is equal for all days.
 * A period is a pair composed of a day and a time-slot. The total number of scheduling
 * periods is the product of the days times the day time-slots.
 */
@XStreamAlias("Day")
public class Day extends AbstractPersistable {

	private static final long serialVersionUID = -8882309610557753683L;

	private static final String[] WEEKDAYS = {"Mo", "Tu", "We", "Th", "Fr", "Sat", "Sun"};

	private int dayIndex;

	private List<Period> periodList;

	public int getDayIndex() {
		return dayIndex;
	}

	public void setDayIndex(int dayIndex) {
		this.dayIndex = dayIndex;
	}

	public List<Period> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<Period> periodList) {
		this.periodList = periodList;
	}

	public String getLabel() {
		String weekday = WEEKDAYS[dayIndex % WEEKDAYS.length];
		if (dayIndex > WEEKDAYS.length) {
			return "Day " + dayIndex;
		}
		return weekday;
	}

	@Override
	public String toString() {
		return Integer.toString(dayIndex);
	}

}
