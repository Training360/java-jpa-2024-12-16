package employees;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HelloService {

    public String sayHello() {
        return "Hello World!";
    }
}
