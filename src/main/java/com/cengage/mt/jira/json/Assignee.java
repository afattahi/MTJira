
package com.cengage.mt.jira.json;

import java.util.List;

public class Assignee{
   	private boolean active;
  // 	private AvatarUrls avatarUrls;
   	private String displayName;
   	private String emailAddress;
   	private String name;
   	private String self;

 	public boolean getActive(){
		return this.active;
	}
	public void setActive(boolean active){
		this.active = active;
	}
 /*	public AvatarUrls getAvatarUrls(){
		return this.avatarUrls;
	}
	public void setAvatarUrls(AvatarUrls avatarUrls){
		this.avatarUrls = avatarUrls;
	}*/
	
 	public String getDisplayName(){
		return this.displayName;
	}
	public void setDisplayName(String displayName){
		this.displayName = displayName;
	}
 	public String getEmailAddress(){
		return this.emailAddress;
	}
	public void setEmailAddress(String emailAddress){
		this.emailAddress = emailAddress;
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
}
