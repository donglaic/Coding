package com.delay;

import akka.actor.*;
import java.time.*;
import java.util.concurrent.*;
import scala.concurrent.duration.Duration;
import akka.actor.SupervisorStrategy.Directive;
import akka.japi.Function;

public class Supervisor extends UntypedActor{
	private static SupervisorStrategy strategy = new OneForOneStrategy(3,Duration.create(1,TimeUnit.MINUTES),
		new Function<Throwable,Directive>(){
			@Override
			public Directive apply(Throwable t){
				if(t instanceof ArithmeticException){
					System.out.println("meet ArithmeticException, just resume");
					return SupervisorStrategy.resume();
				}else if(t instanceof NullPointerException){
					System.out.println("meet NullPointerException, restart");
					return SupervisorStrategy.restart();
				}else if(t instanceof IllegalArgumentException){
					return SupervisorStrategy.stop();
				}else{
					return SupervisorStrategy.escalate();
				}
			}
		});

	@Override
	public SupervisorStrategy supervisorStrategy (){
		return strategy;
	}

	public void onReceive(Object o){
		if (o instanceof Props){
			getContext().actorOf((Props)o,"restartActor");
		}else{
			unhandled(o);
		}
	}
}
