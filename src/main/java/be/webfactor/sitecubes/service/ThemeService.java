package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Theme;

import java.util.List;

public interface ThemeService {

	Theme getDefault();

	List<Theme> getThemes();

	Theme save(Theme theme);

	void delete(Theme theme);

	Theme getTheme(long id);
	
}
