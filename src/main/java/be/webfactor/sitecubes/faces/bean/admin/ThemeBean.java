package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.Theme;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.ThemeService;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Scope("view")
public class ThemeBean implements Serializable {

	@Inject private ThemeService themeService;
	@Inject private FacesUtil facesUtil;

	private List<Theme> themes;
	private Theme theme;

	@PostConstruct
	public void initData() {
		theme = null;
		themes = themeService.getThemes();
	}

	public void initNewTheme() {
		theme = new Theme();
	}

	public void onRowSelect(SelectEvent event) {
		theme = (Theme) event.getObject();
	}

	public void save() {
		themeService.save(theme);
		facesUtil.info("theme-saved-successfully");
	}

	public void delete() {
		themeService.delete(theme);
		initData();
		facesUtil.info("theme-deleted-successfully");
	}

	public void cancel() {
		initData();
	}

	public List<Theme> getThemes() {
		return themes;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}

}
