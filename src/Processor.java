import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class Processor {

	Config config = null;
	ComboPooledDataSource dataSource = null;
	HashMap<Long, Integer> hm = new HashMap<Long, Integer>();
	OutputFile output = new OutputFile();

	Processor(Config config) {
		this.config = config;
	}

	void run() {
		init();
		process();
	}

	void init() {
		initDataSource();
	}

	void process() {
		List<CollectTrade> lct = new ArrayList<CollectTrade>();
		readDataFromMysql(lct);
		read("333.txt", lct);
	}

	void readDataFromMysql(List<CollectTrade> lct) {
		MysqlFromPool mfp = new MysqlFromPool(config, dataSource);
		List<Trade> lt = new ArrayList<Trade>();
		mfp.getTrade(lt);
		int pre = 0;
		for (int i = 0; i < lt.size(); i++) {
			if (lt.get(i).time != lt.get(i - 1).time) {
				CollectTrade ct = new CollectTrade(lt, pre, i);
				hm.put(ct.time, lct.size());
				lct.add(ct);
				pre = i;
			}
		}
		CollectTrade ct = new CollectTrade(lt, pre, lt.size());
		hm.put(ct.time, lct.size());
		lct.add(ct);
	}

	void read(String file_name, List<CollectTrade> lct) {
		InputFile input = new InputFile();
		input.setFileName(file_name);
		input.openFile();
		String line = null;
		while ((line = input.read()) != null) {
			String pt[] = line.split("\t");
			if (!hm.containsKey(Long.valueOf(pt[0]))) {
				output.write(line + "\n");
			} else {
				Node nd = new Node(pt, lct.get(hm.get(Long.valueOf(pt[0]))));
				output.write(pt[0] + "\t" + pt[1] + "\t" + nd.formatOutput() + "\n");
			}
		}
		output.closeFile();
		input.closeFile();
	}

	public boolean initDataSource() {
		try {
			dataSource = new ComboPooledDataSource();
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
			dataSource.setJdbcUrl(
					"jdbc:mysql://" + config.mysql_ip + ":" + config.mysql_port + "/" + config.mysql_db_name);
			dataSource.setUser(config.mysql_user_name);
			dataSource.setPassword(config.mysql_password);
			dataSource.setMaxIdleTime(config.c3p0_max_idle_time);
			dataSource.setIdleConnectionTestPeriod(config.c3p0_idle_connection_test_period);
			dataSource.setMaxPoolSize(config.c3p0_max_pool_size);
			dataSource.setMinPoolSize(config.c3p0_min_pool_size);
			dataSource.setInitialPoolSize(config.c3p0_initial_pool_size);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
