package com.luotao.demo.dubbozipkin.web;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.SpanTextMap;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.cloud.sleuth.instrument.web.HttpSpanInjector;
import org.springframework.cloud.sleuth.instrument.web.ZipkinHttpSpanInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * User: luotao-pc
 * Date: 2018/6/16
 * Time: 15:40
 */
//@EnableFeignClients
//@EnableDiscoveryClient
@SpringBootApplication
@Configuration
@ImportResource(locations = {"classpath:applicationContext.xml"})
public class Main extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Main.class);
    }

    @Bean HttpSpanInjector customHttpServletResponseSpanInjector() {
    	return new CustomHttpServletResponseSpanInjector();
    }

    @Bean
    HttpResponseInjectingTraceFilter responseInjectingTraceFilter(Tracer tracer) {
    	return new HttpResponseInjectingTraceFilter(tracer, customHttpServletResponseSpanInjector());
    }

    static class CustomHttpServletResponseSpanInjector extends ZipkinHttpSpanInjector {

        @Override
        public void inject(Span span, SpanTextMap carrier) {
            super.inject(span, carrier);
            carrier.put(Span.TRACE_ID_NAME, span.traceIdString());
            carrier.put(Span.SPAN_ID_NAME, Span.idToHex(span.getSpanId()));
        }
    }

    static class HttpResponseInjectingTraceFilter extends GenericFilterBean {

        private final Tracer tracer;
        private final HttpSpanInjector spanInjector;

        public HttpResponseInjectingTraceFilter(Tracer tracer, HttpSpanInjector spanInjector) {
            this.tracer = tracer;
            this.spanInjector = spanInjector;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            Span currentSpan = this.tracer.getCurrentSpan();
            this.spanInjector.inject(currentSpan, new HttpServletResponseTextMap(response));
            filterChain.doFilter(request, response);
        }

        class HttpServletResponseTextMap implements SpanTextMap {

            private final HttpServletResponse delegate;

            HttpServletResponseTextMap(HttpServletResponse delegate) {
                this.delegate = delegate;
            }

            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                Map<String, String> map = new HashMap<>();
                for (String header : this.delegate.getHeaderNames()) {
//                    map.put(header, this.delegate.getHeader(header));
                }
                return map.entrySet().iterator();
            }

            @Override
            public void put(String key, String value) {
                this.delegate.addHeader(key, value);
            }
        }
    }

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(Main.class).web(true).run(args);
    }
}
