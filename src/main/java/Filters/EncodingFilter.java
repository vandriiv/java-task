package Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(value = "/*")
public class EncodingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        if(response instanceof HttpServletResponse){
            HttpServletResponse alteredResponse = (HttpServletResponse)response;
            HttpServletRequest alteredRequest = (HttpServletRequest)request;
        }

        filterChain.doFilter(request, response);
    }

    private void addCorsHeader(HttpServletResponse response){

        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
        response.addHeader("Access-Control-Max-Age", "1728000");
        response.addHeader("Access-Control-Expose-Headers","Origin, X-Requested-With, Content-Type, Accept, Authorization");

    }

    @Override
    public void destroy() {}

    @Override
    public void init(FilterConfig filterConfig)throws ServletException{}
}
