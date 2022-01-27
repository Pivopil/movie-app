package io.github.pivopil.movieapp.gatling;

import io.gatling.recorder.GatlingRecorder;
import io.gatling.recorder.config.RecorderPropertiesBuilder;
import scala.Option;

import java.nio.file.Path;

public class Recorder {
    public static void main(String[] args) {
        RecorderPropertiesBuilder props = new RecorderPropertiesBuilder()
                .simulationsFolder(IDEPathHelper.mavenSourcesDirectory.toString())
                .resourcesFolder(IDEPathHelper.mavenResourcesDirectory.toString())
                .simulationPackage("io.github.pivopil.movieapp.gatling.simulation");

        GatlingRecorder.fromMap(props.build(), Option.<Path> apply(IDEPathHelper.recorderConfigFile));
    }
}
