package springjpa.signup.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class ItemController {
    @GetMapping("/items")
    public String items() {
        log.info("items controller");
        return "item/items";
    }
}
