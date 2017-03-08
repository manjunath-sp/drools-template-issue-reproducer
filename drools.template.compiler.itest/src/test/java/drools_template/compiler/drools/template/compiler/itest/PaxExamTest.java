package drools_template.compiler.drools.template.compiler.itest;

import static org.ops4j.pax.exam.CoreOptions.cleanCaches;
import static org.ops4j.pax.exam.CoreOptions.maven;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.wrappedBundle;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.configureConsole;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.features;
import static org.ops4j.pax.exam.karaf.options.KarafDistributionOption.karafDistributionConfiguration;

import java.io.File;

import javax.inject.Inject;

import org.drools.testcoverage.service.DroolsCompiler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.options.MavenArtifactUrlReference;
import org.ops4j.pax.exam.options.MavenUrlReference;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;
import org.osgi.framework.BundleContext;

/**
 * Unit test for simple App.
 */
@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class PaxExamTest {

	private static final String DROOLS_VERSION = "6.4.0.Final";
	private final static String KARAF_VERSION = "3.0.3";

	@Inject
	BundleContext bundleContext;

	@Inject
	DroolsCompiler droolsCompiler;

	@Configuration
	public Option[] config() {

		MavenArtifactUrlReference karafUrl = maven().groupId("org.apache.karaf").artifactId("apache-karaf")
				.version(KARAF_VERSION).type("zip");

		MavenUrlReference karafStandardRepo = maven().groupId("org.apache.karaf.features").artifactId("standard")
				.version(KARAF_VERSION).classifier("features").type("xml");

		return new Option[] {

				karafDistributionConfiguration().frameworkUrl(karafUrl).unpackDirectory(new File("target", "exam"))
						.useDeployFolder(false),
				// keepRuntimeFolder(),

				cleanCaches(true), configureConsole().ignoreLocalConsole(),

				features(karafStandardRepo, "scr"), features(karafStandardRepo, "eventadmin"), cleanCaches(true),

				mavenBundle().groupId("com.google.protobuf").artifactId("protobuf-java").version("2.6.0").start(),
				mavenBundle().groupId("org.apache.servicemix.bundles")
						.artifactId("org.apache.servicemix.bundles.xstream").version("1.4.7_1").start(),
				mavenBundle().groupId("org.apache.servicemix.bundles")
						.artifactId("org.apache.servicemix.bundles.jaxb-xjc").version("2.2.11_1").start(),
				mavenBundle().groupId("org.apache.servicemix.bundles")
						.artifactId("org.apache.servicemix.bundles.jaxb-impl").version("2.2.11_1").start(),
				mavenBundle().groupId("org.mvel").artifactId("mvel2").version("2.2.8.Final").start(),
				wrappedBundle(mavenBundle("org.eclipse.jdt.core.compiler", "ecj", "4.4.2")).exports("*;version=4.4.2"),
				wrappedBundle(mavenBundle("org.codehaus.janino", "janino", "2.5.16")).exports("*;version=2.5.16"),
				mavenBundle().groupId("org.apache.geronimo.specs").artifactId("geronimo-atinject_1.0_spec")
						.version("1.0").start(),
				wrappedBundle(mavenBundle("javax.enterprise", "cdi-api", "1.1")).exports("*;version=1.1"),
				mavenBundle().groupId("commons-codec").artifactId("commons-codec").version("1.9").start(),
				mavenBundle().groupId("org.apache.servicemix.bundles").artifactId("org.apache.servicemix.bundles.antlr")
						.version("3.5_1").start(),

				mavenBundle().groupId("org.apache.servicemix.bundles").artifactId("org.apache.servicemix.bundles.poi")
						.version("3.13_2").start(),
				// Drools bundles
				mavenBundle().groupId("org.kie").artifactId("kie-api").version(DROOLS_VERSION).start(),
				mavenBundle().groupId("org.kie").artifactId("kie-internal").version(DROOLS_VERSION).start(),

				mavenBundle().groupId("org.drools").artifactId("drools-core").version(DROOLS_VERSION).start(),
				mavenBundle().groupId("org.drools").artifactId("drools-compiler").version(DROOLS_VERSION).start(),
				mavenBundle().groupId("org.drools").artifactId("drools-decisiontables").version(DROOLS_VERSION).start(),
				mavenBundle().groupId("org.drools").artifactId("drools-templates").version(DROOLS_VERSION).start(),

				// Compiler bundle
				mavenBundle().groupId("drools.template.compiler").artifactId("drools.template.compiler.bundle")
						.version("0.0.1-SNAPSHOT").start(),

		};
	}

	@Test
	public void testRuleTemplateCompiler() {
		final String compiledRules = droolsCompiler.compileRules();
		System.out.println(compiledRules);
		Assert.assertNotNull(compiledRules);
	}

}
