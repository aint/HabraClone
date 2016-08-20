package ua.sg.academy.java2.habraclone.webController.tag;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.JspAwareRequestContext;
import org.springframework.web.servlet.support.RequestContext;
import ua.sg.academy.java2.habraclone.service.transactional.ArticleService;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

import static org.springframework.web.servlet.tags.RequestContextAwareTag.REQUEST_CONTEXT_PAGE_ATTRIBUTE;

@Component
public class IfUserCanVote extends ConditionalTagSupport {
    private static final long serialVersionUID = 8139166071990512678L;

    private Long articleId;

    @Override
    protected boolean condition() throws JspTagException {
        ArticleService articleService = getRequestContext().getWebApplicationContext().getBean(ArticleService.class);
        return articleService.userCanVote(articleId, getPrincipal());
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    private String getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (principal instanceof UserDetails
                ? ((UserDetails)principal).getUsername()
                : principal.toString());
    }

    private RequestContext getRequestContext() {
        RequestContext requestContext = (RequestContext) pageContext.getAttribute(REQUEST_CONTEXT_PAGE_ATTRIBUTE);
        if (requestContext == null) {
            requestContext = new JspAwareRequestContext(pageContext);
            pageContext.setAttribute(REQUEST_CONTEXT_PAGE_ATTRIBUTE, requestContext);
        }
        return requestContext;
    }

}