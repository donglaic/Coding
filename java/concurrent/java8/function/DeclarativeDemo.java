import java.util.*;
import java.util.function.*;

public class DeclarativeDemo{
	static int[] iArr = {1,2,3,4,5};

	public static void main(String[] args){
		IntConsumer outprintln=System.out::println;
		IntConsumer errprintln=System.out::println;
		Arrays.stream(iArr).forEach(outprintln.andThen(errprintln));
	}
}
