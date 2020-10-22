package com.lingyun.gateway.config;


import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.*;

/**

 * @Description

 * @Author changyandong@cyd.com

 * @Created Date: 2018/8/16 17:14

 * @ClassName ForwardedHeadersFilter

 * @Version: 1.0

 */

@Component

public class ForwardedHeadersFilter implements HttpHeadersFilter, Ordered {

    public static final String FORWARDED_HEADER = "Forwarded";

    public ForwardedHeadersFilter() {

    }

    @Override

    public int getOrder() {

        return 0;

    }

    @Override

    public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {

        ServerHttpRequest request = exchange.getRequest();

        HttpHeaders updated = new HttpHeaders();

        input.entrySet().stream().filter((entry) -> {

            return !((String)entry.getKey()).toLowerCase().equalsIgnoreCase("Forwarded");

        }).forEach((entry) -> {

            updated.addAll((String)entry.getKey(), (List)entry.getValue());

        });

        List<Forwarded> forwardeds = parse(input.get("Forwarded"));

        Iterator var7 = forwardeds.iterator();

        while(var7.hasNext()) {

            ForwardedHeadersFilter.Forwarded f = (ForwardedHeadersFilter.Forwarded)var7.next();

            updated.add("Forwarded", f.toString());

        }

        URI uri = request.getURI();

        String host = input.getFirst("Host");

        ForwardedHeadersFilter.Forwarded forwarded = (new ForwardedHeadersFilter.Forwarded()).put("host", host).put("proto", uri.getScheme());

        InetSocketAddress remoteAddress = request.getRemoteAddress();

        //改了这里

        if (remoteAddress != null) {

            String forValue = remoteAddress.getAddress().getHostAddress();

            int port = remoteAddress.getPort();

            if (port >= 0) {

                forValue = forValue + ":" + port;

            }

            forwarded.put("for", forValue);

        }

        updated.add("Forwarded", forwarded.toHeaderValue());

        return updated;

    }

    static List<Forwarded> parse(List<String> values) {

        ArrayList<Forwarded> forwardeds = new ArrayList();

        if (CollectionUtils.isEmpty(values)) {

            return forwardeds;

        } else {

            Iterator var2 = values.iterator();

            while(var2.hasNext()) {

                String value = (String)var2.next();

                ForwardedHeadersFilter.Forwarded forwarded = parse(value);

                forwardeds.add(forwarded);

            }

            return forwardeds;

        }

    }

    static ForwardedHeadersFilter.Forwarded parse(String value) {

        String[] pairs = StringUtils.tokenizeToStringArray(value, ";");

        LinkedCaseInsensitiveMap<String> result = splitIntoCaseInsensitiveMap(pairs);

        if (result == null) {

            return null;

        } else {

            ForwardedHeadersFilter.Forwarded forwarded = new ForwardedHeadersFilter.Forwarded(result);

            return forwarded;

        }

    }

    static LinkedCaseInsensitiveMap<String> splitIntoCaseInsensitiveMap(String[] pairs) {

        if (ObjectUtils.isEmpty(pairs)) {

            return null;

        } else {

            LinkedCaseInsensitiveMap<String> result = new LinkedCaseInsensitiveMap();

            String[] var2 = pairs;

            int var3 = pairs.length;

            for(int var4 = 0; var4 < var3; ++var4) {

                String element = var2[var4];

                String[] splittedElement = StringUtils.split(element, "=");

                if (splittedElement != null) {

                    result.put(splittedElement[0].trim(), splittedElement[1].trim());

                }

            }

            return result;

        }

    }

    static class Forwarded {

        private static final char EQUALS = '=';

        private static final char SEMICOLON = ';';

        private final Map<String, String> values;

        public Forwarded() {

            this.values = new HashMap();

        }

        public Forwarded(Map<String, String> values) {

            this.values = values;

        }

        public ForwardedHeadersFilter.Forwarded put(String key, String value) {

            this.values.put(key, value);

            return this;

        }

        private String quoteIfNeeded(String s) {

            return s != null && s.contains(":") ? "\"" + s + "\"" : s;

        }

        public String get(String key) {

            return (String)this.values.get(key);

        }

        Map<String, String> getValues() {

            return this.values;

        }

        public String toString() {

            return "Forwarded{values=" + this.values + '}';

        }

        public String toHeaderValue() {

            StringBuilder builder = new StringBuilder();

            Map.Entry entry;

            for(Iterator var2 = this.values.entrySet().iterator(); var2.hasNext(); builder.append((String)entry.getKey()).append('=').append((String)entry.getValue())) {

                entry = (Map.Entry)var2.next();

                if (builder.length() > 0) {

                    builder.append(';');

                }

            }

            return builder.toString();

        }

    }

}
