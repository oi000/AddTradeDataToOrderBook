
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MysqlFromPool {
	Config theConfig;
	private Connection con = null;

	private boolean isConn = false;
	ComboPooledDataSource dataSource = null;
	int tot = 0;

	public MysqlFromPool(Config ccf, ComboPooledDataSource dataSource) {
		this.theConfig = ccf;
		this.dataSource = dataSource;
		this.tot = 0;
	}

	public void getTrade(List<Trade> lt) {
		lt.clear();
		// System.out.println("========");
		Connection con = null;
		Statement stmt = null;
		String sql = "select * from okcoin_trade_old3z sort by date_ms";

		try {
			ResultSet rs = null;
			con = dataSource.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);
			int i = 0;
			while (null != rs && rs.next()) {
				tot++;
				Trade t = new Trade();
				if ((++i) % 10000 == 0)
					System.out.println(i);
				// if (i == 10000)
				// break;
			}
		} catch (MySQLIntegrityConstraintViolationException me) {

		} catch (Exception e) {
			System.err.println(sql);
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.clearBatch();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
