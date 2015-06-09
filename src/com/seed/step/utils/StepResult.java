package com.seed.step.utils;

/**
 * Created by Ian on 5/26/2015.
 */
public class StepResult {
   private Steps nextStep;
   private Object param;

   private StepResult (Steps nextStep, Object param) {
      this.nextStep = nextStep;
      this.param = param;
   }

   private StepResult (Steps nextStep) {
      this.nextStep = nextStep;
   }

   public static StepResult create (Steps nextStep, Object param) {
      return new StepResult(nextStep, param);
   }


   public Steps getNextStep () {
      return nextStep;
   }

   public Object getParam () {
      return param;
   }

   public static StepResult create (Steps step) {
      return create(step, "");
   }

   public static StepResult error (String s) {
      return create(Steps.ERROR,s);
   }
}
