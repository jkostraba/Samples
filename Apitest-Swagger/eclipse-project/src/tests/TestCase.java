package tests;

import java.util.List;

public interface TestCase {

	public String getHeader();
	
	public void run();
	
	public List<String> getErrors();
	
	public int getTestCount();
	
	public int getFailCount();
}
