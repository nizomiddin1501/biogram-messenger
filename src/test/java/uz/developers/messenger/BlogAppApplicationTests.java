package uz.developers.messenger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import uz.developers.messenger.repository.UserRepository;

@SpringBootTest
class BlogAppApplicationTests {

    @Autowired
    private UserRepository userRepository;

    public BlogAppApplicationTests(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void repositoryTest(){
        String className = userRepository.getClass().getName();
        String packName = userRepository.getClass().getPackageName();
        System.out.println(className);
        System.out.println(packName);

    }

}
