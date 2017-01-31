/*
 * Copyright © 2016 Richard Burrow (https://github.com/codeframes)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.codeframes.hal.tooling.api.doc.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Nullable;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Configuration
public class StaticConfig {

    private static final String WEB_JAR_API_DOCS_PATH = "META-INF/resources/webjars/hal-tooling-api-doc-example/1.2.0-alpha-1";

    @Bean
    public WebMvcConfigurer staticConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/docs/**")
                        .addResourceLocations("classpath:/" + WEB_JAR_API_DOCS_PATH + "/docs/");
                registry.addResourceHandler("/css/**")
                        .addResourceLocations("classpath:/" + WEB_JAR_API_DOCS_PATH + "/css/");
                registry.addResourceHandler("/assets/**")
                        .addResourceLocations("classpath:/" + WEB_JAR_API_DOCS_PATH + "/assets/");
                registry.addResourceHandler("/js/**")
                        .addResourceLocations("classpath:/" + WEB_JAR_API_DOCS_PATH + "/js/");
            }

            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                URL webJarApiDocURL = getWebJarApiDocURL();
                if (webJarApiDocURL != null) {
                    registry.addRedirectViewController("/docs", "/docs/root.html");

                    try (ZipInputStream zipInputStream = new ZipInputStream(webJarApiDocURL.openStream())) {
                        ZipEntry entry;
                        while ((entry = zipInputStream.getNextEntry()) != null) {
                            if (!entry.isDirectory() && entry.getName().endsWith(".html")) {
                                String redirectUrl = entry.getName().replace(WEB_JAR_API_DOCS_PATH, "");
                                String urlPath = redirectUrl.replace(".html", "");
                                registry.addRedirectViewController(urlPath, redirectUrl);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
    }

    @Nullable
    private static URL getWebJarApiDocURL() {
        try {
            ClassPathResource resource = new ClassPathResource("/" + WEB_JAR_API_DOCS_PATH + "/");
            if (resource.exists()) {
                URI uri = resource.getURI();
                String[] array = uri.toString().split("!");

                try (FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), new HashMap<String, String>())) {
                    Path path = fs.getPath(array[1]);
                    return path.toUri().toURL();
                }
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
