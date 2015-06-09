package com.seed.step;

import com.seed.FarmLocation;
import com.seed.SeedUtils;
import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import org.tbot.methods.Players;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Walking;
import org.tbot.methods.web.Web;
import org.tbot.util.Condition;

/**
 * Created by Ian on 6/8/2015.
 */
public class ToPatchStep implements Step<FarmLocation> {
   private Web web = new Web(true);
   @Override
   public StepResult execute (final FarmLocation nextLoc) {

      SeedUtils.info(String.format("Going to %s", nextLoc.getLoc()));
      if(Walking.getRunEnergy() > 70 && !Walking.isRunEnabled()) {
         Walking.setRun(true);
      }
      Walking.findPath(nextLoc.getLoc()).traverse();
      Time.sleepUntil(new Condition() {
         @Override
         public boolean check () {
            return Players.getLocal().
                    getLocation().distance(nextLoc.getLoc()) <= 10;
         }
      }, 600000);
      return null;
   }
}
