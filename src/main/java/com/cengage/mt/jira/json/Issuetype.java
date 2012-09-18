
package com.cengage.mt.jira.json;

import java.util.List;

public class Issuetype{
   	private String description;
   	private String iconUrl;
   	private String id;
   	private String name;
   	private String self;
   	private boolean subtask;

 	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
 	public String getIconUrl(){
		return this.iconUrl;
	}
	public void setIconUrl(String iconUrl){
		this.iconUrl = iconUrl;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public String getSelf(){
		return this.self;
	}
	public void setSelf(String self){
		this.self = self;
	}
 	public boolean getSubtask(){
		return this.subtask;
	}
	public void setSubtask(boolean subtask){
		this.subtask = subtask;
	}
}
