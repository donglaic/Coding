package com.delay.pso;

import akka.actor.UntypedActor;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MasterBird extends UntypedActor{
	private final LoggingAdapter log = Logging.getLogger(getContext().system(),this);
	private PsoValue gBest=null;

	@Override
	public void onReceive(Object msg){
		if(msg instanceof PBestMsg){
			PsoValue pBest=((PBestMsg)msg).getValue();
			System.out.println("new pBest: " + msg + " by " + getSender());
			if (gBest==null || gBest.value<pBest.value){
				//update globle best,info all birds
				System.out.println(msg+"\n");
				gBest=pBest;
				ActorSelection selection=getContext().actorSelection("/user/bird_*");
				selection.tell(new GBestMsg(gBest),getSelf());
			}
		}else{
			unhandled(msg);
		}
	}
}
