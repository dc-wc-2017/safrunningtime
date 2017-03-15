package com.successfactors.safrunningtime.output;

import java.util.ArrayList;
import java.util.List;

public class Results {
	
	private List<Result> results = new ArrayList<Result>();

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
	
	public void addResult(Result result) {
		results.add(result);
	}	

}
