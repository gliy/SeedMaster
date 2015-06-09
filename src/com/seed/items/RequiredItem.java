package com.seed.items;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Ian on 5/26/2015.
 */
@Deprecated
public class RequiredItem {
   public static final String SPADE = "Spade";
   public static final String RAKE = "Rake";
   public static final String COMPOST = "Compost";
   public static final String SUPER_COMPOST = "Supercompost";

   public static final String WATERING_CAN = "Watering Can";

   public static List<String> getRequired() {
      return Arrays.asList(SPADE, RAKE, "Seed Dibber");
   }


}
