import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = CenterApplication.class)
public class Test {

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        //$2a$10$USsqXFfue.To1CYDoQ6hUeY2g9sTGA9iuTI9mjeG7IpQGFlDwpDxq
        //$2a$10$jwXty5CaCzlZ.bPeD4Li/.5RehYh32CKqvNOnCZIWPX6i6C61vPa.
    }

}
