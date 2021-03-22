package logs;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Classe permettant de créer le journal récapitulatif du fonctionnement de l'application
 * 
 * @author Chri-
 *
 */
public class LoggerUtility {
	
	private static final String FILE_LOG_CONFIG = "src/logs/log4j-file.properties";
	private static final String HTML_LOG_CONFIG = "src/logs/log4j-html.properties";

	public static Logger getLogger(Class<?> logClass) {
		PropertyConfigurator.configure(HTML_LOG_CONFIG);
		String className = logClass.getName();
		return Logger.getLogger(className);
	}

}
