
package com.cengage.mt.jira.json;

import java.util.List;

public class Parent{
   	private Fields fields;
   	private String id;
   	private String key;
   	private String self;

 	public Fields getFields(){
		return this.fields;
	}
	public void setFields(Fields fields){
		this.fields = fields;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getKey(){
		return this.key;
	}
	public void setKey(String key){
		this.key = key;
	}
 	public String getSelf(){
		return this.self;
	}
	public void setSelf(String self){
		this.self = self;
	}
}
