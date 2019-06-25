package com.delay;

import akka.actor.UntypedActor;
import akka.dispatch.Mapper;
import scala.concurrent.Future;

public class CounterActor extends UntypedActor{
	Mapper addMapper = new Mapper<Integer,Integer>(){
		@Override
		public Integer apply(Integer i){
			return i+1;
		}
	};

	@Override
	public void onReceive(Object msg){
		if(msg instanceof Integer){
			for(int i=0;i<100;i++){
				Future<Integer> f = AgentDemo.counterAgent.alter(addMapper);
				AgentDemo.futures.add(f);
			}
		}else{
			unhandled(msg);
		}
	}
}
