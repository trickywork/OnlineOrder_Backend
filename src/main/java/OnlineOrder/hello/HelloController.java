package OnlineOrder.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.datafaker.Faker;

//放在hello folder里是为了organize
@RestController
public class HelloController {
//这里可以改string 变成8080/string http://localhost:8080/hello
//并且路径必须是唯一的  路径不要用动词 要用名词
//    @GetMapping("/hello")
//    public String sayello() {
//        return "Hello World";
//    }

    @GetMapping("/hello")
    public Person getHello(@RequestParam(required = false) String name) {
        if (name == null) {
            name = "Guest";
        }
        Faker faker = new Faker();
        String company = faker.company().name();
        String street = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String bookTitle = faker.book().title();
        String bookAuthor = faker.book().author();
        return new Person(name, company, new Address(street, city, state, null), new Book(bookTitle, bookAuthor));
    }

}
