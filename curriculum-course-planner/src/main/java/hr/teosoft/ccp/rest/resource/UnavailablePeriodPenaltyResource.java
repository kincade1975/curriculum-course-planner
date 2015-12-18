package hr.teosoft.ccp.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class UnavailablePeriodPenaltyResource {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("courseCode")
	private String courseCode;

	@JsonProperty("periodCode")
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
