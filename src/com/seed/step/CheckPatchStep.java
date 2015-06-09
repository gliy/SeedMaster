package com.seed.step;

import com.seed.PatchType;
import com.seed.SeedSettings;
import com.seed.SeedUtils;
import com.seed.step.utils.Step;
import com.seed.step.utils.StepResult;
import com.seed.step.utils.Steps;
import org.tbot.methods.GameObjects;
import org.tbot.methods.tabs.Inventory;
import org.tbot.util.Filter;
import org.tbot.wrappers.GameObject;

import java.util.Arrays;

/**
 * Created by Ian on 5/27/2015.
 */
public class CheckPatchStep implements Step<Object> {

   @Override
   public StepResult execute(Object param) {

      GameObject plant;

      for (PatchType pType : PatchType.values()) {
         plant = GameObjects.getNearest(pType.getId());
         if (plant != null && isValid(pType)) {
            return StepResult.create(Steps.PLANT, pType);
         } else if (!isValid(pType)) {
            SeedUtils.info("No valid seeds found for plot " + pType);
         }
      }

      if (Inventory.isFull()) {
         return StepResult.create(Steps.NOTE);
      }
      plant = GameObjects.getNearest(new Filter<GameObject>() {
         @Override
         public boolean accept(GameObject gameObject) {
            return gameObject.hasAction("Harvest");
         }
      });
      if (plant == null) {
         plant = GameObjects.getNearest(new Filter<GameObject>() {
            @Override
            public boolean accept(GameObject gameObject) {
               return gameObject.hasAction("Pick") &&
                       gameObject.getName().equals("Herbs");
            }
         });
         if (plant == null) {
            plant = GameObjects.getNearest(new Filter<GameObject>() {
               @Override
               public boolean accept(GameObject gameObject) {
                  return gameObject.hasAction("Pick") &&
                          !gameObject.getName().equals("Herbs");
               }
            });
         }
      }

      if (plant != null) {
         return StepResult.create(Steps.HARVEST, plant);
      }

      GameObject remove = GameObjects.getNearest(new Filter<GameObject>() {
         @Override
         public boolean accept(GameObject gameObject) {
            return Arrays.asList(gameObject.getActions()).contains("Rake");
         }
      });

      if (remove != null) {
         return StepResult.create(Steps.RAKE, remove);
      } else {
         remove = GameObjects.getNearest(new Filter<GameObject>() {
            @Override
            public boolean accept(GameObject gameObject) {
               return Arrays.asList(gameObject.getActions()).contains("Clear");
            }
         });
         if (remove != null) {
            return StepResult.create(Steps.DIG_UP, remove);
         }
      }

      SeedUtils.info("PATCH IS DONE");
      return StepResult.create(Steps.TO_PATCH, SeedSettings.getSettings().next());
   }

   private boolean isValid(final PatchType pType) {
      String name = SeedSettings.getSettings().getSeed(pType).toString();
      return Inventory.contains(name) &&
              (pType != PatchType.ALLOTMENT ||
                      Inventory.getFirst(name).getStackSize() >= 3);
   }

}
