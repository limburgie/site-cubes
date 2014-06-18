package be.webfactor.sitecubes.faces.helper.exception;

public final class ExceptionUtil {

	private ExceptionUtil() {
	}

	/**
	 * Returns the first encountered cause of the given exception that isn't one of the given Exception types.
	 */
	public static <T extends Throwable> Throwable getCauseExcludingTypes(Throwable exception, Class<?>... types) {
		Throwable result = exception;
		for (Class<?> type : types) {
			result = getCauseExcludingType(result, type);
		}
		return result;
	}

	private static <T extends Throwable> Throwable getCauseExcludingType(Throwable exception, Class<?> type) {
		while (type.isInstance(exception) && exception.getCause() != null) {
			exception = exception.getCause();
		}

		return exception;
	}

}
