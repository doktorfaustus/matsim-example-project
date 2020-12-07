package org.matsim.project;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.matsim.api.core.v01.Scenario;
import org.matsim.contrib.otfvis.OTFVisLiveModule;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.testcases.MatsimTestUtils;

import java.util.logging.Logger;

/**
 * @author nagel
 *
 */
public class RunMatsimMyTest{
    //private static final Logger log = Logger.getLogger(RunMatsimMyTest.class);

	@Rule
	public static MatsimTestUtils utils = new MatsimTestUtils();

	@Test
	public static void main(String[] args) {
		try{
			RunMatsim.main(null);
		}
		catch (Exception ee){
			//log.fatal(ee);
			Assert.fail();
		}
	}
	
}
