package org.matsim.project;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Population;
import org.matsim.contrib.av.robotaxi.fares.drt.DrtFaresConfigGroup;
import org.matsim.contrib.drt.run.MultiModeDrtConfigGroup;
import org.matsim.contrib.dvrp.run.DvrpConfigGroup;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.io.IOUtils;
import org.matsim.examples.ExamplesUtils;
import org.matsim.testcases.MatsimTestUtils;
import org.matsim.utils.eventsfilecomparison.EventsFileComparator;
import org.matsim.vis.otfvis.OTFVisConfigGroup;

import java.net.URL;

public class ITS_RemakeIntegrationTest {
// Integration test to find out whether my remake of the ITS class car-case achieves the same results as the bundled JAR we got in the class.
        @Rule
        public MatsimTestUtils utils = new MatsimTestUtils() ;

        @Before
       public void init(){
            Config config = ConfigUtils.loadConfig("scenarios/ue5_input_files/carconfig.xml", new DrtFaresConfigGroup(), new DvrpConfigGroup(), new MultiModeDrtConfigGroup(), new OTFVisConfigGroup());
            config.controler().setOutputDirectory(utils.getOutputDirectory());
            ITS_Remake.main(config);
        }
        @Test
        public final void test() {
            try {
                    Population expected = PopulationUtils.createPopulation( ConfigUtils.createConfig() ) ;
                    PopulationUtils.readPopulation( expected,  "scenarios/ue5_input_files/output/car-case/car-case.output_plans.xml.gz");

                    Population actual = PopulationUtils.createPopulation( ConfigUtils.createConfig() ) ;
                    PopulationUtils.readPopulation( actual, "scenarios/ue5_input_files/myOutput/car-case/car-case.output_plans.xml.gz" );

                    boolean result = PopulationUtils.comparePopulations( expected, actual );
                    Assert.assertTrue( result );
            } catch ( Exception ee ) {
                Logger.getLogger(this.getClass()).fatal("there was an exception: \n" + ee ) ;
                // if one catches an exception, then one needs to explicitly fail the test:
                Assert.fail();
            }

        }

    @Test
    public final void testEvents() {
        try {
                String expected = utils.getInputDirectory() + "/car_case.output_events.xml.gz" ;
                String actual = utils.getOutputDirectory() + "/car_case.output_events.xml.gz" ;
                EventsFileComparator.Result result = EventsUtils.compareEventsFiles( expected, actual );
                Assert.assertEquals( EventsFileComparator.Result.FILES_ARE_EQUAL, result );
        } catch ( Exception ee ) {
            Logger.getLogger(this.getClass()).fatal("there was an exception: \n" + ee ) ;
            Assert.fail();
        }


    }
}
