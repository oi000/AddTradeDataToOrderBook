public class test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Config config = new Config();
		XmlParse xml = new XmlParse("test.xml");
		xml.parse(config);
		Processor p = new Processor(config);
		p.run();
	}

}
