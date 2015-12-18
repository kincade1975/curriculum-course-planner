package hr.teosoft.ccp.domain.solver;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;

import hr.teosoft.ccp.domain.CourseSchedule;
import hr.teosoft.ccp.domain.Period;
import hr.teosoft.ccp.domain.UnavailablePeriodPenalty;

public class PeriodStrengthWeightFactory implements SelectionSorterWeightFactory<CourseSchedule, Period> {

	@SuppressWarnings("rawtypes")
	@Override
	public Comparable createSorterWeight(CourseSchedule schedule, Period period) {
		int unavailablePeriodPenaltyCount = 0;
		for (UnavailablePeriodPenalty penalty : schedule.getUnavailablePeriodPenaltyList()) {
			if (penalty.getPeriod().equals(period)) {
				unavailablePeriodPenaltyCount++;
			}
		}
		return new PeriodStrengthWeight(period, unavailablePeriodPenaltyCount);
	}

	public static class PeriodStrengthWeight implements Comparable<PeriodStrengthWeight> {

		private final Period period;
		private final int unavailablePeriodPenaltyCount;

		public PeriodStrengthWeight(Period period, int unavailablePeriodPenaltyCount) {
			this.period = period;
			this.unavailablePeriodPenaltyCount = unavailablePeriodPenaltyCount;
		}

		@Override
		public int compareTo(PeriodStrengthWeight other) {
			return new CompareToBuilder()
					// The higher unavailablePeriodPenaltyCount, the weaker
					.append(other.unavailablePeriodPenaltyCount, unavailablePeriodPenaltyCount) // Descending
					.append(period.getDay().getDayIndex(), other.period.getDay().getDayIndex())
					.append(period.getTimeslot().getTimeslotIndex(), other.period.getTimeslot().getTimeslotIndex())
					.append(period.getId(), other.period.getId())
					.toComparison();
		}

	}

}
