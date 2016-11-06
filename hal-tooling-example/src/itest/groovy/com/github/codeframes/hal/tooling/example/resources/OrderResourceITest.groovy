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
import com.github.codeframes.hal.tooling.test.json.JSON
import org.hamcrest.BaseMatcher
import spock.lang.Specification

import static com.github.codeframes.hal.tooling.test.http.HttpClientResponseMatchers.*
import static spock.util.matcher.HamcrestSupport.expect

class OrderResourceITest extends Specification {

    @InjectHttpClient
    HttpClient client

    def "test get orders"() {
        when:
          def response = client.get(path: '/orders/')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json;charset=UTF-8')
          expect response, has_body(orders_response)
    }

    static orders_response = new JSON('''
    {
        "_links": {
            "self": {
                "href": "/orders/"
            },
            "curies": [{
                "name": "ord",
                "href": "http://example.com/docs/orders/{rel}",
                "templated": true
            }]
        },
        "_embedded": {
            "orders": [{
                "_links": {
                    "self": {
                        "href": "/orders/456"
                    },
                    "ord:remove-order": {
                        "href": "/orders/456"
                    }
                },
                "items": [{
                    "name": "ssd",
                    "quantity": 2
                }],
                "cost": 167.64
            }]
        }
    }
    ''')

    def "test get order"() {
        when:
          def response = client.get(path: '/orders/456')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json;charset=UTF-8')
          expect response, has_body(order_response)
    }

    static order_response = new JSON('''
    {
        "_links": {
            "self": {
                "href": "/orders/456"
            },
            "curies": [{
                "name": "ord",
                "href": "http://example.com/docs/orders/{rel}",
                "templated": true
            }],
            "ord:remove-order": {
                "href": "/orders/456"
            }
        },
        "items": [{
            "name": "ssd",
            "quantity": 2
        }],
        "cost": 167.64
    }
    ''')

    def "test create order"() {
        when:
          def response = client.post(path: '/orders/', requestContentType: 'application/json', body: '''
            {
                "items": [{
                    "name": "ram",
                    "quantity": 1
                }],
                "cost": 165.40
            }
            ''')
        then:
          expect response, has_status(201)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json;charset=UTF-8')
          expect response, has_body('''
            {
                "_links": {
                    "self": {
                        "href": "/orders/789"
                    },
                    "curies": [{
                        "name": "ord",
                        "href": "http://example.com/docs/orders/{rel}",
                        "templated": true
                    }],
                    "ord:remove-order": {
                        "href": "/orders/789"
                    }
                },
                "items": [{
                    "name": "ram",
                    "quantity": 1
                }],
                "cost": 165.40
            }
            ''' as JSON)
    }

    def "test delete order"() {
        when:
          def response = client.delete(path: '/orders/456')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/json;charset=UTF-8')
          expect response, has_body(deleted_order_response)
    }

    static deleted_order_response = new JSON('''
    {
        "items": [{
            "name": "ssd",
            "quantity": 2
        }],
        "cost": 167.64
    }
    ''')

    def "test create invalid order"() {
        when:
          def response = client.post(path: '/orders/', requestContentType: 'application/json', body: invalid_order_request)
        then:
          expect response, has_status(400)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/vnd.error+json;charset=UTF-8')
          expect response, has_body(error_response)
    }

    static invalid_order_request = new JSON('''
    {
        "cost": 167.64
    }
    ''')

    static is_uuid = [
            matches         : { String actual ->
                try {
                    UUID.fromString(actual)
                    return true
                } catch (IllegalArgumentException ignore) {
                    return false
                }
            },
            describeTo      : { description -> description.appendText("value to be a UUID") },
            describeMismatch: { item, description -> description.appendText("was ").appendValue(item) }
    ] as BaseMatcher

    static error_response = new JSON("""
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
    """)
}