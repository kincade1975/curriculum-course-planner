package hr.teosoft.ccp.rest.resource;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="Room", description="Each room has a capacity - expressed in terms of number of available seats and type - indicates the type of course suitable to take place in it.")
public class RoomResource {

	@JsonProperty("id")
	@ApiObjectField(name="id", required=false, description="Unique identifier of the room set and used internally by the planner.")
	private Long id;

	@JsonProperty("code")
	@ApiObjectField(name="code", required=true, description="Room code.")
	private String code;

	@JsonProperty("type")
	@ApiObjectField(name="type", required=true, description="Course type. Arbitrary value can be set (e.g. L - lecture room, C - computer room, etc).")
	private String type;

	@JsonProperty("capacity")
	@ApiObjectField(name="capacity", required=true, description="Room capacity.")
	private Integer capacity;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "RoomResource [id=" + id + ", code=" + code + ", type=" + type + ", capacity=" + capacity + "]";
	}

}
