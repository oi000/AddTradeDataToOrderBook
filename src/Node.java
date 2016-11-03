
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Node {
	public long time;
	public List<TK> data = new ArrayList<TK>();
	public List<TK> ask = new ArrayList<TK>();
	public List<TK> bid = new ArrayList<TK>();
	public int index;
	public double t1, t2, tmin1, tmax1, tmin2, tmax2;
	public TK trade = null;

	Node() {
	}

	Node(String[] pt, CollectTrade qt) {
		time = Long.valueOf(pt[0]).longValue();

		List<TK> tmp1 = new ArrayList<TK>();
		List<TK> tmp2 = new ArrayList<TK>();
		for (int i = 2; i < pt.length; i += 4) {
			tmp1.add(new TK(Double.valueOf(pt[i]), Double.valueOf(pt[i + 1])));
		}
		for (int i = 4; i < pt.length; i += 4)
			tmp2.add(new TK(Double.valueOf(pt[i]), Double.valueOf(pt[i + 1])));
		for (int i = tmp2.size() - 1; i >= 0; i--)
			data.add(tmp2.get(i));
		process(tmp1, qt, ask);
		process(tmp2, qt, bid);
	}

	public void process(List<TK> tmp, CollectTrade q, List<TK> ret) {
		for (int i = 0; i < q.lt.size(); i++)
			tmp.add(new TK(q.lt.get(i).price, q.lt.get(i).amount));
		Collections.sort(tmp, new Comparator<TK>() {
			@Override
			public int compare(TK arg0, TK arg1) {
				if (arg0.t1 < arg1.t1)
					return -1;
				if (arg0.t1 > arg1.t2)
					return 1;
				return 0;
			}
		});
		ret.clear();
		double tot = 0;
		for (int i = 1; i < tmp.size(); i++) {
			if (tmp.get(i).t1 != tmp.get(i - 1).t1) {
				ret.add(new TK(tmp.get(i - 1).t1, tot));
				tot = tmp.get(i).t2;
			} else
				tot += tmp.get(i).t2;
		}
		ret.add(new TK(tmp.get(tmp.size() - 1).t1, tot));
	}

	String formatOutput() {
		String str = "";
		for (int i = bid.size() - 1, j = 0; i >= 0 && j < bid.size(); i--, j++) {
			str = str + "\t" + bid.get(i).t1 + "\t" + bid.get(i).t2 + "\t" + ask.get(j).t1 + "\t" + ask.get(j).t2;
		}
		return str;
	}

	public void initTrade(TK tmp) {
		this.trade = tmp;
	}

	public String toString() {
		String str = "";
		str = "[" + t1 + ", " + t2 + "], ";
		for (int i = 0; i < data.size(); i++)
			str = str + "(" + data.get(i).t1 + ", " + data.get(i).t2 + "), ";
		return str;
	}
}
