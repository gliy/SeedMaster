package com.seed.step.utils;

import com.seed.step.utils.StepResult;

/**
 * Created by Ian on 5/26/2015.
 */
public interface Step<E> {

   StepResult execute(E param);
}
