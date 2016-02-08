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

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.management.event.ExchangeCompletedEvent;
import org.apache.camel.management.event.ExchangeFailedEvent;
import org.apache.camel.spi.EventNotifier;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.apache.camel.support.EventNotifierSupport;
import org.apache.camel.util.ServiceHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.EventObject;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ceposta
 * <a href="http://christianposta.com/blog>http://christianposta.com/blog</a>.
 */
@Configuration
@ComponentScan(basePackages = "org.jboss.fuse.examples.route")
public class SpringConfig extends CamelConfiguration {

    @Bean
    public PropertiesComponent properties() {
        PropertiesComponent rc = new PropertiesComponent();
        rc.setLocation("classpath:/camel.properties");
        return rc;
    }


    @Bean
    public EventNotifier mainKiller(CamelContext context) throws Exception {
        EventNotifier rc = new EventNotifierSupport() {
            private final AtomicInteger current = new AtomicInteger(0);

            public void notify(EventObject event) throws Exception {
                if (event instanceof ExchangeFailedEvent || event instanceof ExchangeCompletedEvent) {
                    current.incrementAndGet();
                }

                if (current.get() == 1) {
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            System.exit(1);
                        }
                    });
                }
            }

            @Override
            public boolean isEnabled(EventObject event) {
                return true;
            }


        };
        ServiceHelper.startService(rc);
        context.getManagementStrategy().addEventNotifier(rc);
        return rc;
    }

}
