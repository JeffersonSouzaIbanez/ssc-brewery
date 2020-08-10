package guru.sfg.brewery.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class RestParameterAuthFilter extends AbstractAuthFilter {

    public RestParameterAuthFilter(final RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected String getPassword(final HttpServletRequest request) {
        return request.getParameter( "Api-Secret");
    }

    @Override
    protected String getUserName(final HttpServletRequest request) {
        return request.getParameter("Api-Key");
    }
}

