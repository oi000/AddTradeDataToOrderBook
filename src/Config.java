

public class Config {
	public String mysql_ip;
	public int mysql_port;
	public String mysql_user_name;
	public String mysql_password;
	public String mysql_db_name;
	public String mysql_table;
	public String sql;

	public int c3p0_max_idle_time;
	public int c3p0_idle_connection_test_period;
	public int c3p0_max_pool_size;
	public int c3p0_min_pool_size;
	public int c3p0_initial_pool_size;

	public String start_day;
	public String end_day;
	public String file_name = null;
	public int nn = 400;

	// public String url_prex;
	// public String symbol;
	// public int num_write_thread;
	// public int num_crawel_thread;

	// public int process_thread_number = 5;

	// public long sleep_time = 1;

	// public int max_line = 10;

	// public String[] receive_address;

	public String toString() {
		return "{mysql_ip: " + mysql_ip + "}; \r\n" + "{mysql_port: " + mysql_port + "}; \r\n" + "{mysql_user_name: "
				+ mysql_user_name + "}; \r\n" + "{mysql_password: " + mysql_password + "}; \r\n" + "{mysql_db_name: "
				+ mysql_db_name + "}; \r\n";
	}
}
