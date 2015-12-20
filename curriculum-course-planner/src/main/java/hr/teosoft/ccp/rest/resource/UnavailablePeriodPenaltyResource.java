package hr.teosoft.ccp.rest.resource;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="UnavailablePeriodPenalty", description="Unavailable period penalty.")
public class UnavailablePeriodPenaltyResource {

	@JsonProperty("id")
	@ApiObjectField(name="id", required=false, description="Unique identifier of the unavailable period penalty set and used internally by the planner.")
	private Long id;

	@JsonProperty("courseCode")
	@ApiObjectField(name="courseCode", required=true, description="Course code.")
	private String courseCode;

	@JsonProperty("periodCode")
	@ApiObjectField(name="periodCode", required=true, description="Period code. Period code is combination of day index and timeslot index concatenated by '-' character (e.g. 0-1 represents Monday 08:00).")
	private String periodCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getPeriodCode() {
		return periodCode;
	}

	public void setPeriodCode(String periodCode) {
		this.periodCode = periodCode;
	}

	@Override
	public String toString() {
		return "UnavailablePeriodPenaltyResource [id=" + id + ", courseCode=" + courseCode + ", periodCode="
				+ periodCode + "]";
	}

}
