package com.delay.pso;

public final class GBestMsg{
	final PsoValue value;

	public GBestMsg(PsoValue v){
		value = v;
	}

	public PsoValue getValue(){
		return value;
	}
}
