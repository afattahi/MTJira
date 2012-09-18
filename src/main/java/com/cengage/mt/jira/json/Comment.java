
package com.cengage.mt.jira.json;

import java.util.List;

public class Comment{
 //  	private List<Comments> comments;
   	private Number maxResults;
   	private Number startAt;
   	private Number total;
/*
 	public List<Comments> getComments(){
		return this.comments;
	}
	public void setComments(List<Comments> comments){
		this.comments = comments;
	} */
 	public Number getMaxResults(){
		return this.maxResults;
	}
	public void setMaxResults(Number maxResults){
		this.maxResults = maxResults;
	}
 	public Number getStartAt(){
		return this.startAt;
	}
	public void setStartAt(Number startAt){
		this.startAt = startAt;
	}
 	public Number getTotal(){
		return this.total;
	}
	public void setTotal(Number total){
		this.total = total;
	}
}
