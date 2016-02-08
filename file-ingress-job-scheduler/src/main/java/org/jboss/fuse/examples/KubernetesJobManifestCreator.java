/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.fuse.examples;

import io.fabric8.kubernetes.api.model.EnvVar;
import io.fabric8.kubernetes.api.model.EnvVarBuilder;
import io.fabric8.kubernetes.api.model.extensions.Job;
import io.fabric8.kubernetes.api.model.extensions.JobBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ceposta 
 * <a href="http://christianposta.com/blog>http://christianposta.com/blog</a>.
 */
public class KubernetesJobManifestCreator {

    private final String fileLocation;
    private final String filePath;
    private final String fileName;

    public KubernetesJobManifestCreator(String fileLocation) {
        this.fileLocation = fileLocation;
        String sep = File.separator;
        int chop = fileLocation.lastIndexOf(sep);
        filePath = fileLocation.substring(0, chop);
        fileName = fileLocation.substring(chop + 1, fileLocation.length());
        System.out.println("file path: '" + filePath + "' file name: '" + fileName + "'");
    }

    public Job createJob() {
        JobBuilder builder = new JobBuilder();
        return builder.withNewMetadata()
                    .withName("file-backend-job")
                .endMetadata()
                    .withNewSpec()
                        .withNewSelector()
                        .withMatchLabels(getJobLabels())
                    .endSelector()
                    .withNewTemplate()
                        .withNewMetadata()
                            .withName("file-backend-job")
                            .withLabels(getJobLabels())
                        .endMetadata()
                        .withNewSpec()
                            .addNewContainer()
                                .withName("file-backend-job")
                                .withImage("fabric8/file-backend-job:1.0-SNAPSHOT")
                                .withEnv(getJobEnvironmentVariables(fileLocation))
                            .endContainer()
                            .withRestartPolicy("Never")
                        .endSpec()
                    .endTemplate()
                    .endSpec().build();

    }

    public Map<String,String> getJobLabels() {
        HashMap<String, String> map = new HashMap<>();
        map.put("job", "job11");
        return map;
    }

    public List<EnvVar> getJobEnvironmentVariables(String fileLocation) {
        LinkedList<EnvVar> rc = new LinkedList<>();
        rc.add(new EnvVarBuilder().withName("FILE_NAME").withValue(fileName).build());
        rc.add(new EnvVarBuilder().withName("FILE_PATH").withValue(filePath).build());
        return rc;

    }
}
