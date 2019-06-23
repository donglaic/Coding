package com.cpc;

import com.cpc.*;
import com.lmax.disruptor.*;

public class PCDataFactory implements EventFactory<PCData>{
	public PCData newInstance(){
		return new PCData();
	}
}
