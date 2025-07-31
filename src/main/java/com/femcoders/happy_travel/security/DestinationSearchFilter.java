package com.femcoders.happy_travel.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class DestinationSearchFilter extends OncePerRequestFilter {

    public static final String SEARCH_TERM_PARAMETER = "searchTerm";
    public static final String SEARCH_TERM_ATTRIBUTE = "destinationSearchTerm";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.debug("DestinationSearchFilter ejecutándose para la URL: {}", request.getRequestURI());
        if (request.getRequestURI().startsWith("/destinations")) {
            String searchTerm = request.getParameter(SEARCH_TERM_PARAMETER);
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                request.setAttribute(SEARCH_TERM_ATTRIBUTE, searchTerm.trim());
                log.info("Filtro de búsqueda de destino: Término '{}' detectado.", searchTerm);
            }
        }


        filterChain.doFilter(request, response);
    }

}

