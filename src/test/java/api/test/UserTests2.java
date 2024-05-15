package api.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserTests2 {
	
	Faker faker;
	User userpayload;
	
	 public Logger logger;
	
	@BeforeClass
	public void setData()
	{
		faker= new Faker();
		userpayload=new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5,10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		
		   // Logs
        logger = LogManager.getLogger(this.getClass());
		}
	
	@Test(priority=1)
	public void test_postUser()
	{
		logger.info("********** Creating User **********");
		
		Response response=UserEndPoints2.createUser(userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("********** User is created Successfully **********");
	}
	
	@Test(priority=2)
	public void test_UserByName()
	{
		 logger.info("********** Reading User Info **********");
		 
		Response response=UserEndPoints2.readUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
		  logger.info("********** User Info is displayed Successfully **********");
	}
	
	@Test(priority=3)
	public void test_updateByUsername()
	{
		 logger.info("********** Updating User **********");
		 
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response =UserEndPoints2.updateUser(this.userpayload.getUsername(), userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		 logger.info("********** User is Updated Successfully **********");
		
		//Checking data after update
		Response responseAfterUpdate=UserEndPoints2.readUser(this.userpayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(),200);
		
	}
	
	@Test(priority=4)
	public void test_DeleteUserByName()
	{
		logger.info("********** Deleting User **********");
		
	Response response=UserEndPoints2.DeleteUser(this.userpayload.getUsername());
	Assert.assertEquals(response.getStatusCode(),200);	
	
	logger.info("********** User is Deleted Successfully **********");
		
		
		
	}

}
