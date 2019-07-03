package com.delay.pso;

public final class PBestMsg{
	final PsoValue value;

	public PBestMsg(PsoValue v){
		value = v;
	}

	public PsoValue getValue(){
		return value;
	}

	public String toString(){
		return value.toString();
	}
}
