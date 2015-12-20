package hr.teosoft.ccp.rest.resource;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="Period", description="We are given a number of teaching days in the week (typically 5 or 6). Each day is split in a fixed number of time-slots, which is equal for all days. A period is a pair composed of a day and a time-slot. The total number of scheduling periods is the product of the days times the day time-slots.")
public class PeriodResource {

	@JsonProperty("id")
	@ApiObjectField(name="id", required=false, description="Unique identifier of the period set and used internally by the planner.")
	private Long id;

	@JsonProperty("code")
	@ApiObjectField(name="code", required=true, description="Period code is combination of day index and timeslot index concatenated by '-' character (e.g. 0-1 represents Monday 08:00).")
	private String code;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "PeriodResource [id=" + id + ", code=" + code + "]";
	}

}
