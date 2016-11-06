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
package com.github.codeframes.hal.tooling.example.resources

import com.github.codeframes.hal.tooling.test.http.HttpClient
import com.github.codeframes.hal.tooling.test.http.InjectHttpClient
import spock.lang.Specification

import static com.github.codeframes.hal.tooling.test.http.HttpClientResponseMatchers.*
import static spock.util.matcher.HamcrestSupport.expect

class ImageResourceITest extends Specification {

    @InjectHttpClient
    HttpClient client

    def "test get image"() {
        when:
          def response = client.get(path: '/images/001')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('image/png')
          expect response, has_body([0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A] as byte[])
    }
}
