package logs;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Classe permettant de créer le journal récapitulatif du fonctionnement de l'application
 *
 * @author Christian BERANGER, Alexis MOSQUERA, Antoine QIU
 *
 * @version 2
 */
public class LoggerUtility {
	private static final String FILE_LOG_CONFIG = "src/logs/log4j-file.properties"; //txt
	private static final String HTML_LOG_CONFIG = "src/logs/log4j-html.properties"; //html

	/**
	 * Logger
	 * @param logClass Classe à journaliser
	 * @return logs
	 */
	public static Logger getLogger(Class<?> logClass) {
		PropertyConfigurator.configure(HTML_LOG_CONFIG);
		String className = logClass.getName();
		return Logger.getLogger(className);
	}
}