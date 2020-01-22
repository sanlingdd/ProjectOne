package com.linkedin.automation;

public class MessageSendRecord {
	private String profile;
	private Long lastMessageSendTime;
	
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Long getLastMessageSendTime() {
		return lastMessageSendTime;
	}
	public void setLastMessageSendTime(Long lastMessageSendTime) {
		this.lastMessageSendTime = lastMessageSendTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageSendRecord other = (MessageSendRecord) obj;
		if (profile == null) {
			if (other.profile != null)
				return false;
		} else if (!profile.equals(other.profile))
			return false;
		return true;
	}
	
}
