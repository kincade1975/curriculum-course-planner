package hr.teosoft.ccp.domain.solver;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;

import hr.teosoft.ccp.domain.CourseSchedule;
import hr.teosoft.ccp.domain.Room;

public class RoomStrengthWeightFactory implements SelectionSorterWeightFactory<CourseSchedule, Room> {

	@Override
	@SuppressWarnings("rawtypes")
	public Comparable createSorterWeight(CourseSchedule schedule, Room room) {
		return new RoomStrengthWeight(room);
	}

	public static class RoomStrengthWeight implements Comparable<RoomStrengthWeight> {

		private final Room room;

		public RoomStrengthWeight(Room room) {
			this.room = room;
		}

		@Override
		public int compareTo(RoomStrengthWeight other) {
			return new CompareToBuilder()
					.append(room.getCapacity(), other.room.getCapacity())
					.append(room.getId(), other.room.getId())
					.toComparison();
		}

	}

}
