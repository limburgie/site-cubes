package be.webfactor.sitecubes.rewrite;

import org.ocpsoft.rewrite.bind.Evaluation;
import org.ocpsoft.rewrite.config.InboundOperation;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.event.InboundRewrite;
import org.ocpsoft.rewrite.servlet.event.InboundServletRewrite;
import org.ocpsoft.rewrite.servlet.impl.HttpInboundRewriteImpl;

public abstract class SiteCubesOperation extends InboundOperation {

	protected void forward(InboundRewrite event, String siteFriendlyUrl, String pageFriendlyUrl) {
		((InboundServletRewrite) event).forward("/pages/view.xhtml?s=" + siteFriendlyUrl + "&p=" + pageFriendlyUrl);
	}

	protected String getPageFriendlyUrl(EvaluationContext context) {
		return getContextValue(context, "pageFriendlyUrl");
	}

	protected String getSiteFriendlyUrl(EvaluationContext context) {
		return getContextValue(context, "siteFriendlyUrl");
	}

	protected String getHostName(InboundRewrite event) {
		HttpInboundRewriteImpl rewrite = (HttpInboundRewriteImpl) event;
		return rewrite.getRequest().getServerName();
	}

	private String getContextValue(EvaluationContext context, String key) {
		Object[] values = (Object[]) context.get(Evaluation.class.getName() + "_" + key);
		if (values != null && values.length > 0) {
			return (String) values[0];
		}
		return null;
	}

}
