package org.drools.testcoverage.service.impl;

import org.drools.decisiontable.ExternalSpreadsheetCompiler;
import org.drools.testcoverage.service.DroolsCompiler;

public class DroolsCompilerImpl implements DroolsCompiler {

	private static final String XLS_FILE = "org/drools/testcoverage/sample_cheese.xls";
	private static final String SIMPLE_TEMPLATE_FILE = "org/drools/testcoverage/sample_cheese.drt";

	/**
	 * When rule are compiled in the activate method java.lang.RuntimeException
	 * is caught The error
	 */
	public void activate() {
		ClassLoader bundleClassloader = getClass().getClassLoader();

		ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
		converter.compile(bundleClassloader.getResourceAsStream(XLS_FILE),
				bundleClassloader.getResourceAsStream(SIMPLE_TEMPLATE_FILE), 2, 1);

	}

	public String compileRules() {
		ClassLoader bundleClassloader = getClass().getClassLoader();

		ExternalSpreadsheetCompiler converter = new ExternalSpreadsheetCompiler();
		return converter.compile(bundleClassloader.getResourceAsStream(XLS_FILE),
				bundleClassloader.getResourceAsStream(SIMPLE_TEMPLATE_FILE), 2, 1);
	}

	/**
	 * Complies when run as a stand alone application
	 */
	public static void main(String args[]) {
		DroolsCompilerImpl droolsComplierImpl = new DroolsCompilerImpl();
		final String drl = droolsComplierImpl.compileRules();
		System.out.println("Complied rule file:" + drl);
	}
}
