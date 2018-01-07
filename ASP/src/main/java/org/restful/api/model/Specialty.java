package org.restful.api.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Specialty {
	
	private  List<String> specialtyList = new ArrayList<>();

	public List<String> getSpecialtyList() {
		return specialtyList;
	}

	public void setSpecialtyList(List<String> specialtyList) {
		this.specialtyList = specialtyList;
	}	


}
