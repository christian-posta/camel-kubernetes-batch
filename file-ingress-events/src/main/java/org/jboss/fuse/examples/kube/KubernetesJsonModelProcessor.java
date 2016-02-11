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
package org.jboss.fuse.examples.kube;

import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.generator.annotation.KubernetesModelProcessor;
import io.fabric8.openshift.api.model.TemplateBuilder;

import javax.inject.Named;
import java.util.HashMap;

/**
 * Created by ceposta 
 * <a href="http://christianposta.com/blog>http://christianposta.com/blog</a>.
 */
@KubernetesModelProcessor
public class KubernetesJsonModelProcessor {


    /**
     * Augment the containers to refer to the volumes
     * @param builder
     */
    @Named("file-ingress-events")
    public void withVolumeMounts(ContainerBuilder builder) {
        builder.withVolumeMounts(
                new VolumeMount("/deployments/camel/incoming", "file-ingress-events-incoming-volume", false),
                new VolumeMount("/deployments/camel/outgoing", "file-ingress-events-outgoing-volume", false))
                .build();
    }

    /**
     * Augment the pod template to create volumes
     * @param builder
     */
    public void withPodTemplate(PodTemplateSpecBuilder builder) {
        builder.withSpec(builder.getSpec()).editSpec()
                .addNewVolume()
                    .withName("file-ingress-events-incoming-volume")
                    .withHostPath(getIncomingCamelDir())
                .endVolume()
                .addNewVolume()
                    .withName("file-ingress-events-outgoing-volume")
                    .withHostPath(getOutgoingCamelDir())
                .endVolume()
                .endSpec().build();
    }

    private HostPathVolumeSource getIncomingCamelDir() {
        HostPathVolumeSource rc = new HostPathVolumeSource("/opt/camel/incoming");
        return rc;
    }

    private HostPathVolumeSource getOutgoingCamelDir() {
        HostPathVolumeSource rc = new HostPathVolumeSource("/opt/camel/outgoing");
        return rc;
    }



}
