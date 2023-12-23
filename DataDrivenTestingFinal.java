
package TestNG;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;

public class DataDrivenTestingFinal {

    //This code has been written for Windows OS & for MacOS
    WebDriver driver =new SafariDriver();

    @BeforeClass
    public void setup()
    {
        //System.setProperty("webdriver.chrome.driver","usr/local/bin/chromedriver");
        //driver=new ChromeDriver();

        WebDriverManager.safaridriver().setup();
        //WebDriver driver=new SafariDriver();
        //driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // This method is deprecated
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }
    @Test(dataProvider="LoginData")
    public void loginTest(String user,String pwd, String exp)
    {
        //System.out.println(user+ " " +pwd+ " " +exp);
        driver.get("https://admin-demo.nopcommerce.com/login");
        WebElement txtEmail=driver.findElement(By.id("Email"));
        txtEmail.clear();
        txtEmail.sendKeys(user);

        WebElement txtPassword=driver.findElement(By.id("Password"));
        txtPassword.clear();
        txtPassword.sendKeys(pwd);

        WebElement login= driver.findElement(By.xpath("//button[@type='submit']"));
        login.click();

        String exp_title="Dashboard / nopCommerce administration";
        String act_title=driver.getTitle();

        /*
        If it is matching then it will be passed and if not matching then failed test case

        so here i have attached 4 test data for login , 1 valid test data (valid Un & PWD) and 3 invalid data
        if login would be successful then exp_title and act_title would be same and test should be passed, if not then test would be failed

        here for login with valid and invalid data we have 4 test conditions / scenarios

        Scenario 1: Valid Test Data (UN & PWD):

        Test Case 1: If Login Success the test is Pass (Page Title should be Matched)
        Test Case 2: If Login not Success the test is Failed (Page title should not match)

        Scenario 2: Invalid Test Data (UN & PWD)

        Test Case 3: If Login Success (Page Title should be Matched) but test is failed
        Test Case 4: If Login is Not Success the test is Failed (Page title should not match)

         */

        //Valid Test Data (UN & PWD):

        if (exp.equals("Valid"))
        {
            if (exp_title.equals(act_title)) //Test Case 1
            {
                driver.findElement(By.linkText("Logout")).click();
                Assert.assertTrue(true);
            }
            else
            {
                Assert.assertTrue(false); //Test Case 2
            }
        }
        //Invalid Test Data (UN & PWD)
        else if (exp.equals("Invalid"))
        {
            if (exp_title.equals(act_title)) //Test Case 3
            {
                driver.findElement(By.linkText("Logout")).click();
                Assert.assertTrue(false);
            }
            else
            {
                Assert.assertTrue(true); //Test Case 4
            }
        }
    }
    @DataProvider(name="LoginData")
    public String[][] getData() throws IOException {

        /*
        Here i am commenting this section because this is hard coded values to take the data and perform our test execution...

        But now i have to "get the data from excel sheet" that is available in my program file folder name: DataFiles -> loginData.xlsx

        String loginData[][]={

                {"admin@yourstore.com","admin","Valid"},
                {"admin@yourstore.com","adm","Invalid"},
                {"adm@yourstore.com","admin","Invalid"},
                {"adm@yourstore.com","adm","Invalid"}
        };

        */

        String path=".//DataFiles//loginData.xlsx";
        XLUtility xlutil=new XLUtility(path);

        int totalrows=xlutil.getRowCount("Sheet 1");
        int totalcols=xlutil.getCellCount("Sheet 1",1);

        String loginData[][]=new String[totalrows][totalcols]; // now next operation will be like read the data from excel and store /keep those data/value into the 2D in this Array
        /*
        In Array the indexes (row and column both) will be start from "0" but in excel sheet 1st row is header part and 2nd row onward it will start from "1", and column will start from "0" or "A","B"...etc
        */

        for (int i=1;i<=totalrows;i++)
        {
            for (int j=0;j<totalcols;j++)
            {
                loginData[i-1][j]=xlutil.getCellData("Sheet 1",i,j);//in excel sheet the value of i(1) and j(0) so i have to store it in 2 D array at the position 0 0, so need to do i-1;
                //this inner loop will execute multiple times untill all the columns completed, after that it will move to next row
            }
        }
        return loginData;

        /*
        Why utility file is needed to work with Excel file for Data Driven Testing?
        Ans.
        1. Re-usability:
        Let's say we have multiple data driven test case TC-1, TC-2, TC-3,TC-4...etc

        here for TC-1 if some piece of code is interacting with Web UI (like finding elements,performing actions...ect and some other piece of code will work with excel file like opening the excel file,
        reading the data,counting the data, counting rows, counting cells...etc like excel related operations would be there...so if i include everything in one test case the test case will be complex... so almost these operations are common for every test case in 'Data Driven Testing')

        since these are the reusable things in every test cases so we create a utility file and have a separate methods there for every re-usable things like: opening the excel file, reading the data,counting the data, counting rows, counting cells...etc and call these methods from utility file to use those methods and perform the operations in test cases.

        2. Reduce the complexity of the code:

         */
    }

    @AfterClass
    void tearDown()
    {
        driver.close();
    }


}


