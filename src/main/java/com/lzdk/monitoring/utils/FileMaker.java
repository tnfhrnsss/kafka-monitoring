package com.lzdk.monitoring.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.util.FileUtil;

@Slf4j
public class FileMaker {
    public static String getFilePath() {
        String savePath = null;
        if (System.getProperty("save.path") == null) {
            savePath = System.getProperty("user.dir");
        } else {
            savePath = System.getProperty("save.path");
        }
        return savePath;
    }

    public static void save(String topics, String fileName) {
        String path = getFilePath() + fileName;
        removeFile(path);
        FileUtil.writeAsString(new File(path), topics);
        log.debug("success saved file.");
    }

    public static Path find(String fileName) {
        return Paths.get(FileMaker.getFilePath() + fileName);
    }

    private static void removeFile(String path) {
        FileUtil.deleteContents(new File(path));
    }
}
