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
import com.github.codeframes.hal.tooling.test.http.HttpClientSupport
import com.github.codeframes.hal.tooling.test.http.InjectHttpClient
import com.github.codeframes.hal.tooling.test.json.JSON
import spock.lang.Specification

import static spock.util.matcher.HamcrestSupport.expect

class RootResourceITest extends Specification implements HttpClientSupport {

    @InjectHttpClient
    HttpClient client

    def "test get API root"() {
        when:
          def response = client.get(path: '/')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json;charset=UTF-8')
          expect response, has_body('''
            {
                "_links": {
                    "self": {
                        "href": "/"
                    },
                    "curies": [{
                        "name": "itm",
                        "href": "http://example.com/docs/items/{rel}",
                        "templated": true
                    }, {
                        "name": "ord",
                        "href": "http://example.com/docs/orders/{rel}",
                        "templated": true
                    }],
                    "ord:create-order": {
                        "href": "/orders/"
                    },
                    "ord:orders": {
                        "href": "/orders/"
                    },
                    "ord:order": {
                        "href": "/orders/{order_id}",
                        "templated": true
                    },
                    "itm:create-item": {
                        "href": "/items/"
                    },
                    "itm:items": {
                        "href": "/items/"
                    },
                    "itm:item": {
                        "href": "/items/{item_id}",
                        "templated": true
                    }
                }
            }
            ''' as JSON)
    }
}