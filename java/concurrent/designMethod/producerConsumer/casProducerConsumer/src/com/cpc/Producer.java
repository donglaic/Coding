package com.cpc;

import com.lmax.disruptor.*;
import com.cpc.*;
import java.nio.*;

public class Producer{
	private final RingBuffer<PCData> ringBuffer;

	public Producer(RingBuffer<PCData> ringBuffer){
		this.ringBuffer = ringBuffer;
	}

	public void pushData(ByteBuffer bb){
		long sequence = ringBuffer.next(); // Grab the next sequence
		try {
			PCData event = ringBuffer.get(sequence); //GEt the entry in the Disruptor for the sequence
			event.set(bb.getLong(0)); // Fill with data
		}finally{
			ringBuffer.publish(sequence);
		}
	}
}
