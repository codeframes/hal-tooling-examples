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
import com.github.codeframes.hal.tooling.test.http.InjectHttpClient
import com.github.codeframes.hal.tooling.test.http.audit.HttpAuditing
import com.github.codeframes.hal.tooling.test.json.JSON
import org.hamcrest.BaseMatcher
import org.hamcrest.Matcher
import spock.lang.Specification

import static spock.util.matcher.HamcrestSupport.expect

class OrderResourceITest extends Specification implements HttpClientSupport {

    @HttpAuditing
    @InjectHttpClient
    HttpClient client

    def "should return all available orders"() {
        when:
          def response = client.get(path: '/hal-tooling-api-doc-example/orders/')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json')
          expect response, has_body('''
            {
                "_links": {
                    "self": {
                        "href": "/hal-tooling-api-doc-example/orders/"
                    },
                    "curies": [{
                        "name": "ex",
                        "href": "/hal-tooling-api-doc-example/docs/rels/{rel}",
                        "templated": true
                    }]
                },
                "_embedded": {
                    "orders": [{
                        "_links": {
                            "self": {
                                "href": "/hal-tooling-api-doc-example/orders/123"
                            },
                            "ex:remove-order": {
                                "href": "/hal-tooling-api-doc-example/orders/123"
                            }
                        },
                        "items": [{
                            "name": "pizza",
                            "quantity": 1
                        }],
                        "cost": 2.45
                    }]
                }
            }
            ''' as JSON)
    }

    def "should return order 123"() {
        when:
          def response = client.get(path: '/hal-tooling-api-doc-example/orders/123')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json')
          expect response, has_body(order_123_response)
    }

    static order_123_response = new JSON('''
    {
        "_links": {
            "self": {
                "href": "/hal-tooling-api-doc-example/orders/123"
            },
            "curies": [{
                "name": "ex",
                "href": "/hal-tooling-api-doc-example/docs/rels/{rel}",
                "templated": true
            }],
            "ex:remove-order": {
                "href": "/hal-tooling-api-doc-example/orders/123"
            }
        },
        "items": [{
            "name": "pizza",
            "quantity": 1
        }],
        "cost": 2.45
    }
    ''')

    def "should create a new order and return it"() {
        when:
          def response = client.post(path: '/hal-tooling-api-doc-example/orders/', requestContentType: 'application/json',
                  body: '''
                    {
                        "items": [{
                            "name": "pizza",
                            "quantity": 1
                        }],
                        "cost": 6.45
                    }
                    ''' as JSON)
        then:
          expect response, has_status(201)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json')
          expect response, has_body('''
            {
                "_links": {
                    "self": {
                        "href": "/hal-tooling-api-doc-example/orders/123"
                    },
                    "curies": [{
                        "name": "ex",
                        "href": "/hal-tooling-api-doc-example/docs/rels/{rel}",
                        "templated": true
                    }],
                    "ex:remove-order": {
                        "href": "/hal-tooling-api-doc-example/orders/123"
                    }
                },
                "items": [{
                    "name": "pizza",
                    "quantity": 1
                }],
                "cost": 6.45
            }
            ''' as JSON)
    }

    def "should delete order 123 and return it"() {
        when:
          def response = client.delete(path: '/hal-tooling-api-doc-example/orders/123')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/json')
          expect response, has_body('''
            {
                "items": [{
                    "name": "pizza",
                    "quantity": 1
                }],
                "cost": 2.45
            }
            ''' as JSON)
    }

    def "should return an error response if an invalid order is submitted for creation"() {
        when:
          def response = client.post(path: '/hal-tooling-api-doc-example/orders/', requestContentType: 'application/json',
                  body: new JSON('''
                    {
                        "cost": 7.45
                    }
                    '''))
        then:
          expect response, has_status(400)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/vnd.error+json')
          expect response, has_body(new JSON("""
            {
                "_links": {
                    "about": {
                        "href": "http://path.to/user/resource/1"
                    },
                    "describes": {
                        "href": "http://path.to/describes"
                    },
                    "help": {
                        "href": "http://path.to/help"
                    }
                },
                "logref": $is_uuid,
                "message": "items may not be empty"
            }
            """))
    }

    static Matcher is_uuid = [
            matches         : { actual ->
                try {
                    UUID.fromString(actual)
                    return true
                } catch (IllegalArgumentException ignore) {
                    return false
                }
            },
            describeTo      : { description -> description.appendText("to be a UUID") },
            describeMismatch: { item, description -> description.appendText("was ").appendValue(item) }
    ] as BaseMatcher
}
