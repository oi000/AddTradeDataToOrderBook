import java.util.ArrayList;
import java.util.List;

public class CollectTrade {
	List<Trade> lt = new ArrayList<Trade>();
	long time;

	public CollectTrade() {
		lt.clear();
		// TODO Auto-generated constructor stub
	}

	public CollectTrade(List<Trade> lr, int from, int to) {
		lt.clear();
		for (int i = from; i < to; i++)
			lt.add(lr.get(i));
		if (lt.size() != 0)
			time = lt.get(0).time;
	}
}
