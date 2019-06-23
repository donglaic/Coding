package com.delay;
import akka.actor.*;
public class WatchActor extends UntypedActor{
	//private final LoggingAdapter log = Logging.getLogger(getContext().system(),this);

	public WatchActor(ActorRef ref){
		getContext().watch(ref);
	}

	@Override
	public void onReceive(Object msg){
		if(msg instanceof Terminated){
			System.out.println(String.format("%s has terminated,shutting down system",((Terminated)msg).getActor().path()));
			getContext().system().terminate();
		}else{
			unhandled(msg);
		}
	}
}
