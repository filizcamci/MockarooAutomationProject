package mockarooAutomation;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

//Step 1.Create a Java class MockarooDataValidation.java
public class MockarooAutomationProject {

	WebDriver driver;

	@BeforeClass
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		// Step 2.Navigate to https://mockaroo.com/
		driver.get("https://mockaroo.com");
		// driver.manage().window().fullscreen();
	}

	// @BeforeMethod
	// public void goToPage() {
	// driver.get("https://mockaroo.com");
	// }

	// Step 3.Assert title is correct
	@Test(priority = 1)
	public void verifyTitle() {
		String actualTitle = driver.getTitle();
		String expectedTitle = "Mockaroo - Random Data Generator and API Mocking Tool | JSON / CSV / SQL / Excel";
		assertEquals(actualTitle, expectedTitle);

		// Step 4.Assert Mockaroo andrealistic data generator are displayed
		String actualText1 = driver.findElement(By.xpath("//div[@class='navbar navbar-fixed-top']/div/div/a/div[1]"))
				.getText();
		String expectedText1 = "mockaroo";
		assertEquals(actualText1, expectedText1);

		String actualText2 = driver.findElement(By.xpath("//div[@class='navbar navbar-fixed-top']/div/div/a/div[2]"))
				.getText();
		String expectedText2 = "realistic data generator";
		assertEquals(actualText2, expectedText2);

	}

	@Test(priority = 2)
	public void generateData() {

		// Step 5. Remove all existing fields by clicking on x icon link
		driver.findElement(By.xpath("//*[@id=\"fields\"]/div[1]/div[5]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"fields\"]/div[2]/div[5]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"fields\"]/div[3]/div[5]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"fields\"]/div[4]/div[5]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"fields\"]/div[5]/div[5]/a")).click();
		driver.findElement(By.xpath("//*[@id=\"fields\"]/div[6]/div[5]/a")).click();

		// Step 6. Assert that ‘Field Name’ , ‘Type’, ‘Options’ labels are displayed
		String field = driver.findElement(By.xpath("//*[@id=\"schema_form\"]/div[2]/div[3]/div[1]/div[1]")).getText();
		String efield = "Field Name";
		assertEquals(field, efield);
		String type = driver.findElement(By.xpath("//div[@class='column column-header column-type']")).getText();
		String etype = "Type";
		assertEquals(type, etype);
		String options = driver.findElement(By.xpath("//div[@class='column column-header column-options']")).getText();
		String eoptions = "Options";
		assertEquals(options, eoptions);

		// Step 7. Assert that ‘Add another field’ button is enabled
		boolean isEnabled = driver
				.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).isEnabled();
		Assert.assertTrue(isEnabled);

		// Step 8. Assert thatdefault number of rows is 1000.
		assertEquals(driver.findElement(By.xpath("//input[@class='medium-number form-control']")).getAttribute("value"),
				"1000");

		// Step 9. Assert thatdefault format selection is CSV
		assertEquals(driver.findElement(By.xpath("//select[@id='schema_file_format']/option[1]")).getText(), "CSV");

		// Step 10. Assert that Line Ending is Unix(LF)
		assertEquals(driver.findElement(By.xpath("//select[@id='schema_line_ending']/option[1]")).getText(),
				"Unix (LF)");

		// Step 11. Assert that header checkbox is checked and BOM is unchecked
		assertTrue(driver.findElement(By.xpath("//input[@id='schema_include_header']")).isSelected());
		assertFalse(driver.findElement(By.xpath("//input[@id='schema_bom']")).isSelected());

	}

	@Test(priority = 3)
	public void createRows() throws InterruptedException {

		// Step 12. Click on ‘Add another field’ and enter word “City”
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn add_nested_fields']")).click();
		driver.findElement(By.xpath("//div[@id='fields']/div[7]/div[2]/input")).sendKeys("City");
		driver.findElement(By.xpath("//div[@class='table-body']/div/div[7]/div[3]/input[3]")).click();
		Thread.sleep(2000);

		// Step 13. Click on Choose type and assert that Choose a Type dialog box is
		// displayed.
		String t = driver.findElement(By.xpath("//div[@id='type_dialog_wrap']//h3")).getText();
		// System.out.println("text: "+t);
		assertEquals(t, "Choose a Type");
		Thread.sleep(3000);

		// Step 14. Search for “city” and click on City on search results.
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("city");
		driver.findElement(By.xpath("//div[@id='type_list']/div")).click();
		// driver.findElement(By.xpath("//a[@class='btn btn-default add-column-btn
		// add_nested_fields']")).click();

		// Step 15. Repeat steps 12-14 with field name and type “Country
		driver.findElement(By.xpath("//div[@id='fields']/div[8]/div[2]/input")).sendKeys("Country");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@class='table-body']/div/div[8]/div[3]")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='type_search_field']")).clear();
		driver.findElement(By.xpath("//input[@id='type_search_field']")).sendKeys("country");
		driver.findElement(By.xpath("//div[@class='type'][1]")).click();

	}

	// Step 16. Click on Download Data
	// @Test(priority = 4)
	// public void downloadData() throws InterruptedException {
	// Thread.sleep(1000);
	// driver.findElement(By.xpath("//*[@id=\"download\"]")).click();
	// }

	@Test(priority = 5)
	public void readFile() throws IOException {

		// Step 17. Open the downloaded file using BufferedReader
		FileReader fr = new FileReader("C:\\Users\\filiz\\Downloads\\MOCK_DATA (2).csv");
		// FileReader fr = new FileReader("Cities_and_Countries.txt");
		BufferedReader br = new BufferedReader(fr);

		// Step 18. Assert that first row is matching with Field names that we selecte
		String line = br.readLine();
		// System.out.println(line);
		assertEquals(line, "City,Country");

		// Step 19. Assert that there are 1000 records
		// Step 20. From file add all Cities to Cities ArrayList
		// Step 21. Add all countries to Countries ArrayList
		List<String> cities = new ArrayList();
		List<String> countries = new ArrayList();

		int count = 0;
		while ((line = br.readLine()) != null) {
			cities.add(line.substring(0, line.indexOf(",")));
			countries.add(line.substring(line.indexOf(",") + 1));

			count++;
		}
		// System.out.println(count);
		assertEquals(count, 1000);
		System.out.println(cities);
		System.out.println(countries);

		// Step 22. Sort all cities and find the city with the longest name and shortest
		// name
		Collections.sort(cities);
		Collections.sort(countries);
		System.out.println(cities);
		System.out.println(countries);
		int maxLength = cities.get(0).length();
		int maxIndex = 0;
		int minLength = cities.get(0).length();
		int minIndex = 0;
		for (int i = 1; i < cities.size(); i++) {
			if (cities.get(i).length() > maxLength) {
				maxLength = cities.get(i).length();
				maxIndex = i;
			}
		}
		System.out.println(
				cities.get(maxIndex) + " is the longest city name in cities list. It has " + maxLength + " letters");
		for (int i = 1; i < cities.size(); i++) {
			if (cities.get(i).length() < minLength) {
				minLength = cities.get(i).length();
				minIndex = i;
			}
		}
		System.out.println(
				cities.get(minIndex) + " is the shortest city name in cities list. It has " + minLength + " letters");

		// Step 23. In Countries ArrayList, find how many times each Country is
		// mentioned. and print out

		Set<String> uniqueCountries = new HashSet(countries);
		for (String each : uniqueCountries) {
			System.out.println(each + " - " + Collections.frequency(countries, each));
		}

		// Step 24. From file add all Cities to citiesSet HashSet
		Set<String> citiesSet = new HashSet<>(cities);

		// Step 25. Count how many unique cities are in Cities ArrayList and assert that
		// it is matching with the count of citiesSet HashSet.
		List<Integer> cityCounts=new ArrayList();
		List<String> uniqueCities=new ArrayList();

		for (int i = 0; i < cities.size(); i++) {
			int cityCount = 1;
			for(int j=0; j<cities.size(); j++) {
				if (!cities.get(i).equals(cities.get(j)) && (!uniqueCities.contains(cities.get(j)))) {
					cityCount++;
					uniqueCities.add(cities.get(j));
				} else {
					continue;
				}
			}
		//cityCounts.add(cityCount);
		}
		assertEquals(uniqueCities.size(),citiesSet.size());

		// Step 26. Add all Countries to countrySet HashSet
		Set<String> countrySet = new HashSet(countries);
		
		
		// Step 27. Count how many unique countries are in Countries ArrayList and
		// assert that it is matching with the count of countrySet HashSet.
		List<String> countryCount=new ArrayList();

		for (int i = 0; i < countries.size(); i++) {
			for(int j=0; j<countries.size(); j++) {
				if (!countries.get(i).equals(countries.get(j)) && (!countryCount.contains(countries.get(j)))) {
					countryCount.add(countries.get(j));
				} else {
					continue;
				}
			}
		}
		assertEquals(countryCount.size(),countrySet.size());
		
		
		// Step 28. Push the code to any GitHub repo that you have and submit the url

	}

	@AfterClass
	public void cleanUp() {
		driver.close();
	}

}
