package hr.teosoft.ccp.rest.resource;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="Teacher", description="Teacher teaching the course.")
public class TeacherResource {

	@JsonProperty("id")
	@ApiObjectField(name="id", required=false, description="Unique identifier of the teacher set and used internally by the planner.")
	private Long id;

	@JsonProperty("code")
	@ApiObjectField(name="code", required=true, description="Teacher code.")
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
		return "TeacherResource [id=" + id + ", code=" + code + "]";
	}

}
