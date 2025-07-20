package co.com.linktic.inventory.config;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${security.api.key}")
    private String apiKey;

    /**
     * Filters incoming HTTP requests and validates the API key for those targeting API endpoints.
     *
     * @param request the HttpServletRequest object containing the client request
     * @param response the HttpServletResponse object for the response to the client
     * @param filterChain the FilterChain object for invoking the next filter or resource
     * @throws ServletException if an exception occurs during the filter process
     * @throws IOException if an I/O exception occurs during the filter process
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/api")) {
            String header = request.getHeader("x-api-key");
            if (!apiKey.equals(header)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API Key");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
