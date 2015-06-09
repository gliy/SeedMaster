package com.seed.step;

import com.seed.PatchType;
import com.seed.SeedUtils;
import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import com.seed.step.utils.Steps;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Time;
import org.tbot.methods.walking.Walking;
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;

import java.util.Arrays;

/**
 * Created by Ian on 5/27/2015.
 */
public class RakeStep implements Step<GameObject> {
   @Override
   public StepResult execute(GameObject param) {
      SeedUtils.info(param.getName());
      final Tile tile = param.getLocation();
      if (!tile.isOnScreen() && tile.isOnMiniMap()) {
         Walking.walkTileMM(tile);
         Time.sleepUntil(new Condition() {
            @Override
            public boolean check() {
               return tile.isOnScreen();
            }
         });
      }

      SeedUtils.info(Arrays.toString(param.getActions()));
      param.interact("Rake");

      Time.sleepUntil(new Condition() {
         @Override
         public boolean check() {
            return PatchType.typeFromId(
                    GameObjects.getTopAt(tile).getID()) != null;
         }
      }, 20000);
      return StepResult.create(Steps.CHECK_PATCH);
   }
}
