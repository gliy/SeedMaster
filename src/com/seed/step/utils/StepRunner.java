package com.seed.step.utils;

import com.seed.SeedSettings;
import com.seed.SeedUtils;
import com.seed.step.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ian on 5/27/2015.
 */
public class StepRunner {

   private static final Map<Steps, Step> steps = new HashMap<Steps, Step>() {
      {
         put(Steps.CHECK_PATCH, new CheckPatchStep());
         put(Steps.HARVEST, new HarvestStep());
         put(Steps.PLANT, new PlantStep());
         put(Steps.DIG_UP, new DigupStep());
         put(Steps.NOTE, new NoteStep());
         put(Steps.RAKE, new RakeStep());
         put(Steps.START, new StartStep());
         put(Steps.BANK, new BankStep());
         put(Steps.COMPOST, new CompostStep());
         put(Steps.TO_PATCH, new ToPatchStep());
      }
   };

   public static boolean run () {
      StepResult current = StepResult.create(Steps.BANK, SeedSettings.getSettings().getLocation());

      while (current.getNextStep() != Steps.ERROR) {
         SeedUtils.info("Running Step " + current.getNextStep());
         current = steps.get(current.getNextStep()).execute(current.getParam());
         org.tbot.methods.Time.sleep(1000);
      }
      SeedUtils.error("ERROR " + current.getParam());
      return false;
   }
}
