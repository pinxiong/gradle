/*
 * Copyright 2007-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.internal.artifacts.publish.maven;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.project.MavenProject;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.maven.Conf2ScopeMappingContainer;
import org.gradle.api.artifacts.maven.MavenPom;
import org.gradle.api.internal.artifacts.publish.maven.dependencies.PomDependenciesConverter;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/**
 * @author Hans Dockter
 */
public class DefaultMavenPom implements MavenPom {
    private PomDependenciesConverter pomDependenciesConverter;
    private MavenProject mavenProject;
    private Conf2ScopeMappingContainer scopeMappings;
    
    public DefaultMavenPom(Conf2ScopeMappingContainer scopeMappings, PomDependenciesConverter pomDependenciesConverter, MavenProject mavenProject) {
        this.scopeMappings = scopeMappings;
        this.pomDependenciesConverter = pomDependenciesConverter;
        this.mavenProject = mavenProject;
        mavenProject.setModelVersion("4.0.0");
    }

    public Conf2ScopeMappingContainer getScopeMappings() {
        return scopeMappings;
    }

    public Artifact getArtifact() {
        return mavenProject.getArtifact();
    }

    public void setArtifact(Artifact artifact) {
        mavenProject.setArtifact(artifact);
    }

    public void setGroupId(String groupId) {
        mavenProject.setGroupId(groupId);
    }

    public String getGroupId() {
        return mavenProject.getGroupId();
    }

    public void setArtifactId(String artifactId) {
        mavenProject.setArtifactId(artifactId);
    }

    public String getArtifactId() {
        return mavenProject.getArtifactId();
    }

    public void setName(String name) {
        mavenProject.setName(name);
    }

    public String getName() {
        return mavenProject.getName();
    }

    public void setVersion(String version) {
        mavenProject.setVersion(version);
    }

    public String getVersion() {
        return mavenProject.getVersion();
    }

    public String getPackaging() {
        return mavenProject.getPackaging();
    }

    public void setPackaging(String packaging) {
        mavenProject.setPackaging(packaging);
    }

    public void setInceptionYear(String inceptionYear) {
        mavenProject.setInceptionYear(inceptionYear);
    }

    public String getInceptionYear() {
        return mavenProject.getInceptionYear();
    }

    public void setUrl(String url) {
        mavenProject.setUrl(url);
    }

    public String getUrl() {
        return mavenProject.getUrl();
    }

    public void setDescription(String description) {
        mavenProject.setDescription(description);
    }

    public String getDescription() {
        return mavenProject.getDescription();
    }

    public MavenProject getMavenProject() {
        return mavenProject;
    }
    
    public void addDependencies(Set<Configuration> configurations) {
        mavenProject.setDependencies(pomDependenciesConverter.convert(getScopeMappings(),configurations));    
    }

    public PomDependenciesConverter getPomDependenciesConverter() {
        return pomDependenciesConverter;
    }

    public void write(Writer pomWriter) {
        try {
            mavenProject.writeModel(pomWriter);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
