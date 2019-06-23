package com.cpc;

import com.lmax.disruptor.*;

public class Consumer implements WorkHandler<PCData>{
	@Override
	public void onEvent(PCData event) throws Exception {
		System.out.println(Thread.currentThread().getId()+":Event:--"+event.get()*event.get()+"--");
	}
}
