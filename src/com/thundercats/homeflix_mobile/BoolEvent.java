package com.thundercats.homeflix_mobile;

public class BoolEvent {
	//Deprecated (that was pretty fast, eh?)
	
	private boolean bool, last, init = true;
	
	public BoolEvent(){
	}
	
	public void update(boolean bool){
		this.bool = bool;
		if(init){
			last = bool;
			init = false;
		}
	}
	
	public boolean switchOn(){
		boolean temp = last;
		last = bool;
		return bool && !temp;
	}
	
	public boolean switchOff(){
		boolean temp = last;
		last = bool;
		return !bool && temp;
	}
}
