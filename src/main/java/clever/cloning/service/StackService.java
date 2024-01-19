package clever.cloning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class StackService {

    private final ResourceLoader resourceLoader;

    @Autowired
    public StackService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Map<String, Map<String, Integer>> getStackInfo() throws IOException {
        Map<String, Map<String, Integer>> stackInfoMap = new HashMap<>();

        Resource stackFolder = resourceLoader.getResource("classpath:/static/stack");
        File[] stackFolders = stackFolder.getFile().listFiles(File::isDirectory);

        for (File stack : stackFolders) {
            Map<String, Integer> levelInfo = new HashMap<>();
            File[] levelFolders = stack.listFiles(File::isDirectory);

            for (File level : levelFolders) {
                File snippetFolder = new File(level, "snippet");
                int fileCount = snippetFolder.listFiles().length;
                levelInfo.put(level.getName(), fileCount);
            }

            stackInfoMap.put(stack.getName(), levelInfo);
        }

        return stackInfoMap;
    }
}