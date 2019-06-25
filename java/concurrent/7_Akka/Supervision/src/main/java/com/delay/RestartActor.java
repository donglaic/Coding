package com.delay;
import akka.actor.*;
import akka.japi.*;
public class RestartActor extends UntypedActor{
	public enum Msg{
		DONE,RESTART
	}

	@Override
	public void preStart(){
		System.out.println("preStart hashcode:"+this.hashCode());
	}

	@Override
	public void postStop(){
		System.out.println("postStop hashcode:"+this.hashCode());
	}

	@Override
	public void postRestart(Throwable reason) throws Exception{
		super.postRestart(reason);
		System.out.println("postRestart hashcode:"+this.hashCode());
	}


/*
	@Override
	public void preRestart(Throwable reason,Option opt) throws Exception{
		System.out.println("preRestart hashcode:"+this.hashCode());
	}
*/
	
	@Override
	public void onReceive(Object msg){
		if (msg == Msg.DONE){
			getContext().stop(getSelf());
		}else if (msg == Msg.RESTART){
			System.out.println(((Object)null).toString());
			double a = 0/0;
		}
		unhandled(msg);
	}
}
