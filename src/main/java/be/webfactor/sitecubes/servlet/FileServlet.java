package be.webfactor.sitecubes.servlet;

import be.webfactor.sitecubes.domain.File;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.service.FileService;
import be.webfactor.sitecubes.service.SiteService;
import be.webfactor.sitecubes.util.BeanLocator;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FileServlet extends HttpServlet {

	private SiteService siteService;
	private FileService fileService;

	@PostConstruct
	public void init() {
		siteService = BeanLocator.getBean(SiteService.class);
		fileService = BeanLocator.getBean(FileService.class);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String siteFriendlyUrl = req.getParameter("s");
		String fileName = req.getParameter("f");

		Site site = siteService.getSiteByFriendlyUrl(siteFriendlyUrl);
		if (site != null) {
			File file = fileService.getFile(site, fileName);
			long etagValue = file.getModifiedDate().getTime();
			resp.setDateHeader("ETag", etagValue);
			resp.setHeader("Cache-Control", "max-age=60");

			if (req.getDateHeader("If-None-Match") == etagValue) {
				resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}

			resp.setContentType(file.getContentType());
			resp.getOutputStream().write(file.getData());
			resp.setContentLengthLong(file.getFileSize());
		}
	}

}
