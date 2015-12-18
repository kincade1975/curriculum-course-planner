package hr.teosoft.ccp.domain.solver;

import java.io.Serializable;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import hr.teosoft.ccp.domain.Course;

/**
 * Calculated during initialization, not modified during score calculation.
 */
public class CourseConflict implements Serializable, Comparable<CourseConflict> {

	private static final long serialVersionUID = 4542925381347835186L;

	private final Course leftCourse;
	private final Course rightCourse;
	private final int conflictCount;

	public CourseConflict(Course leftCourse, Course rightCourse, int conflictCount) {
		this.leftCourse = leftCourse;
		this.rightCourse = rightCourse;
		this.conflictCount = conflictCount;
	}

	public Course getLeftCourse() {
		return leftCourse;
	}

	public Course getRightCourse() {
		return rightCourse;
	}

	public int getConflictCount() {
		return conflictCount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o instanceof CourseConflict) {
			CourseConflict other = (CourseConflict) o;
			return new EqualsBuilder()
					.append(leftCourse, other.leftCourse)
					.append(rightCourse, other.rightCourse)
					.isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(leftCourse)
				.append(rightCourse)
				.toHashCode();
	}

	@Override
	public int compareTo(CourseConflict other) {
		return new CompareToBuilder()
				.append(leftCourse, other.leftCourse)
				.append(rightCourse, other.rightCourse)
				.toComparison();
	}

	@Override
	public String toString() {
		return leftCourse + " & " + rightCourse;
	}

}
