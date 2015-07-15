package demo;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class NewrelicDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewrelicDemoApplication.class, args);
    }
    
    

    
    
}

@RestController("/service1")
class ServiceControllers
{
	 
	@RequestMapping("/service1")
    public String service1()
    {
    	
    	return "service1";
    	
    }
    
	@RequestMapping("/service2")
    public String service2()
    {
    	throw new ConversionNotSupportedException(null, String.class, new Throwable());
		//return "service2";
    	
    }
    
	@RequestMapping("/service3")
    public String service3()
    {
    	throw new TypeMismatchException("abc", String.class);
		//return "service3";
    	
    }

}