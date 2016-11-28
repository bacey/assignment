import models.Category;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {

    @Override
    public void doJob() {
        // Load initial data into the DB if the DB is empty
        if (Category.count() == 0) {
            Logger.debug("Loading initial data into the DB...");
            Fixtures.loadModels("initial-data.yml");
            Logger.debug("Loading of initial data finished.");
        } else {
            Logger.debug("Not loading initial data into the DB " +
                    "in order to avoid overwriting the already existing data.");
        }
    }

}
