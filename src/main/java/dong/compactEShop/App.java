package dong.compactEShop;

import dong.compactEShop.dao.UserDOMapper;
import dong.compactEShop.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"dong.compactEShop"})
@RestController
@MapperScan("dong.compactEShop.dao")
public class App
{
    @Autowired
    private UserDOMapper userDOMapper;

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/")
    public String home(){
        UserDO userDO =  userDOMapper.selectByPrimaryKey(2);
        if(userDO==null){
            return "User Object does not exist";
        }else{
            return userDO.getName();
        }
    }
}
