
package com.cengage.mt.jira.json;

import java.util.List;

public class Aggregateprogress{
   	private Number percent;
   	private Number progress;
   	private Number total;

 	public Number getPercent(){
		return this.percent;
	}
	public void setPercent(Number percent){
		this.percent = percent;
	}
 	public Number getProgress(){
		return this.progress;
	}
	public void setProgress(Number progress){
		this.progress = progress;
	}
 	public Number getTotal(){
		return this.total;
	}
	public void setTotal(Number total){
		this.total = total;
	}
}
