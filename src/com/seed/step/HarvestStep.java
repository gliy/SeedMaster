package com.seed.step;

import com.seed.PatchType;
import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import com.seed.step.utils.Steps;
import org.tbot.methods.GameObjects;
import org.tbot.methods.Time;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Condition;
import org.tbot.wrappers.GameObject;
import org.tbot.wrappers.Tile;

/**
 * Created by Ian on 5/27/2015.
 */
public class HarvestStep implements Step<GameObject> {


   @Override
   public StepResult execute(GameObject param) {
      String interaction = param.hasAction("Harvest") ? "Harvest" : "Pick";
      if (param.hasAction(interaction)) {
         param.interact(interaction);
         
         int full = harvest(param.getLocation());
         if (full == 1) {
            return StepResult.create(Steps.NOTE);
         } else if(full == -1){
            return StepResult.error(String.format("Harvest of %s failed", param.getName()));
         }
      }
      return StepResult.create(Steps.CHECK_PATCH);
   }

   private int harvest(final Tile tile) {

      boolean until = Time.sleepUntil(new Condition() {
         @Override
         public boolean check () {
            return PatchType.typeFromId(
                    GameObjects.getTopAt(tile).getID()) != null ||
                    Inventory.isFull();
         }
      }, 10000);
      if(!until){
         return -1;
      }
      return Inventory.isFull() ? 1 :0;
   }


}
