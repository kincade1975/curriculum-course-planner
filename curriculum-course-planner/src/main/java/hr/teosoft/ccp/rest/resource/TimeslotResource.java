package hr.teosoft.ccp.rest.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class TimeslotResource {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("timeslotIndex")
	private Integer timeslotIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTimeslotIndex() {
		return timeslotIndex;
	}

	public void setTimeslotIndex(Integer timeslotIndex) {
		this.timeslotIndex = timeslotIndex;
	}

	@Override
	public String toString() {
		return "TimeslotResource [id=" + id + ", timeslotIndex=" + timeslotIndex + "]";
	}

}
