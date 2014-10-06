package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Theme;
import be.webfactor.sitecubes.repository.ThemeRepository;
import be.webfactor.sitecubes.service.ThemeService;
import be.webfactor.sitecubes.service.exception.InvalidThemeNameException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Transactional(readOnly = true)
public class ThemeServiceImpl implements ThemeService {

	@Inject private ThemeRepository themeRepository;

	public Theme getTheme(long id) {
		return themeRepository.findOne(id);
	}

	public List<Theme> getThemes() {
		return themeRepository.findAll();
	}

	@Transactional @Secured("ROLE_ADMIN")
	public Theme save(Theme theme) {
		validateName(theme);
		return themeRepository.save(theme);
	}

	private void validateName(Theme theme) {
		if (StringUtils.isBlank(theme.getName())) {
			throw new InvalidThemeNameException();
		}
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void delete(Theme theme) {
		themeRepository.delete(theme);
	}

}
