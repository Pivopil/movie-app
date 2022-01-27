package io.github.pivopil.movieapp.gatling;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class IDEPathHelper {

  static final Path mavenSourcesDirectory;
  static final Path mavenResourcesDirectory;
  static final Path mavenBinariesDirectory;
  static final Path resultsDirectory;
  static final Path recorderConfigFile;

  static {
    try {
      Path projectRootDir =
          Paths.get(
                  Objects.requireNonNull(
                          IDEPathHelper.class.getClassLoader().getResource("gatling.conf"))
                      .toURI())
              .getParent()
              .getParent()
              .getParent();
      Path mavenTargetDirectory = projectRootDir.resolve("target");
      Path mavenSrcTestDirectory = projectRootDir.resolve("src").resolve("test");

      mavenSourcesDirectory = mavenSrcTestDirectory.resolve("java");
      mavenResourcesDirectory = mavenSrcTestDirectory.resolve("resources");
      mavenBinariesDirectory = mavenTargetDirectory.resolve("test-classes");
      resultsDirectory = mavenTargetDirectory.resolve("gatling");
      recorderConfigFile = mavenResourcesDirectory.resolve("recorder.conf");
    } catch (URISyntaxException e) {
      throw new ExceptionInInitializerError(e);
    }
  }
}
