package repository.impl;

import exception.GetFilesPathsException;
import repository.PathsRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathsRepositoryImpl implements PathsRepository {

    public static final String USER_DIR_PATH = "user.dir";
    public static final String INPUT_DIRECTORY_PATH = "/src/main/resources/input_directory/";
    public static final String OUTPUT_DIRECTORY_PATH = "/src/main/resources/output_directory/";

    @Override
    public List<Path> getFilesPaths() throws GetFilesPathsException {
        try (Stream<Path> walk = Files.walk(Paths.get(System.getProperty(USER_DIR_PATH) + INPUT_DIRECTORY_PATH))) {

            var paths = walk
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
            return paths;

        } catch (IOException e) {
            throw new GetFilesPathsException(String.format("Can not load files from directory path: %s",
                    System.getProperty(USER_DIR_PATH) + INPUT_DIRECTORY_PATH));
        }
    }

}
