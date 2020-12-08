package org.matsim.project;

import org.matsim.api.core.v01.Scenario;
import org.matsim.contrib.av.robotaxi.fares.drt.DrtFareConfigGroup;
import org.matsim.contrib.av.robotaxi.fares.drt.DrtFareModule;
import org.matsim.contrib.av.robotaxi.fares.drt.DrtFaresConfigGroup;
import org.matsim.contrib.drt.run.DrtModeModule;
import org.matsim.contrib.drt.run.MultiModeDrtConfigGroup;
import org.matsim.contrib.drt.run.MultiModeDrtModule;
import org.matsim.contrib.dvrp.run.DvrpConfigGroup;
import org.matsim.contrib.emissions.EmissionModule;
import org.matsim.contrib.otfvis.OTFVisLiveModule;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigGroup;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.AbstractModule;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.Injector;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.vis.otfvis.OTFVisConfigGroup;

import static org.matsim.api.core.v01.TransportMode.drt;

public class ITS_Remake {
public static void main(String[] args) {
    DrtFaresConfigGroup drtfcg = new DrtFaresConfigGroup();
    Config config;
    if (args == null || args.length == 0 || args[0] == null) {
        config = ConfigUtils.loadConfig("scenarios/ue5_input_files/carconfig.xml", new DrtFaresConfigGroup(), new DvrpConfigGroup(), new MultiModeDrtConfigGroup(), new OTFVisConfigGroup());
    } else {
        config = ConfigUtils.loadConfig(args);

    }
    config.controler().setOverwriteFileSetting(OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists);
}
private static void run(Config config){
    Scenario scenario = ScenarioUtils.loadScenario(config);
    EventsManager eventsManager = EventsUtils.createEventsManager();
    Controler controler = new Controler(scenario);
    controler.run();
}

public static void main(Config config){
    run(config);
}

}
