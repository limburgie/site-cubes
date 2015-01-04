package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Theme;
import be.webfactor.sitecubes.repository.ThemeRepository;
import be.webfactor.sitecubes.service.ThemeService;
import be.webfactor.sitecubes.service.exception.DefaultThemeCannotBeDeletedException;
import be.webfactor.sitecubes.service.exception.InvalidThemeNameException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Transactional(readOnly = true)
public class ThemeServiceImpl implements ThemeService {

	@Inject private ThemeRepository themeRepository;

	@PostConstruct
	public void initDefault() {
		if (getDefault() == null) {
			themeRepository.save(Theme.DEFAULT);
		}
	}

	public Theme getDefault() {
		return themeRepository.getDefault();
	}

	public List<Theme> getThemes() {
		return themeRepository.findAll();
	}

	@Transactional
	public Theme save(Theme theme) {
		validateName(theme);
		return themeRepository.save(theme);
	}

	private void validateName(Theme theme) {
		if (StringUtils.isBlank(theme.getName())) {
			throw new InvalidThemeNameException();
		}
	}

	@Transactional
	public void delete(Theme theme) {
		if (theme.isDefaultTheme()) {
			throw new DefaultThemeCannotBeDeletedException();
		}
		themeRepository.delete(theme);
	}

	public Theme getTheme(long id) {
		return themeRepository.findOne(id);
	}

}
