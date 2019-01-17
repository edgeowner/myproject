/**
 * Copyright 2012 Netflix, Inc.
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
package com.huboot.share.common.feign;

import com.netflix.hystrix.exception.HystrixBadRequestException;


public class BizHystrixBadRequestException extends HystrixBadRequestException {

    private String errorKey;

    public BizHystrixBadRequestException(String message) {
        super(message);
    }

    public BizHystrixBadRequestException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }

    public BizHystrixBadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }
}
