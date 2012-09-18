
package com.cengage.mt.jira.json;

import java.util.List;

public class FixVersions{
   	private boolean archived;
   	private String id;
   	private String name;
   	private boolean released;
   	private String self;

 	public boolean getArchived(){
		return this.archived;
	}
	public void setArchived(boolean archived){
		this.archived = archived;
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
 	public boolean getReleased(){
		return this.released;
	}
	public void setReleased(boolean released){
		this.released = released;
	}
 	public String getSelf(){
		return this.self;
	}
	public void setSelf(String self){
		this.self = self;
	}
}
