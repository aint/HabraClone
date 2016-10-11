package com.github.aint.habraclone.web.mvc.tag;

import com.github.aint.habraclone.service.transactional.ArticleService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.JspAwareRequestContext;
import org.springframework.web.servlet.support.RequestContext;

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
        return articleService.userCanVote(articleId);
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
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
