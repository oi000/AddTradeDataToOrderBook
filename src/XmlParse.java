

import java.io.File;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlParse {
	private String doc_path;
	private Element root;
	private Document document = null;

	public XmlParse(String path) {
		doc_path = path;
		readDocument(path);
		root = getRoot();
	}

	void readDocument(String path) {
		try {
			SAXReader saxReader = new SAXReader();
			document = saxReader.read(new File(path));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	Element getRoot() {
		return document.getRootElement();
	}

	public boolean parse(Config config) {
		if (parseMysqlDB(config) && parseC3p0(config) && parseOther(config))
			return true;
		return false;
	}

	private boolean parseMysqlDB(Config config) {
		try {
			Element e = root.element("MysqlInfo");
			config.mysql_ip = e.element("IP").getText();
			config.mysql_port = Integer.valueOf(e.element("Port").getText()).intValue();
			config.mysql_user_name = e.element("UserName").getText();
			config.mysql_password = e.element("Password").getText();
			config.mysql_db_name = e.element("DBName").getText();
			config.mysql_table = e.element("MysqlTable").getText();
			config.sql = e.element("SQL").getText();
			// config.mysql_table_task = e.element("TableTask").getText();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	private boolean parseOther(Config config) {
		try {
			// Element e = root.element("MysqlInfo");
			config.start_day = root.element("StartDay").getText();
			config.end_day = root.element("EndDay").getText();
			config.nn = Integer.valueOf(root.element("NN").getText()).intValue();
			config.file_name = root.element("FileName").getText();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	boolean parseC3p0(Config config) {
		try {
			Element e = root.element("C3p0");
			config.c3p0_max_idle_time = Integer.valueOf(e.element("MaxIdleTime").getText()).intValue();
			config.c3p0_idle_connection_test_period = Integer.valueOf(e.element("IdleConnectionTestPeriod").getText())
					.intValue();
			config.c3p0_max_pool_size = Integer.valueOf(e.element("MaxPoolSize").getText()).intValue();
			config.c3p0_min_pool_size = Integer.valueOf(e.element("MinPoolSize").getText()).intValue();
			config.c3p0_initial_pool_size = Integer.valueOf(e.element("InitialPoolSize").getText()).intValue();
			return true;
		} catch (Exception e) {
			// Log.logger
			// .error("Fail to parse c3p0 in parseC3p0 function in XmlParse
			// Class");
			e.printStackTrace();
			return false;
		}
	}

	private void parseStringtoVec(String str, Vector<Long> vec) {
		vec.clear();
		String p[] = str.split(",");
		for (int i = 0; i < p.length; i++)
			vec.add(Long.valueOf((long) (Double.valueOf(p[i]).doubleValue() * 3600 * 1000)));
	}
}
