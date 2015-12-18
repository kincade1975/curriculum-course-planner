package hr.teosoft.ccp.domain;

import org.optaplanner.examples.common.domain.AbstractPersistable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Teacher")
public class Teacher extends AbstractPersistable {

	private static final long serialVersionUID = -4675362427475584028L;

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return code;
	}

	@Override
	public String toString() {
		return code;
	}

}
