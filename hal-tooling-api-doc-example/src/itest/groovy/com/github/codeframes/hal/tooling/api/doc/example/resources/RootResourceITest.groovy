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
package com.github.codeframes.hal.tooling.api.doc.example.resources

import com.github.codeframes.hal.tooling.test.http.HttpClient
import com.github.codeframes.hal.tooling.test.http.HttpClientSupport
import com.github.codeframes.hal.tooling.test.http.audit.InjectAuditingHttpClient
import com.github.codeframes.hal.tooling.test.json.JSON
import spock.lang.Specification

import static spock.util.matcher.HamcrestSupport.expect

class RootResourceITest extends Specification implements HttpClientSupport {

    @InjectAuditingHttpClient
    HttpClient client

    def "should return root resource showing available links"() {
        when:
          def response = client.get(path: '/hal-tooling-api-doc-example/')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json')
          expect response, has_body('''
            {
                "_links": {
                    "self": {
                        "href": "/hal-tooling-api-doc-example/"
                    },
                    "curies": [{
                        "name": "ex",
                        "href": "/hal-tooling-api-doc-example/docs/rels/{rel}",
                        "templated": true
                    }],
                    "ex:create-order": {
                        "href": "/hal-tooling-api-doc-example/orders/"
                    },
                    "ex:order": {
                        "href": "/hal-tooling-api-doc-example/orders/{order_id}",
                        "templated": true
                    },
                    "ex:orders": {
                        "href": "/hal-tooling-api-doc-example/orders/"
                    }
                }
            }
            ''' as JSON)
    }
}
