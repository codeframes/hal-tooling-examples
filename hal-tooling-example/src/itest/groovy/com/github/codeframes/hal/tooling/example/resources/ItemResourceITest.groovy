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

class ItemResourceITest extends Specification implements HttpClientSupport {

    @InjectHttpClient
    HttpClient client

    def "test get items"() {
        when:
          def response = client.get(path: '/items/')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json;charset=UTF-8')
          expect response, has_body(items_response)
    }

    static items_response = new JSON('''
    {
        "_links": {
            "self": {
                "href": "/items/"
            },
            "curies": [{
                "name": "itm",
                "href": "http://example.com/docs/items/{rel}",
                "templated": true
            }]
        },
        "_embedded": {
            "items": [{
                "_links": {
                    "self": {
                        "href": "/items/321"
                    },
                    "itm:remove-item": {
                        "href": "/items/321"
                    },
                    "itm:image": [{
                        "href": "/images/001"
                    }]
                },
                "name": "cpu",
                "cost": 959.95
            }]
        }
    }
    ''')

    def "test get item"() {
        when:
          def response = client.get(path: '/items/321')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json;charset=UTF-8')
          expect response, has_body(item_response)
    }

    static item_response = new JSON('''
    {
        "_links": {
            "self": {
                "href": "/items/321"
            },
            "curies": [{
                "name": "itm",
                "href": "http://example.com/docs/items/{rel}",
                "templated": true
            }],
            "itm:remove-item": {
                "href": "/items/321"
            }
        },
        "name": "cpu",
        "cost": 959.95
    }
    ''')

    def "test create item"() {
        when:
          def response = client.post(path: '/items/', requestContentType: 'application/json', body: item_request)
        then:
          expect response, has_status(201)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/hal+json;charset=UTF-8')
          expect response, has_body(created_item_response)
    }

    static item_request = '''
    {
        "name": "gpu",
        "cost": 659.99
    }
    '''

    static created_item_response = new JSON('''
    {
        "_links": {
            "self": {
                "href": "/items/654"
            },
            "curies": [{
                "name": "itm",
                "href": "http://example.com/docs/items/{rel}",
                "templated": true
            }],
            "itm:remove-item": {
                "href": "/items/654"
            }
        },
        "name": "gpu",
        "cost": 659.99
    }
    ''')

    def "test delete item"() {
        when:
          def response = client.delete(path: '/items/321')
        then:
          expect response, has_status(200)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/json;charset=UTF-8')
          expect response, has_body(deleted_item_response)
    }

    static deleted_item_response = new JSON('''
    {
        "name": "cpu",
        "cost": 959.95
    }
    ''')

    def "test create invalid item"() {
        when:
          def response = client.post(path: '/items/', requestContentType: 'application/json', body: invalid_item_request)
        then:
          expect response, has_status(400)
          expect response, has_no_cache_control()
          expect response, has_content_type('application/vnd.error+json;charset=UTF-8')
          expect response, has_body(error_response)
    }

    static invalid_item_request = '''
    {
        "name": "",
        "cost": 959.95
    }
    '''

    static error_response = new JSON('''
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
        "message": "name may not be empty"
    }
    ''')
}
