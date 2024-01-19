package clever.cloning.controller;

import clever.cloning.service.StackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/stack")
public class StackController {

    private final StackService stackService;

    @Autowired
    public StackController(StackService stackService) {
        this.stackService = stackService;
    }

    @GetMapping("/info")
    public Map<String, Map<String, Integer>> getStackInfo() throws IOException {
        return stackService.getStackInfo();
    }
}