package repository;

import exception.GetFilesPathsException;

import java.nio.file.Path;
import java.util.List;

public interface PathsRepository {

    List<Path> getFilesPaths() throws GetFilesPathsException;

}
