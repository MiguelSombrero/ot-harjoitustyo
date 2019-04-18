
package budgetapp.domain;

import java.time.LocalDate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    
    User user;
    
    @Before
    public void setUp() {
        user = new User("Miika", "Salasana", LocalDate.parse("2019-05-20"));
    }
    
    @Test
    public void createsUserRightWithoutDayCreated() {
        LocalDate now = LocalDate.now();
        
        User user2 = new User("Miika", "Salasana");
        assertEquals("Miika", user2.getUsername());
        assertEquals("Salasana", user2.getPassword());
        assertEquals(now, user2.getCreated());
    }
    
    @Test
    public void createsUserRightWithDayCreated() {
        assertEquals("Miika", user.getUsername());
        assertEquals("Salasana", user.getPassword());
        assertEquals(LocalDate.parse("2019-05-20"), user.getCreated());
    }
    
    @Test
    public void toStringWorks() {
        assertEquals("Username Miika, created 20.05.2019", user.toString());
    }
    
}
