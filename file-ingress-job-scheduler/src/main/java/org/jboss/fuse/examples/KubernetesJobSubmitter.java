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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.fabric8.kubernetes.api.model.KubernetesList;
import io.fabric8.kubernetes.api.model.extensions.Job;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.apache.camel.Body;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Created by ceposta 
 * <a href="http://christianposta.com/blog>http://christianposta.com/blog</a>.
 */
public class KubernetesJobSubmitter {

    private static final ObjectMapper MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Autowired
    private KubernetesClient kubernetesClient;

    private Map<String, String> jobLabels;

    public String generateKubernetesJobManifest(@Body String fileLocation) {
        String kubeJson = null;
        try {
            KubernetesJobManifestCreator manifestCreator = new KubernetesJobManifestCreator(fileLocation);
            KubernetesList kubeJob = manifestCreator.createJob();
            kubeJson = MAPPER.writeValueAsString(kubeJob);
            System.out.println(kubeJson);
            kubernetesClient.extensions().jobs().create(kubeJob);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
        return kubeJson;
    }




}
