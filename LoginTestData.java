package TestNG;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTestData {

    @Test(dataProvider="LoginData")
    public void loginTest(String user,String pwd, String exp)
    {
        System.out.println(user +"    "+ pwd +"    "+ exp);

    }

    @DataProvider(name="LoginData")
    public String[][] getData()
    {
        String loginData[][]={

                {"admin@yourstore.com","admin","Valid"},
                {"admin@yourstore.com","adm","Invalid"},
                {"adm@yourstore.com","admin","Invalid"},
                {"adm@yourstore.com","adm","Invalid"}
        };
        return loginData;
    }
}
