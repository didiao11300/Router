/*
 * Copyright (C) 2017 Haoge
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lzh.nonview.router.parser;

import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 *
 * A parser to parse uri to scheme/host/params .etc
 * Created by lzh on 16/9/5.
 */
public class URIParser {

    private Uri uri;
    private String scheme;
    private String host;
    private Map<String,String> params;

    public URIParser(Uri uri) {
        this.uri = uri;
        parse();
    }

    private void parse() {
        scheme = this.uri.getScheme();
        host = this.uri.getHost() + this.uri.getPath();
        if (host.endsWith("/")) {
            host = host.substring(0,host.lastIndexOf("/"));
        }
        String query = uri.getEncodedQuery();
        if (!TextUtils.isEmpty(query)) {
            params = parseParams(query);
        } else {
            params = new HashMap<>();
        }
    }

    /**
     * Parse params form query string
     * <p>
     * To support parse list to bundle,use {@link IdentityHashMap} to hold key-value
     * </p>
     * @param query query in uri
     * @return a map contains key-value data parsed by query in uri
     */
    static Map<String,String> parseParams(String query) {
        Map<String,String> params = new IdentityHashMap<>();
        String[] split = query.split("&");
        for (String param : split) {
            String[] keyValue = param.split("=");
            //noinspection RedundantStringConstructorCall
            params.put(new String(keyValue[0]), keyValue.length >= 2 ? Uri.decode(keyValue[1]) : "");
        }
        return params;
    }

    public String getScheme() {
        return scheme;
    }

    public String getHost() {
        return host;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
