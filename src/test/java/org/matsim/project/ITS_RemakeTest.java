package org.matsim.project;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.utils.io.IOUtils;
import org.matsim.examples.ExamplesUtils;
import org.matsim.testcases.MatsimTestUtils;
import org.matsim.utils.eventsfilecomparison.EventsFileComparator;

import java.net.URL;

public class ITS_RemakeTest {

        @Rule
        public MatsimTestUtils utils = new MatsimTestUtils() ;

        @Test
        public final void test() {

            try {
                final URL baseUrl = ExamplesUtils.getTestScenarioURL( "scenarios/ue5_input_files" );
                final String fullUrl = IOUtils.extendUrl( baseUrl, "carconfig.xml" ).toString();
                String [] args = {fullUrl,
                        "--config:controler.outputDirectory", utils.getOutputDirectory(),
                        "--config:controler.lastIteration", "1"
                } ;
                RunMatsim.main( args ) ;
                {
                    Population expected = PopulationUtils.createPopulation( ConfigUtils.createConfig() ) ;
                    PopulationUtils.readPopulation( expected, utils.getInputDirectory() + "/car_case.output_plans.xml.gz" );

                    Population actual = PopulationUtils.createPopulation( ConfigUtils.createConfig() ) ;
                    PopulationUtils.readPopulation( actual, utils.getOutputDirectory() + "/car_case.output_plans.xml.gz" );

                    boolean result = PopulationUtils.comparePopulations( expected, actual );
                    Assert.assertTrue( result );
                }
                {
                    String expected = utils.getInputDirectory() + "/car_case.output_events.xml.gz" ;
                    String actual = utils.getOutputDirectory() + "/car_case.output_events.xml.gz" ;
                    EventsFileComparator.Result result = EventsUtils.compareEventsFiles( expected, actual );
                    Assert.assertEquals( EventsFileComparator.Result.FILES_ARE_EQUAL, result );
                }

            } catch ( Exception ee ) {
                Logger.getLogger(this.getClass()).fatal("there was an exception: \n" + ee ) ;

                // if one catches an exception, then one needs to explicitly fail the test:
                Assert.fail();
            }


        }
    }
