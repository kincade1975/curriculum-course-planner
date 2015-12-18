package hr.teosoft.ccp.rest.resource;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ScoreResource {

	@JsonProperty("score")
	private String score;

	@JsonProperty("unsatisfiedConstraints")
	private List<UnsatisfiedConstraint> unsatisfiedConstraints = new ArrayList<>();

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public List<UnsatisfiedConstraint> getUnsatisfiedConstraints() {
		return unsatisfiedConstraints;
	}

	public void setUnsatisfiedConstraints(List<UnsatisfiedConstraint> unsatisfiedConstraints) {
		this.unsatisfiedConstraints = unsatisfiedConstraints;
	}

	@Override
	public String toString() {
		return "ScoreResource [score=" + score + ", unsatisfiedConstraints=" + unsatisfiedConstraints + "]";
	}

}
