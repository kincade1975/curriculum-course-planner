package hr.teosoft.ccp.rest.resource;

import java.util.ArrayList;
import java.util.List;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
@ApiObject(name="Score", description="Score of the planning process (HardSoftScore). It also contains list of unsatisfied contraints.")
public class ScoreResource {

	@JsonProperty("score")
	@ApiObjectField(name="score", required=true, description="Score of the planning process (HardSoftScore).")
	private String score;

	@JsonProperty("unsatisfiedConstraints")
	@ApiObjectField(name="unsatisfiedConstraints", required=true, description="List of unsatisfied contraints.")
	private List<UnsatisfiedConstraintResource> unsatisfiedConstraints = new ArrayList<>();

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<UnsatisfiedConstraintResource> getUnsatisfiedConstraints() {
		return unsatisfiedConstraints;
	}

	public void setUnsatisfiedConstraints(List<UnsatisfiedConstraintResource> unsatisfiedConstraints) {
		this.unsatisfiedConstraints = unsatisfiedConstraints;
	}

	@Override
	public String toString() {
		return "ScoreResource [score=" + score + ", unsatisfiedConstraints=" + unsatisfiedConstraints + "]";
	}

}
