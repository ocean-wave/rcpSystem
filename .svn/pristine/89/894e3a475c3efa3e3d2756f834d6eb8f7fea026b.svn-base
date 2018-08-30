package cn.com.cdboost.collect.filter;

import com.google.common.reflect.TypeToken;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author wt
 * @desc
 * @create in  2018/5/11
 **/
public class LogStream implements Filter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                // 防止流读取一次后就没有了, 所以需要将流继续写出去
                ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper(httpServletRequest);
                String body = HttpHelper.getBodyString(requestWrapper);
                filterChain.doFilter(requestWrapper, servletResponse);

    }

}
