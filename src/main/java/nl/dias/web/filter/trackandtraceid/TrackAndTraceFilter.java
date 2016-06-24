package nl.dias.web.filter.trackandtraceid;

import nl.dias.service.trackandtraceid.InkomendRequestService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@Component
public class TrackAndTraceFilter implements Filter {
    private InkomendRequestService inkomendRequestService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
        this.inkomendRequestService = ctx.getBean(InkomendRequestService.class);
    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String json = null;

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        MultiReadHttpServletRequest multiReadHttpServletRequest = new MultiReadHttpServletRequest(httpRequest);
        String url = getFullURL(httpRequest);

        if (!url.endsWith("/log4j/log4javascript")) {

            if ("POST".equalsIgnoreCase(httpRequest.getMethod()) && !url.endsWith("bijlage/uploadBijlage")) {
                json = getJson(multiReadHttpServletRequest.getReader());

                //Wachtwoord filteren uit inloggen request, niet zo netjes om dit plain text op te slaan
                if (url.endsWith("inloggen")) {
                    int i = json.indexOf("wachtwoord\":") + 13;
                    int j = json.indexOf("\",", i);

                    json = json.substring(0, i) + "XXXX" + json.substring(j);
                }
            }
            inkomendRequestService.opslaan(getIngelogdeGebruiker(httpRequest), json, httpRequest, url);
        }


        filterChain.doFilter(multiReadHttpServletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }

    private String getJson(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }
        reader.close();
        return sb.toString();
    }

    private Long getIngelogdeGebruiker(HttpServletRequest httpServletRequest) {
        String ingelogdeGebruiker = "ingelogdeGebruiker";
        if (httpServletRequest.getSession().getAttribute(ingelogdeGebruiker) != null && !"".equals(httpServletRequest.getSession().getAttribute(ingelogdeGebruiker))) {
            return Long.valueOf(httpServletRequest.getSession().getAttribute(ingelogdeGebruiker).toString());
        }
        return null;
    }
}