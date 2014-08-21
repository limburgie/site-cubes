package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.faces.helper.FacesUtil;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named @Scope("session")
public class SessionDataBean implements Serializable {

	@Inject private FacesUtil facesUtil;

	private boolean controlsActive;

	public void setControlsActive(boolean controlsActive) {
		this.controlsActive = controlsActive;
		if(controlsActive) {
			facesUtil.js("$('body').removeClass('controls-hidden');");
		} else {
			facesUtil.js("$('body').addClass('controls-hidden')");
		}
	}

	public boolean isControlsActive() {
		return controlsActive;
	}

}
